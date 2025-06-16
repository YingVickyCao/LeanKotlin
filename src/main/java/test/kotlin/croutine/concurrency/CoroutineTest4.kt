package test.kotlin.croutine.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlin.system.measureTimeMillis

/**
 * https://book.kotlincn.net/text/shared-mutable-state-and-concurrency.html
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#mutual-exclusion
 *
 * 运行变快了
Completed 100000 actions in 131 ms
Counter = 100000
 */

val mutex = Mutex()
private var counter = 0

fun main() = runBlocking {
    //  // 将一切都限制在单线程上下文中
    withContext(Dispatchers.Default) {
        // [0]CounterContext @coroutine#1,15
//        System.err.println("[0]" + Thread.currentThread().name + "," + Thread.currentThread().id)
        massiveRun {
            // 用锁保护每次自增
//            mutex.withLock {
//                counter++
//            }
            // 等价于
            mutex.lock()
            try {
                counter++
            } finally {
                mutex.unlock()
            }

            // [2]CounterContext @coroutine#101,15
//            System.err.println("[2]" + Thread.currentThread().name + "," + Thread.currentThread().id)
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
                    /**
                     * [1]CounterContext @coroutine#2,15
                     * [1]CounterContext @coroutine#3,15
                     * [1]CounterContext @coroutine#4,15
                     * [1]CounterContext @coroutine#5,15
                     * [1]CounterContext @coroutine#6,15
                     * ...
                     * [1]CounterContext @coroutine#100,15
                     * [1]CounterContext @coroutine#101,15
                     */
//                    System.err.println("[1]" + Thread.currentThread().name + "," + Thread.currentThread().id)
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}
