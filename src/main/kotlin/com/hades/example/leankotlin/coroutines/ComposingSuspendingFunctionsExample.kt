package com.hades.example.leankotlin.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


/**
 * Sequential by default
 */

private fun test1() {
    runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("answer is ${one + two}")
        }
        println("completed in $time ms")
    }
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    println("doSomethingUsefulOne returned.")
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    println("doSomethingUsefulTwo returned.")
    return 29
}

/**
 * Concurrent using async
 *
 */
private fun test2() {
    runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("answer is ${one.await() + two.await()}")
        }
        // job.await => one / two is running as sequential
        println("completed in $time ms")
    }
}

/**
 * Lazily started async
 */
private fun test3() {
    runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
            one.start() //start first one
            two.start() // start second one

            // job.start + job.await => one / two is running as async => ok
            // no job.start + job.await => one / two is running as sequential => not ok
            println("answer is ${one.await() + two.await()}")
        }
        println("completed in $time ms")
    }
}

/**
 * Async-style functions
 * Discourage.
 */

private fun test4() {
//    test4_example1()
    test4_example2()
}

private fun test4_example1() {
    val time = measureTimeMillis {
        // init async actions outside a coroutine
        val one = doSomethingUsefulOneAsync()
        val two = doSomethingUsefulTwoAsync()
        // waiting for a result must invoke either suspending or blocking.
        // here use runBlocking {} to lock main thread while waiting for result.
        runBlocking {
            println("answer is ${one.await() + two.await()}")
        }
    }
    println("completed in $time ms")
}

private fun test4_example2() { // TODO: how to cancel
    val time = measureTimeMillis {
        var one: Deferred<Int>? = null
        var two: Deferred<Int>? = null
        try {
            // init async actions outside a coroutine
            one = doSomethingUsefulOneAsync()
            two = doSomethingUsefulTwoAsync()
            // waiting for a result must invoke either suspending or blocking.
            // here use runBlocking {} to lock main thread while waiting for result.
            runBlocking {
                println("answer is ${one.await() + two.await()}")
            }
        } finally {
            if (one?.isCancelled == false) {
                one.cancel()
            }
            if (two?.isCancelled == false) {
                two.cancel()
            }
            println("finally  catched")
        }
    }
    println("completed in $time ms")
}

private fun doSomethingUsefulOneAsync() = GlobalScope.async {
    throw Exception("mocked exception")
    doSomethingUsefulOne()
}

private fun doSomethingUsefulTwoAsync() = GlobalScope.async { doSomethingUsefulTwo() }

/**
 * Structured concurrency with async
 */

private fun test5() {
//    test5_example1()
    test5_example2()
}

private suspend fun concurrentSum(): Int {
    return coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }
}

private fun test5_example1() {
    runBlocking {
        val time = measureTimeMillis {
            println("the answer is ${concurrentSum()}")
        }
        println("completed in $time ms")
    }
//    println("end")
}

private fun test5_example2() {
    runBlocking {
        try {
            failedConcurrentSum()
        } catch (ex: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }
    println("end")
}

private suspend fun failedConcurrentSum(): Int {
    return coroutineScope {
        val one = async<Int> {
            try {
                println("First child entered")
                delay(Long.MAX_VALUE)
                println("First child returned")
                42
            } finally {
                println("First child was cancelled")
            }
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }
}


fun main() {
//    test1()
//    test2()
//    test3()
    test4()
//    test5()
}