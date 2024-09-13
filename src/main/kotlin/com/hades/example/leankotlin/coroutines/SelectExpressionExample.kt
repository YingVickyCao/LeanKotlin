package com.hades.example.leankotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

// https://kotlinlang.org/docs/select-expression.html
// Select expression (experimental)

// Select expression makes it possible to await multiple suspending functions simultaneously and select the first one that becomes available.

/**
 * Selecting from channels
 */
@OptIn(ExperimentalCoroutinesApi::class)
private fun test1() {
    fun CoroutineScope.fizz(): ReceiveChannel<String> = produce<String> {
        while (true) { // send Fizz every 500 ms
            delay(500)
            send("Fizz")
        }
    }

    fun CoroutineScope.buzz(): ReceiveChannel<String> = produce<String> {
        while (true) { // send buzz every 1000 ms
            delay(1000)
            send("Buzz!")
        }
    }

    suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
        // select expression allows us to receive from both simultaneously using its onReceive clauses
        select<Unit> { // This select expression does not produce any result
            fizz.onReceive { value -> println("fizz -> '$value'") } // first select clause
            buzz.onReceive { value -> println("buzz -> '$value'") } // second select clause
        }
        // If not uses select, Using receive suspending function we can receive either from one channel or the other.
//        println("fizz -> '${fizz.receive()}'")
//        println("buzz -> '${buzz.receive()}'")
    }

    runBlocking {
        val fizz = fizz()
        val buzz = buzz()
        repeat(7) {
            selectFizzBuzz(fizz, buzz)
        }
        coroutineContext.cancelChildren() // cancel fizz and buzz coroutines
    }

    // Use select expression :
    // fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'

    // If not uses select,
    // fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
    //fizz -> 'Fizz'
    //fizz -> 'Fizz'
    //buzz -> 'Buzz!'
}

/**
 * Selecting on close
 */
@OptIn(ExperimentalCoroutinesApi::class)
private fun test2() {
    suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String {
        // select is biased to the first clause. When several clauses are selectable at the same time, the first one among them gets selected. Here
        return select<String> {
            //  onReceiveCatching gets immediately selected when the channel is already closed.
            a.onReceiveCatching { it ->
                val value = it.getOrNull()
                if (value != null) {
                    "a -> '$value'"
                } else {
                    "Channel a is closed"
                }
            }
            b.onReceiveCatching { it ->
                val value = it.getOrNull()
                if (value != null) {
                    "b -> '$value'"
                } else {
                    "Channel b is closed"
                }
            }
        }
    }

    runBlocking {
        val a: ReceiveChannel<String> = produce<String> {
            repeat(4) { send("Hello $it") }
        }
        val b: ReceiveChannel<String> = produce<String> {
            repeat(4) { send("World $it") }
        }
        repeat(8) { // print first 8 results
            println(selectAorB(a, b))
        }
        coroutineContext.cancelChildren()
    }
    // a -> 'Hello 0'
    //a -> 'Hello 1'
    //b -> 'World 0'
    //a -> 'Hello 2'
    //a -> 'Hello 3'
    //b -> 'World 1'
    //Channel a is closed
    //Channel a is closed
}

/**
 * Selecting to send
 */
@OptIn(ExperimentalCoroutinesApi::class)
private fun test3() {
    fun CoroutineScope.produceNumbers(side: SendChannel<Int>): ReceiveChannel<Int> = produce<Int>() {
        for (num in 1..10) { // produces 10 numbers from 1 to 10
            delay(100)
            select<Unit> {
                onSend(num) {} // send to the primary channel
                side.onSend(num) {} // or to the side channel
            }
        }
    }

    runBlocking {
        val side = Channel<Int>() // allocate side channel
        launch { // this is a very fast consumer for the side channel
            side.consumeEach { println("Side channel has $it") }
        }
        produceNumbers(side).consumeEach {
            println("Consuming $it")
            delay(250) // let us digest the consumed number properly
        }
        println("Done consuming")
        coroutineContext.cancelChildren()
    }

    // Consuming 1
    //Side channel has 2
    //Side channel has 3
    //Consuming 4
    //Side channel has 5
    //Side channel has 6
    //Consuming 7
    //Side channel has 8
    //Side channel has 9
    //Consuming 10
    //Done consuming
}


/**
 * Selecting deferred values
 */
private fun test4() {
    fun CoroutineScope.asyncString(time: Int): Deferred<String> = async {
        delay(time.toLong())
        "Wait for $time ms"
    }

    fun CoroutineScope.asyncStringList(): List<Deferred<String>> {
        val random = Random(3)
        return List(12) {
            asyncString(random.nextInt(1000))
        }
    }

    runBlocking {
        val list = asyncStringList()
        val result = select<String> {
            list.withIndex().forEach { (index, deferred) ->
                deferred.onAwait { answer ->
                    "Deferred $index produces answer '$answer'" // index may be different value when running this function
                }
            }
        }
        println(result)
        val countActive = list.count() { it.isActive }
        println("$countActive coroutines are still active")
    }

    // Deferred 6 produces answer 'Wait for 43 ms'
    //11 coroutines are still active
}

/**
 * Switch over a channel of deferred values
 */
// TODO:to understand code
@OptIn(ExperimentalCoroutinesApi::class)
private fun test5() {
    fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) = produce<String> {
        var current = input.receive() //start with first received deferred value
        while (isActive) {
            val next = select<Deferred<String>?> { // return next deferred value from this select or null
                input.onReceiveCatching { update ->
                    update.getOrNull()
                }
                current.onAwait { value ->
                    send(value) // send value that current deferred has produced
                    input.receiveCatching().getOrNull() // use the next deferred from the input channel
                }
            }
            if (null == next) {
                println("Channel was closed")
                break // out of loop
            } else {
                current = next
            }
        }
    }

    fun CoroutineScope.asyncString(str: String, time: Long) = async {
        delay(time)
        str
    }

    runBlocking {
        val chan = Channel<Deferred<String>>() // the channel for test
        launch {  // launch printing coroutine
            for (s in switchMapDeferreds(chan)) {
                println(s) //print each received string
            }
        }
        chan.send(asyncString("BEGIN", 100))
        delay(200) // enough time for "BEGIN" to produce
        chan.send(asyncString("Slow", 500))
        delay(100) // not enough time to produce slow
        chan.send(asyncString("Replace", 100))
        delay(500) // give it time before the last one
        chan.send(asyncString("End", 500))
        delay(1000) // give it time to process
        chan.close() // close the channel
        delay(500) // wait some time to left it finish
    }
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
    test5()
}


