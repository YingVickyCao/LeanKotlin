package com.hades.example.leankotlin.coroutines.CoroutineAndChannelsTutorial.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// https://kotlinlang.org/docs/coroutines-and-channels.html#channels

fun main() {
    println("---->")
    runBlocking<Unit> {
        val channel: Channel<String> = Channel(2)
        launch {
            println("A start: [${Thread.currentThread().name}]")
            channel.send("A1")
            channel.send("A2")
            channel.send("A3")
            println("A done : [${Thread.currentThread().name}]")
        }
        launch {
            println("B start: [${Thread.currentThread().name}]")
            channel.send("B1")
            channel.send("B2")
            channel.send("B3")
            println("B done: [${Thread.currentThread().name}]")
        }
        launch {
            println("receive start: [${Thread.currentThread().name}]")
            repeat(6) {
                val x = channel.receive()
                println("receive: $x ,[${Thread.currentThread().name}]")
            }
            println("receive end: [${Thread.currentThread().name}]")
        }
    }
    println("<----")
}