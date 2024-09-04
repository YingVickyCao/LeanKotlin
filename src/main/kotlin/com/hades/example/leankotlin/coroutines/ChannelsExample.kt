package com.hades.example.leankotlin.coroutines

import com.hades.example.leankotlin.coroutines.CoroutineAndChannelsTutorial.loadData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.concurrent.fixedRateTimer

// Channels
// https://kotlinlang.org/docs/channels.html

// Deferred values provide a convenient way to transfer a single value between coroutines. Channels provide a way to transfer a stream of values.


/**
 * Channel basics
 */
private fun test1() {
    // ----------->
    //1
    //4
    //9
    //16
    //25
    //Done!
    //<-----------

    println("----------->")
    runBlocking {
        val channel = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic,
            // we'll just send five squares
            for (x in 1..5) channel.send(x * x)
        }
        repeat(5) { println(channel.receive()) }
        println("Done!")
    }
    println("<-----------")
}

/**
 * Closing and iteration over channels
 */
private fun test2() {
//    test2_exmple1()
    test2_exmple2()
}

private fun test2_exmple1() {
    // 1
    //2
    //3
    //4
    //5
    //Done!

    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)
            channel.close() // We are doing sending
        }
        // here we print received values using `for` loop (until the channel is closed)
        for (y in channel) println(y)
        println("Done!")
    }
}

private fun test2_exmple2() {
    // 1
    //2
    //Done!

    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                if (x == 3) {
                    channel.close()
                    break
                } else {
                    channel.send(x)
                }
            }
        }
        // here we print received values using `for` loop (until the channel is closed)
        for (y in channel) println(y)
        println("Done!")
    }
}

/**
 * Building channel producers
 */
private fun test3() {
    // 1
    //2
    //3
    //4
    //5
    //Done!

    // There is a convenient coroutine builder named produce that makes it easy to do it right on producer side, and an extension function consumeEach, that replaces a for loop on the consumer side:
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce<Int> {
        for (x in 1..5) send(x)
//        for (x in 1..5) send(x * x)
    }

    runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
    }
}

/**
 * Pipelines
 */
private fun test4() {
    // 1
    //4
    //9
    //16
    //25
    //Done!

    // this coroutine is producing, possibly infinite, stream of values
    fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce<Int> {
        var x = 1
        while (true) send(x++) // infinite stream of integers starting from 1
    }

    // another coroutine or coroutines are consuming that stream, doing some processing, and producing some other results.
    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce<Int> {
        for (x in numbers) send(x * x)
    }

    runBlocking {
        val numbers: ReceiveChannel<Int> = produceNumbers() // prodyce integers from 1 and       on
        val squares: ReceiveChannel<Int> = square(numbers)
        repeat(5) {
            println(squares.receive())  // o
        }
        println("Done!")
        coroutineContext.cancelChildren() // cancel
    }
}

/***
 * Prime numbers with pipeline
 */
private fun test5() {
    // 2
    //3
    //5
    //7
    //11
    //13
    //17
    //19
    //23
    //29

    // Let's take pipelines to the extreme with an example that generates prime numbers using a pipeline of coroutines.
    fun CoroutineScope.numbersFrom(start: Int): ReceiveChannel<Int> = produce<Int> {
        var x = start
        while (true) send(x++) // infinite stream of integers from start
    }

    fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
        for (x in numbers) {
            if (x % prime != 0) send(x)
        }
    }

    runBlocking {
        var cur = numbersFrom(2)
        repeat(10) {
            val prime = cur.receive()
            println(prime)
            cur = filter(cur, prime)
        }

        coroutineContext.cancelChildren()
    }
}

/**
 * Fan-out
 */
private fun test6() {
    // Multiple coroutines may receive from the same channel, distributing work between themselves.
    fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce<Int> {
        var x = 1
        while (true) {
            send(x++)
            delay(100)
        }
    }

    fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        // Also, pay attention to how we explicitly iterate over channel with for loop to perform fan-out in launchProcessor code. Unlike consumeEach, this for loop pattern is perfectly safe to use from multiple coroutines. If one of the processor coroutines fails, then others would still be processing the channel, while a processor that is written via consumeEach always consumes (cancels) the underlying channel on its normal or abnormal completion.
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }

    runBlocking {
        val producer: ReceiveChannel<Int> = produceNumbers()
        repeat(5) {
            println(it)
            launchProcessor(it, producer)
        }
        delay(950)
        producer.cancel() // cancelling a producer coroutine closes its channel, thus eventually terminating iteration over the channel that processor coroutines are doing.
    }

    // 0
    //1
    //2
    //3
    //4
    //Processor #0 received 1
    //Processor #0 received 2
    //Processor #1 received 3
    //Processor #2 received 4
    //Processor #3 received 5
    //Processor #4 received 6
    //Processor #0 received 7
    //Processor #1 received 8
    //Processor #2 received 9
    //Processor #3 received 10
}

/**
 * Fan-in
 */
private fun test7() {
    // Multiple coroutines may send to the same channel.

    suspend fun sendString(chanel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)
            chanel.send(s)
        }
    }

    runBlocking {
        val channel = Channel<String>()
        launch { sendString(channel, "foo", 200L) }
        launch { sendString(channel, "Bar!", 500L) }
        repeat(6) { // receive first six
            println(channel.receive())
        }
        coroutineContext.cancelChildren() // cancel all children to let main finish
    }

    //foo
    //foo
    //Bar!
    //foo
    //foo
    //Bar!
}


/**
 * Buffered channels
 */
private fun test8() {
    runBlocking {
        val channel = Channel<Int>(4) // create buffered channel
        val sender = launch {
            repeat(10) {
                println("Sending $it")
                channel.send(it) // will suspend when buffer is full
                println("Sent $it")
            }
        }
        println("Waiting")
        delay(1000) // don't receive anything, just wait
        println("Cancelled")
        sender.cancel() // cancel sender coroutine
    }

    // Waiting
    //Sending 0
    //Sent 0
    //Sending 1
    //Sent 1
    //Sending 2
    //Sent 2
    //Sending 3
    //Sent 3
    //Sending 4
    //Cancelled
}

/**
 * Channels are fair
 */
private fun test9() {
    // Send and receive operations to channels are fair with respect to the order of their invocation from multiple coroutines.
    // TODO:Note that sometimes channels may produce executions that look unfair due to the nature of the executor that is being used. https://github.com/Kotlin/kotlinx.coroutines/issues/111
    data class Ball(var hits: Int)

    suspend fun player(name: String, table: Channel<Ball>) {
        for (ball in table) { // receive the ball in a loop
            ball.hits++
            println("$name $ball")
            delay(300)
            table.send(ball)    // send the ball back
        }
    }

    runBlocking {
        val table = Channel<Ball>() // a shared table
        launch { player("ping", table) }
        launch { player("pong", table) }
        table.send(Ball((0)))   // serve the ball
        delay(1000)
        coroutineContext.cancelChildren()   // game over, cancel them
    }

    //ping Ball(hits=1)
    //pong Ball(hits=2)
    //ping Ball(hits=3)
    //pong Ball(hits=4)
}

/**
 * Ticker channels
 */
private fun test10() {
    // TODO:Ticker channel is a special rendezvous channel that produces Unit every time given delay passes since last consumption from this channel. Though it may seem to be useless standalone, it is a useful building block to create complex time-based produce pipelines and operators that do windowing and other time-dependent processing. Ticker channel can be used in select to perform "on tick" action.

    runBlocking {
        val tickerChannel: ReceiveChannel<Unit> = ticker(delayMillis = 200, initialDelayMillis = 0)
        var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Initial element is available immediately : $nextElement") // no initial delay

        nextElement = withTimeoutOrNull(100) { tickerChannel.receive() } // all subsequent elements have 200ms
        println("Next element is not ready in 100ms : $nextElement")

        nextElement = withTimeoutOrNull(120) { tickerChannel.receive() }
        println("Next element is ready in 200ms : $nextElement")

        // Emulate large consumption delays
        println("Consumer pauses for 300ms")
        delay(300)

        // Next element is available immediately
        nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Next element is available immediately after large consumer delay: $nextElement")

        // Note that the pause between `receive` calls is taken into account and next element arrives faster
        nextElement = withTimeoutOrNull(120) { tickerChannel.receive() }
        println("Next element is ready in 100ms after consumer pause in 300ms : $nextElement")

        tickerChannel.cancel() // indicate that no more elements are needed
    }

    // Initial element is available immediately : kotlin.Unit
    //Next element is not ready in 100ms : null
    //Next element is ready in 200ms : kotlin.Unit
    //Consumer pauses for 300ms
    //Next element is available immediately after large consumer delay: kotlin.Unit
    //Next element is ready in 100ms after consumer pause in 300ms : kotlin.Unit
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
//    test6()
//    test7()
//    test8()
//    test9()
    test10()
}