package com.hades.example.leankotlin.coroutines

// https://kotlinlang.org/docs/coroutines-basics.html
import kotlinx.coroutines.*
import java.util.Date

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
    test5()
//    test6()
}

/**
 * Your first coroutine
 */
private fun test1() {
    // A coroutine is an instance of a suspendable computation. a coroutine is not bound to any particular thread. It may suspend its execution in one thread and resume in another one.
    println("[1]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id)

    runBlocking { //  runBlocking :a coroutine builder
        println("[2]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id)
        launch { // launch : a coroutine builder, to create a new coroutine concurrently.
            println("[3]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id)
            delay(2000)
            println("world:" + Date().toString())
        }
        println("[4]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id)
        println("hello:" + Date().toString())
    }
    println("[5]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id)

    /**
     * Structured concurrency
     */
    // Coroutines follow a principle of structured concurrency which means that new coroutines can only be launched in a specific CoroutineScope which delimits the lifetime of the coroutine.
    // In a real application, you will be launching a lot of coroutines. Structured concurrency ensures that they are not lost and do not leak. An outer scope cannot complete until all its children coroutines complete. Structured concurrency also ensures that any errors in the code are properly reported and are never lost.
    // An outer coroutine cannot complete until all it's children coroutines complete.
}

/**
 * Extract function refactoring
 */
private fun test2() {
    println("[1]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())

    runBlocking {
        println("[2]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        launch {
            doWorld()
        }
        println("[3]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("hello:" + Date().toString())
    }
    println("[4]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
}

private suspend fun doWorld() {
    println("[5]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
    delay(2000)
    println("[6]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
    println("world:" + Date().toString())
}


/**
 * Scope builder
 */
private fun test3() {
    /**
     *
     * runBlocking method / coroutineScope function :wait for their body and all its children to complete.
     *
     * runBlocking method:
     * is a regulation function,
     * blocks the current thread for waiting.
     *
     * coroutineScope function:
     * is a suspend function,
     * releasing the underlying thread for other usages.
     * coroutineScope can be used for any suspend function.
     */
    println("[1]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())

    runBlocking {
        println("[2]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        launch {
            doWorld2()
        }
        println("[3]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("hello:" + Date().toString())
    }
    println("[4]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
}

// coroutineScope can be used for any suspend function.
private suspend fun doWorld2() = coroutineScope {
    println("[5]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
    launch {
        println("[6]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        delay(2000)
        println("[7]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("world:" + Date().toString())
    }
    println("[8]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
}

/**
 * Scope builder and concurrency
 */
private fun test4() {
    println("[1]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
    runBlocking {
        println("[2]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        launch {
            doWorld3()
        }
        println("[3]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("hello:" + Date().toString())
    }
    println("[4]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
}

// a coroutineScope builder can be used inside any suspending function to perfrom multiple concurrent operations.
private suspend fun doWorld3() = coroutineScope {
    println("[5]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
    launch {
        println("[6]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        delay(4000)
        println("[7]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("world2:" + Date().toString())
    }
    launch {
        println("[8]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        delay(1000)
        println("[9]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("world1:" + Date().toString())
    }
    println("[10]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
}

/**
 * An explicit job
 */
private fun test5() {
    // A launch coroutine builder returns a Job object that is a handle to the launched coroutine and can be used to explicitly wait for its completion.
    println("[1]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())

    runBlocking {
        println("[2]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        val job = launch {
            println("[3]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
            delay(2000)
            println("world:" + Date().toString())
        }
        println("[4]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        job.join()
        println("[5]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
        println("hello:" + Date().toString())
        println("[6]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
    }
    println("[7]thread name = " + Thread.currentThread().name + ",thread id = " + Thread.currentThread().id + "," + Date().toString())
}


/**
 * Coroutines are light-weight
 */
// Coroutines are less resource-intensive than JVM threads. Code that exhausts the JVM's available memory when using threads can be expressed using coroutines without hitting resource limits.
private fun test6() {
    testCoroutines()
    testThreads()
}

private fun testCoroutines() {
    println("[1]" + Date().toString())
    runBlocking {
        println("[2]" + Date().toString())
        repeat(50_000) {// 50_000
            launch {
                delay(5000L) // 5000L
                print(".")
            }
        }
    }
    println("\n")
    println("[3]" + Date().toString())
}

private fun testThreads() {
    println("[1]" + Date().toString())
    repeat(50_000) {// 50_000
        Thread(Runnable {
            Thread.sleep(5000L) // 5000L
            print(".")
        })
    }
    println("[3]" + Date().toString())
}