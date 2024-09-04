package com.hades.example.leankotlin.coroutines

import kotlinx.coroutines.*

// Coroutine exceptions handling
// https://kotlinlang.org/docs/exception-handling.html

// This section covers exception handling and cancellation on exceptions.

/**
 * Exception propagation
 */
private fun test1() {
//    test1_example1()
    test1_example2()
//    test1_example3()
}

@OptIn(DelicateCoroutinesApi::class)
private fun test1_example1() {
    println("----------->")
    runBlocking {
        println("runBlocking ----------->")
        val job = GlobalScope.launch { // root coroutine with launch
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }
        job.join()
        println("Joined failed job")

        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException() // Nothing is printed, relying on user to call await
        }
        try {
            deferred.await()
            println("Unreached")
        } catch (e: ArithmeticException) {
            println("Caught ArithmeticException")
        }
        println("runBlocking <-----------")
    }
    println("<-----------")

    // TODO: why console input is not same with Kotlin guide?
    // Current
    //Throwing exception from launch
    //Joined failed job
    //Throwing exception from async
    //Caught ArithmeticException
    //Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException

    // Expect:
    // Throwing exception from launch
    //Exception in thread "DefaultDispatcher-worker-1 @coroutine#2" java.lang.IndexOutOfBoundsException
    // Joined failed job
    //Throwing exception from async
    //Caught ArithmeticException
}

private fun test1_example2() {
    println("----------->")
    runBlocking {
        println("runBlocking ----------->")
        val job = GlobalScope.launch { // root coroutine with launch
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }
        // GlobalScope.launch throws uncaught exceptions, and try-cath is not useful.
        try {
            job.join()
        } catch (e: IndexOutOfBoundsException) {
            println("Caught IndexOutOfBoundsException")
        }
        println("Joined failed job")
        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException() // Nothing is printed, relying on user to call await
        }
        // GlobalScope.async throws catchable exceptions, and try-cath is useful.
        try {
            deferred.await()
            println("Unreached")
        } catch (e: ArithmeticException) {
            println("Caught ArithmeticException")
        }
        println("runBlocking <-----------")
    }
    println("<-----------")

    // Throwing exception from launch
    //Joined failed job
    //Throwing exception from async
    //Caught ArithmeticException
    // Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException
}

private fun test1_example3() {
    println("----------->")
    runBlocking {
        println("runBlocking ----------->")
        val job = GlobalScope.launch { // root coroutine with launch
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }
        try {
            job.join()
        } catch (e: IndexOutOfBoundsException) {
            println("Caught IndexOutOfBoundsException")
        }
        println("Joined failed job")

        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException() // Nothing is printed, relying on user to call await
        }
        deferred.await()
        println("Unreached")
        println("runBlocking <-----------")
    }
    println("<-----------")

    // Throwing exception from launch
    //Joined failed job
    //Throwing exception from async
    //Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException
    // Exception in thread "main" java.lang.ArithmeticException
}

private fun test1_example4() {
    println("----------->")
    runBlocking {
        println("runBlocking ----------->")
        val job = launch {
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }
        try {
            job.join()
        } catch (e: Exception) {
            println("Caught IndexOutOfBoundsException")
        }
        println("Joined failed job")
        val deferred = async {
            println("Throwing exception from async")
            throw ArithmeticException() // Nothing is printed, relying on user to call await
        }
        try {
            deferred.await()
            println("Unreached")
        } catch (e: ArithmeticException) {
            println("Caught ArithmeticException")
        }
        println("runBlocking <-----------")
    }
    println("<-----------")

    // ----------->
    //runBlocking ----------->
    //Throwing exception from launch
    //Caught IndexOutOfBoundsException
    //Joined failed job
    //Exception in thread "main" java.lang.IndexOutOfBoundsException

    // launch's IndexOutOfBoundsException -> it's parent runBlocking

    // It is possible to customize the default behavior of printing uncaught exceptions to the console. CoroutineExceptionHandler context element on a root coroutine can be used as a generic catch block for this root coroutine and all its children where custom exception handling may take place.
    // CoroutineExceptionHandler is invoked only on uncaught exceptions — exceptions that were not handled in any other way. In particular, all children coroutines (coroutines created in the context of another Job) delegate handling of their exceptions to their parent coroutine, which also delegates to the parent, and so on until the root, so the CoroutineExceptionHandler installed in their context is never used. In addition to that, async builder always catches all exceptions and represents them in the resulting Deferred object, so its CoroutineExceptionHandler has no effect either.
}

/**
 * CoroutineExceptionHandler
 */
private fun test2() {
//    test2_example1()
//    test2_example2()
//    test2_example3()
//    test2_example4()
    test2_example5()
//    test2_example6()
//    test2_example7()
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example1() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
//        joinAll(job, deferred)
    }
    println("<----------")

    // ---------->
    //CoroutineExceptionHandler got java.lang.AssertionError
    //<----------
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example2() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        // async builder always catches all exceptions and represents them in the resulting Deferred object, so its CoroutineExceptionHandler has no effect either.
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        job.join()
        deferred.await()
    }
    println("<----------")
    // ---------->
    //CoroutineExceptionHandler got java.lang.AssertionError
    //Exception in thread "main" java.lang.ArithmeticException
    //Process finished with exit code 1
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example3() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        job.join()

        val deferred = GlobalScope.async() { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        try {
            deferred.await()
        } catch (ex: ArithmeticException) {
            println("Caught ArithmeticException")
        }
        println("Reached")
    }
    println("<----------")


    // ---------->
    //CoroutineExceptionHandler got java.lang.AssertionError
    //Caught ArithmeticException
    //Reached
    //<----------
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example4() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        job.join()

        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        deferred.await()
        println("Reached")
    }
    println("<----------")

    // ---------->
    //CoroutineExceptionHandler got java.lang.AssertionError
    //Exception in thread "main" java.lang.ArithmeticException

}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example5() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { p, exception ->
            println("CoroutineExceptionHandler got $p, $exception")
        }
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        joinAll(deferred)
        println("Reached")
    }
    println("<----------")

    // ---------->
    //Reached
    //<----------
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example6() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        deferred.await()
        println("Reached")
    }
    println("<----------")

    // ---------->
    //Exception in thread "main" java.lang.ArithmeticException

    // CoroutineExceptionHandler is not working on  deferred.await()
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example7() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = launch(handler) {
            throw AssertionError()
        }
        val deferred = async(handler) {
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        job.join()

        try {
            deferred.await()
        } catch (ex: ArithmeticException) {
            println("Caught ArithmeticException")
        }
        println("Reached")
    }
    println("<----------")
    // ---------->
    //Exception in thread "main" java.lang.AssertionError

    // CoroutineExceptionHandler is only can be used for GlobalScope.launch, not useful for launch
}

/**
 * Cancellation and exceptions
 */
private fun test3() {

}

/**
 * Exceptions aggregation
 */
private fun test4() {

}

/**
 * Supervision
 */

private fun test5() {

}

/**
 * Supervision job
 */
private fun test6() {

}

/**
 * Supervision scope
 */
private fun test7() {

}

fun main() {
//    test1()
//    test1_example4()
    test2()
//    test3()
//    test4()
//    test5()
//    test6()
//    test7()
}