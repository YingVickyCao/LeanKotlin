package test.kotlin.croutine.concurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * https://book.kotlincn.net/text/shared-mutable-state-and-concurrency.html
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#thread-confinement-fine-grained
 *
 * 运行变慢了
 * Completed 100000 actions in 468 ms
 * Completed 100000 actions in 593 ms
 * Completed 100000 actions in 596 ms
 * Counter = 100000
 */

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
private val counterContext = newSingleThreadContext("CounterContext")
private var counter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // confine each increment to a single-threaded context
//            System.err.println("[0]" + Thread.currentThread().name + "," + Thread.currentThread().id)
            withContext(counterContext) {
                counter++
//                System.err.println("[2]" + Thread.currentThread().name + "," + Thread.currentThread().id)
            }
        }
    }
    println("Counter = $counter")
}

private suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
//                    System.err.println("[1]" + Thread.currentThread().name + "," + Thread.currentThread().id)
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}