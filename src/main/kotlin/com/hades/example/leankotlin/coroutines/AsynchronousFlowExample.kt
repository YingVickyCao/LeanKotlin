package com.hades.example.leankotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.swing.text.FlowView.FlowStrategy
import kotlin.system.measureTimeMillis

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
    // Running different parts of a flow in different coroutines can be helpful from the standpoint of the overall time it takes to collect the flow, especially when long-running asynchronous operations are involved. F
    fun simple(): Flow<Int> = flow<Int> {
        log("simple:------->")
        for (i in 1..3) {
            log("waiting for $i")
            delay(100) // pretend we are asynchronously waiting 100ms
            log("Emitting $i")
            emit(i)
        }
        log("simple:<-------")
    }

    runBlocking {
        log("runBlocking ----------->")
        val time = measureTimeMillis {
            simple().collect { value ->
                delay(300) // pretend we are processing it for 300ms
                log("" + value)
            } // collecting coroutine : [main @coroutine#1]
        }
        log("Collected in $time ms") // 1227 ms
        log("runBlocking <-----------")
    }
}

/**
 * Buffering - Conflation
 */
private fun test9_1() {
    // Conflation is one way to speed up processing when both the emitter and collector are slow. It does it by dropping emitted values.
    // When a flow represents partial results of the operation or operation status updates, it may not be necessary to process each value, but instead, only most recent ones. In this case, the conflate operator can be used to skip intermediate values when a collector is too slow to process them.
    fun simple(): Flow<Int> = flow<Int> {
//        log("simple:------->")
        for (i in 1..3) {
//            log("waiting for $i")
            delay(100) // pretend we are asynchronously waiting 100ms
            log("Emitting $i")
            emit(i)
        }
//        log("simple:<-------")
    }

    runBlocking {
//        log("runBlocking ----------->")
        val time = measureTimeMillis {
            simple()
                .conflate() // conflate emissions, don't process each one
                .collect { value ->
                    delay(300) // pretend we are processing it for 300ms
                    log("" + value)
                } // collecting coroutine : [main @coroutine#1]
        }
        log("Collected in $time ms") // 727 ms
//        log("runBlocking <-----------")
    }
}

/**
 * Buffering - Processing the latest value
 */
private fun test9_2() {
    // The other way is to cancel a slow collector and restart it every time a new value is emitted.
    fun simple(): Flow<Int> = flow<Int> {
//        log("simple:------->")
        for (i in 1..3) {
//            log("waiting for $i")
            delay(100) // pretend we are asynchronously waiting 100ms
            log("Emitting $i")
            emit(i)
        }
//        log("simple:<-------")
    }

    runBlocking {
//        log("runBlocking ----------->")
        val time = measureTimeMillis {
            simple()
                .collectLatest { value -> // cancel & restart on the latest value
                    delay(300) // pretend we are processing it for 300ms
                    log("" + value)
                } // collecting coroutine : [main @coroutine#1]
        }
        log("Collected in $time ms") // 671 ms
//        log("runBlocking <-----------")
    }
}


/**
 * Composing multiple flows
 */
/**
 * Composing multiple flows - Zip
 */
private fun test10_1() {
//    test10_1_example1()
    test10_1_example2()
}

private fun test10_1_example1() {
    // flows have a zip operator that combines the corresponding values of two flows:
    runBlocking {
        val nums = (1..3).asFlow()
        val strs = flowOf("one", "two", "three") // strings
        nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
            .collect { println(it) }
    }
}

private fun test10_1_example2() {
    runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("one", "two", "three").onEach { delay(400) }
        val startTime = System.currentTimeMillis()
        nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string with zip
            .collect { value ->
                // Use zip:
                // 1 -> one at 430 ms from start
                // 2 -> two at 823 ms from start
                // 3 -> three at 1233 ms from start
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }
}

/**
 * Composing multiple flows - Combine
 */
private fun test10_2() {
    runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("one", "two", "three").onEach { delay(400) }
        val startTime = System.currentTimeMillis()
        nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with combine
            .collect { value ->
                // combine:
                // 1 -> one at 436 ms from start
                //2 -> one at 629 ms from start
                //2 -> two at 848 ms from start
                //3 -> two at 934 ms from start
                //3 -> three at 1250 ms from start
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }
}

/**
 * Flattening flows
 */

private fun test11() {
    runBlocking {
        (1..3).asFlow()
            .map { requestFlow(it) } // returns a flow of flows (Flow<Flow<String>>)
            .collect { value ->
                // kotlinx.coroutines.flow.SafeFlow@2471cca7 : 1 : First
                //kotlinx.coroutines.flow.SafeFlow@2471cca7 : 1 : Second
                //kotlinx.coroutines.flow.SafeFlow@3834d63f : 2 : First
                //kotlinx.coroutines.flow.SafeFlow@3834d63f : 2 : Second
                //kotlinx.coroutines.flow.SafeFlow@1ae369b7 : 3 : First
                //kotlinx.coroutines.flow.SafeFlow@1ae369b7 : 3 : Second
                value.collect {
                    println("$value : $it")
                }
            }
    }
}

/**
 * Flattening flows - flatMapConcat
 */
private fun test11_1() {
    // flatMapConcat - They are the most direct analogues of the corresponding sequence operators. They wait for the inner flow to complete before starting to collect the next one
    runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapConcat { requestFlow(it) } // returns a flow of flows (Flow<Flow<String>>)
            .collect { value ->
                // 1 : First at 122 ms from start.
                //1 : Second at 630 ms from start.
                //2 : First at 735 ms from start.
                //2 : Second at 1239 ms from start.
                //3 : First at 1343 ms from start.
                //3 : Second at 1847 ms from start.
                println("$value at ${System.currentTimeMillis() - startTime} ms from start.")
            }
    }
}

/**
 * Flattening flows - flatMapMerge
 */
private fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i : First")
    delay(500)
    emit("$i : Second")
}

private fun test11_2() {
    // flatMapMerge - Another flattening operation is to concurrently collect all the incoming flows and merge their values into a single flow so that values are emitted as soon as possible. I
    runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapMerge { requestFlow(it) } // returns a flow of flows (Flow<Flow<String>>)
            .collect { value ->
                // 1 : First at 126 ms from start.
                //2 : First at 224 ms from start.
                //3 : First at 328 ms from start.
                //1 : Second at 629 ms from start.
                //2 : Second at 729 ms from start.
                //3 : Second at 837 ms from start.
                System.err.println("$value at ${System.currentTimeMillis() - startTime} ms from start.")
            }
    }
}

/**
 * Flattening flows - flatMapLatest
 */
private fun test11_3() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapLatest { requestFlow(it) } // returns a flow of flows (Flow<Flow<String>>)
            .collect { value ->
                // TODO:flatMapLatest
                // 1 : First at 128 ms from start.
                //2 : First at 258 ms from start.
                //3 : First at 362 ms from start.
                //3 : Second at 870 ms from start.
                println("$value at ${System.currentTimeMillis() - startTime} ms from start.")
            }
    }
}

/**
 * Flow exceptions
 */

/***
 * Flow exceptions - Collector try and catch
 */
private fun test12_1() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }
    runBlocking {
        try {
            // A collector can use Kotlin's try/catch block to handle exceptions
            simple().collect { value ->
                println(value)
                check(value <= 1) { "Collected $value" }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }
}

/***
 * Flow exceptions - Everything is caught
 */
private fun test12_2() {
    // Emitting 1
    //String 1
    //Emitting 2
    //Caught java.lang.IllegalStateException: Crashed on 2
    fun simple(): Flow<String> =
        flow {
            for (i in 1..3) {
                println("Emitting $i")
                emit(i)
            }
        }.map { value ->
            check(value <= 1) { "Crashed on $value" } // IllegalStateException occurred on value 3
            "String $value"
        }

    runBlocking {
        try {
            simple().collect { value -> println(value) }
        } catch (e: Throwable) {
            println("Caught $e")
        }
    }
}

/**
 * Exception transparency
 */

private fun test13() {
    // Emitting 1
    //String 1
    //Emitting 2
    //Caught java.lang.IllegalStateException: Crashed on 2
    fun simple(): Flow<String> =
        flow {
            for (i in 1..3) {
                println("Emitting $i")
                emit(i)
            }
        }.map { value ->
            check(value <= 1) { "Crashed on $value" } // IllegalStateException occurred on value 3
            "String $value"
        }


    runBlocking {
        simple()
            .catch { e -> emit("Caught $e") } // emit an exception
            .collect { value -> println(value) }
    }
}

/**
 * Exception transparency - Transparent catch
 */
private fun test13_1() {
    // Emitting 1
    //1
    //Emitting 2
    //Exception in thread "main" java.lang.IllegalStateException: Crashed on 2
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }

    runBlocking {
        simple()
            .catch { e -> println("Caught $e") } // doest catch downstream exception
            .collect { value ->
                check(value <= 1) { "Crashed on $value" }
                println(value)
            }
    }
}

/**
 * Exception transparency - Catching declaratively
 */
private fun test13_2() {

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

//    test9()
//    test9_1()
//    test9_2()

//    test10_1()
//    test10_2()

//    test11()
//    test11_1()
//    test11_2()
//    test11_3()

//    test12_1()
//    test12_2()

//    test13()
    test13_1()
}