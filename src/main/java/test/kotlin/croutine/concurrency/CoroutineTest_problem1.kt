package test.kotlin.croutine.concurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//fun main(): Unit = runBlocking {
//    try {
//        supervisorScope {
//            launch {
//                throw Exception("子协程失败")
//            }
//            launch {
//                delay(100L)
//                println("尽管其他子协程失败了，这段代码仍然会被打印。")
//            }
//        }
//    } catch (ex: Exception) {
//        println(ex)
//    }
//}

// 有并发问题 concurrency
// Completed 100000 actions in 13 ms
//Counter = 42700

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