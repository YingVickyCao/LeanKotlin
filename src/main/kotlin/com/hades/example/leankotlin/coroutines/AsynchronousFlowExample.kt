package com.hades.example.leankotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Asynchronous Flow
//https://kotlinlang.org/docs/flow.html

/**
 * Representing multiple values
 */
private fun test1() {
    fun simple(): List<Int> {
        return listOf(1, 2, 3)
    }
    simple().forEach { value -> println(value) }
}

/**
 * Representing multiple values - Sequences
 */
// Sequence<Int>: for synchronously computed values
// This computation blocks the main thread that is running the code.
private fun test1_1() {
    println("--------->")
    fun simple(): Sequence<Int> {
        return sequence<Int> {
            for (i in 1..3) {
                println("waiting $i")
                Thread.sleep(100) // pretend we are computing it
                println("yield $i")
                yield(i) // yield next value
            }
        }
    }
    println("loop the values")
    simple().forEach { value -> println(value) }
    println("<---------")
}

/**
 * Representing multiple values - Suspending functions
 */
// List<Int>  : can return all the values at once.
private fun test1_2() {
    println("--------->")
    suspend fun simple(): List<Int> {
        log("delay before returned list")
        delay(1000L)
        log("returned list")
        return listOf(1, 2, 3)
    }

    runBlocking {
        println("runBlocking -------->")
        simple().forEach { value -> log("" + value + "") }
        println("runBlocking <--------")
    }
    println("<---------")
}

/**
 * Representing multiple values - Flows
 */
// Flow<Int> : represent the steams of values that re being computed asynchronously.
private fun test1_3() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            emit(i) // emit next value
        }
    }
    println("--------->")
    runBlocking {
        println("runBlocking:--------->")
        launch {
            println("launch:--------->")
            for (k in 1..3) {
                println("I'm not block $k")
                delay(100)
            }
            println("launch:<---------")
        }
        simple().collect { value ->
            println(value)
        }
        println("runBlocking:<---------")
    }
    println("<---------")
}

/**
 * Flows are cold
 */
// Flows are cold streams similar to sequences — the code inside a flow builder does not run until the flow is collected.
private fun test2() {
    fun simple(): Flow<Int> = flow {
        println("Flow started")
        for (i in 1..3) {
            println("delay before emitting $i")
            delay(100)
            println("emitting $i")
            emit(i)
        }
    }
    // The simple() call itself returns quickly and does not wait for anything. The flow starts afresh every time it is collected and that is why we see "Flow started" every time we call collect again.
    runBlocking {
        println("Calling simple function")
        val flow = simple()
        println("Calling collect..")
        flow.collect { value -> println(value) }
        println("Calling collect again..")
        flow.collect { value -> println(value) }
    }
}

/**
 * Flow cancellation basics
 */
private fun test3() {
    fun simple(): Flow<Int> = flow { //  collections / sequences -> Flow
        println("Flow started")
        for (i in 1..3) {
            println("delay before emitting $i")
            delay(100)
            println("emitting $i")
            emit(i)
        }
    }
    // The simple() call itself returns quickly and does not wait for anything. The flow starts afresh every time it is collected and that is why we see "Flow started" every time we call collect again.
    runBlocking {
        println("Calling simple function")
        // when withTimeoutOrNull block timeout, flow is cancelled.
        withTimeoutOrNull(250) { // timeout after 250ms
            val flow = simple()
            println("Calling collect..")
            flow.collect { value -> println(value) }
        }
        println("Done")
    }
}

/**
 * Flow builders
 */
private fun test4() {
    runBlocking {
        // Convert an integer range to a flow
        (1..3).asFlow().collect { value -> println(value) }
    }
}


/**
 * Intermediate flow operators
 */
private fun test5() {
    suspend fun performRequest(request: Int): String {
        delay(1000) // imitate long-running asynchronous work
        println("performRequest $request")
        return "response $request"
    }

    runBlocking {
        (1..3).asFlow()  // Flow -> collections / sequences
            .map { request ->
                println("map $request")
                performRequest(request)
            }
            .collect { response -> println(response) }
    }
}

/**
 * Intermediate flow operators - Transform operator
 */
private fun test5_1() {
    suspend fun performRequest(request: Int): String {
        delay(1000) // imitate long-running asynchronous work
        println("performRequest $request")
        return "response $request"
    }

    runBlocking {
        (1..3).asFlow()  // Flow -> collections / sequences
            .transform { request ->
                println("Emitting $request")
                emit("Making request $request")
                emit(performRequest(request))
            }
            .collect { response -> System.err.println(response) }
    }
}

/**
 * Intermediate flow operators - Size-limiting operators
 */
private fun test5_2() {
    // Size-limiting intermediate operators like take cancel the execution of the flow when the corresponding limit is reached. Cancellation in coroutines is always performed by throwing an exception, so that all the resource-management functions (like try { ... } finally { ... } blocks) operate normally in case of cancellation:
    fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }
    runBlocking {
        numbers()
            .take(2) //take only the first two
            .collect { value -> println(value) }
    }
}

/**
 * Terminal flow operators
 */
private fun test6() {
    runBlocking {
        val flow: Flow<Int> = (1..3).asFlow()
        val list = flow.toList() // Flow -> List
        println(list)

        // toSet
        val set = flow.toSet()  // Flow -> Set
        println(set)

        val first = flow.first() // Get the first value
        println(first)

        // java.lang.IllegalArgumentException: Flow has more than one element
//        val single = flow.single()    // ensure that flow emits a single value, otherwise IllegalArgumentException.
//        println(single)

        val last = flow.last()  // Get the last value
        println(last)

        // fold
        val fold = flow.fold("a") { str: String, i: Int ->
            "$str-$i"
        }
        println(fold)

        // reduce
        val sum = flow
            .map {
                println("map item $it -> ${it * it} ")
                it * it
            }
            .reduce { a, b ->
                println("sum $a,$b")
                a + b
            } // sum theme (terminal operator)
        println(sum)
    }
}


/**
 * Flows are sequential
 */
private fun test7() {
    runBlocking {
        (1..5).asFlow()
            .filter {
                println("Filter $it")
                it % 2 == 0
            }
            .map {
                println("Map $it")
                "String $it"
            }
            .collect {
                println("Collect $it")
            }
    }

}


/**
 * Flow context
 */
private fun test8() {
    fun simple(): Flow<Int> = flow {
        log("Started simple flow")
        for (i in 1..3) {
            log("Emit $i")
            emit(i)
        }
    }
    // So, by default, code in the flow { ... } builder runs in the context that is provided by a collector of the corresponding flow. For example, consider the implementation of a simple function that prints the thread it is called on and emits three numbers:
    runBlocking {
        simple().collect { value ->
            log("Collected $value")
        }
    }
}

/**
 * Flow context - A common pitfall when using withContext
 */
private fun test8_1() {
    fun simple(): Flow<Int> = flow {
        log("simple:------->")
        withContext(Dispatchers.Default) { // Wrong way to change context for  CPU-consuming code in flow builder
            for (i in 1..3) {
                log("waiting for $i")
                Thread.sleep(100) // pretend we are computing in CPU-consuming way
                log("Emitting $i")
                // java.lang.IllegalStateException: Flow invariant is violated
                // Usually, withContext is used to change the context in the code using Kotlin coroutines, but code in the flow { ... } builder has to honor the context preservation property and is not allowed to emit from a different context.
                emit(i)
            }
        }
        log("simple:<-------")
    }
    runBlocking {
        log("runBlocking ----------->")
        simple().collect { value -> log("" + value) }
        log("runBlocking <-----------")
    }
}

/**
 * Flow context - flowOn operator
 */
private fun test8_2() {
    fun simple(): Flow<Int> = flow<Int> {
        log("simple:------->")
        for (i in 1..3) {
            log("waiting for $i")
            Thread.sleep(100) // pretend we are computing in CPU-consuming way
            log("Emitting $i")
            emit(i)
        }
        log("simple:<-------")
    }.flowOn(Dispatchers.Default) // Right way to change context for  CPU-consuming code in flow builder
    // emission happens in another coroutine : [DefaultDispatcher-worker-1 @coroutine#2] that is running on another thread

    runBlocking {
        log("runBlocking ----------->")
        simple().collect { value -> log("" + value) } // collecting coroutine : [main @coroutine#1]
        log("runBlocking <-----------")
    }
}


/**
 * Buffering
 */
private fun test9() {

}

fun main() {
//    test1()
//    test1_1()
//    test1_2()
//    test1_3()

//    test2()
//    test3()
//    test4()

//    test5()
//    test5_1()
//    test5_2()

//    test6()
//    test7()

//    test8()
//    test8_1()
//    test8_2()

    test9()
}