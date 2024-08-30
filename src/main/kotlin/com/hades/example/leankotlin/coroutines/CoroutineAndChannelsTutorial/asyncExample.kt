package com.hades.example.leankotlin.coroutines.CoroutineAndChannelsTutorial

import kotlinx.coroutines.*

/**
 * https://kotlinlang.org/docs/coroutines-and-channels.html
 */
fun main() {
//    deferred()
    deferredObjects()
}

private fun deferred() {
    println("start")
    runBlocking {
        // When you call launch inside runBlocking, it's called as an extension to the implicit receiver of the CoroutineScope type. Alternatively, you could explicitly write this.launch.
        val deferred: Deferred<Int> = async {
            loadData()
        }
        println("waiting...")
        println(deferred.await()) // while waiting from the result, coroutine deferred.await() is called from is suspended
        println("result printed")
    }
    println("closed")
}

suspend fun loadData(): Int {
    println("loading")
    delay(5000L)
    println("loaded")
    return 42
}

private fun deferredObjects() {
    println("start")
    runBlocking {
        val deferreds: List<Deferred<Int>> = (1..3).map {
            async {
                println("loading")
                delay(2000L * it)
                println("loaded $it")
                it
            }
        }
        val sum = deferreds.awaitAll().sum()
        println("sum=$sum")
    }
    println("close")
}