package com.hades.example.leankotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

// https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html
// Shared mutable state and concurrency


/**
 * The problem
 */

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100 // numbers of coroutines t launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { //scope of coroutines
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

private fun test1() {
    // increments a shared mutable variable using multi-threaded Dispatchers.
    var counter = 0

//    println("------>")
    runBlocking {
//        println("runBlocking ------>")
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
//        println("runBlocking <------")
    }
//    println("<------")

    // Completed 100000 actions in 12 ms
    // Counter = 64382
}

/**
 * Volatiles are of no help
 */
// volatile variables guarantee linearizable (this is a technical term for "atomic") reads and writes to the corresponding variable, but do not provide atomicity of larger actions (increment in our case).
@Volatile
var counterOfVolatile = 0
private fun test2() {

//    println("------>")
    runBlocking {
//        println("runBlocking ------>")
        withContext(Dispatchers.Default) {
            massiveRun {
                counterOfVolatile++
            }
        }
        println("Counter = $counterOfVolatile")
//        println("runBlocking <------")
    }
//    println("<------")

    // Completed 100000 actions in 12 ms
    //Counter = 24000

    // Completed 100000 actions in 13 ms
    //Counter = 23451
}


/**
 *Thread-safe data structures
 */
val counterOfAtomicInteger = AtomicInteger()
private fun test3() {
    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counterOfAtomicInteger.incrementAndGet()
            }
        }
        println("Counter = $counterOfAtomicInteger")
    }
    // Completed 100000 actions in 16 ms
    //Counter = 100000
}

/**
 * Thread confinement fine-grained
 */

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
private fun test4() {
    // Thread confinement is an approach to the problem of shared mutable state where all access to the particular shared state is confined to a single thread. It is typically used in UI applications, where all UI state is confined to the single event-dispatch/application thread. It is easy to apply with coroutines by using a single-threaded context.
    // This code works very slowly, because it does fine-grained thread-confinement. Each individual increment switches from multi-threaded Dispatchers.Default context to the single-threaded context using withContext(counterContext) block.
    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0
    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                // confine each increment to a singe-thread context
                withContext(counterContext) {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }
    // Completed 100000 actions in 432 ms
    //Counter = 100000
}


/**
 * Thread confinement coarse-grained
 */
@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
private fun test5() {
    // In practice, thread confinement is performed in large chunks, e.g. big pieces of state-updating business logic are confined to the single thread.
    // running each coroutine in the single-threaded context
    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0

    runBlocking {
        // confine everything to a single-threaded context
        withContext(counterContext) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
    }

    //Completed 100000 actions in 9 ms
    //Counter = 100000
}

/**
 * Mutual exclusion
 */

private fun test6() {
    val mutex = Mutex()
    var counter = 0
    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                // protect each increment with lock
                mutex.withLock {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }

    // Completed 100000 actions in 85 ms
    //Counter = 100000
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
    test6()
}