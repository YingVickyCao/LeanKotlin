package test.kotlin.croutine.concurrency

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

private var counter = AtomicInteger()

/**
 * https://book.kotlincn.net/text/shared-mutable-state-and-concurrency.html
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#thread-safe-data-structures
 *
 * Completed 100000 actions in 13 ms
 * Completed 100000 actions in 16 ms
 * Counter = 100000
 */
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter.incrementAndGet()
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
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}