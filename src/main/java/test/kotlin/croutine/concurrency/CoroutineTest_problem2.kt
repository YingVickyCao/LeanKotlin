package test.kotlin.croutine.concurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 结论 ： Volatile并没有解决并发问题，同时运行速度变慢了
// @Volatile
// Completed 100000 actions in 12 ms
//Counter = 25543
@Volatile
private var counter = 0
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter++
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