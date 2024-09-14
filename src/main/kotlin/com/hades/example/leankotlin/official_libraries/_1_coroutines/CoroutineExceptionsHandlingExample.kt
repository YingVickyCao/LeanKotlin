package com.hades.example.leankotlin.official_libraries._1_coroutines

import kotlinx.coroutines.*
import java.io.IOException

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
    test2_example1()
//    test2_example2()
//    test2_example3()
//    test2_example4()
//    test2_example5()
//    test2_example6()
//    test2_example7()
//    test2_example8()
}

@OptIn(DelicateCoroutinesApi::class)
private fun test2_example1() {
    println("---------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        joinAll(job, deferred)
    }
    println("<----------")

    // ---------->
    //CoroutineExceptionHandler got java.lang.AssertionError with suppressed []
    //<----------

    // CoroutineExceptionHandler is working on joinAll of GlobalScope.launch, and GlobalScope.async
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

    // CoroutineExceptionHandler is working on job.join() of GlobalScope.launch, but not working on deferred.await() of GlobalScope.async
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

    // CoroutineExceptionHandler is working on job.join() of GlobalScope.launch
    // try-catch is working on deferred.await() of GlobalScope.async
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

    // CoroutineExceptionHandler is working on job.join() of GlobalScope.launch, not working on deferred.await() of GlobalScope.async
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


    // CoroutineExceptionHandler is useful on joinAll of GlobalScope.async, but  exception not handled on CoroutineExceptionHandler.
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

    // CoroutineExceptionHandler is not working on  deferred.await() of GlobalScope.async
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

private fun test2_example8() {
    println("---------->")

    try {
        runBlocking {
            val job = launch() {
                throw AssertionError()
            }
            job.join()

            val deferred = async() {
                throw ArithmeticException()
            }
            try {
                deferred.await()
            } catch (ex: ArithmeticException) {
                println("Caught ArithmeticException")
            }
            println("Reached")
        }
    } catch (ex: Exception) {
        println("Caught $ex")
    }

    println("<----------")

    // ---------->
    //Exception in thread "main" java.lang.AssertionError

    // try-catch is not useful on launch / async in runBlocking
}

/**
 * Cancellation and exceptions
 */
private fun test3() {
//    test3_example1()
    test3_example2()
}

private fun test3_example1() {
    runBlocking {
        val job = launch {
            val child = launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled.")
                }
            }
            yield()
            println("Cancelling child")
            child.cancel()
            child.join()
            yield()
            println("Parent is not cancelled")
        }
        job.join()
    }

    // Cancelling child
    //Child is cancelled.
    //Parent is not cancelled
}

@OptIn(DelicateCoroutinesApi::class)
private fun test3_example2() {
    println("---------------->")

    // The original exception is handled by the parent only when all its children terminate, which is demonstrated by the following example.
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")

        }
        val job = GlobalScope.launch(handler) {
            launch { // the first child
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    withContext(NonCancellable) {
                        println("Children are cancelled, but exception is not handled util all the children terminate")
                        delay(100)
                        println("The first child finished it's non cancellable block")
                    }
                }
            }

            // In these examples, CoroutineExceptionHandler is always installed to a coroutine that is created in GlobalScope. It does not make sense to install an exception handler to a coroutine that is launched in the scope of the main runBlocking,
            // If a coroutine encounters an exception other than CancellationException, it cancels its parent with that exception. This behaviour cannot be overridden and is used to provide stable coroutines hierarchies for structured concurrency. CoroutineExceptionHandler implementation is not used for child coroutines.
            // The original exception is handled by the parent only when all its children terminate
            // the second child's ArithmeticException -> cancel parent GlobalScope.launch with ArithmeticException-> cancel first child -> when all its children terminate, original exception
            // ArithmeticException is handled by CoroutineExceptionHandler
            launch { // second child
                delay(10)
                println("Second child throws an exception")
                throw ArithmeticException()
            }
        }
        job.join()
    }
    println("<----------------")


    // ---------------->
    //Second child throws an exception
    //Children are cancelled, but exception is not handled util all the children terminate
    //The first child finished it's non cancellable block
    //CoroutineExceptionHandler got java.lang.ArithmeticException
    //<----------------
}

/**
 * Exceptions aggregation
 */

private fun test4() {
//    test4_example1()
    test4_example2()
}

@OptIn(DelicateCoroutinesApi::class)
private fun test4_example1() {
    // When multiple children of a coroutine fail with an exception, the general rule is "the first exception wins", so the first exception gets handled. All additional exceptions that happen after the first one are attached to the first exception as suppressed ones.

    println("---------------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) {
            launch {// it gets cancelled when another sibling fails with IOException
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    throw ArithmeticException() // the second exception
                }
            }

            launch { // second child
                delay(100)
                println("Second child throws an exception")
                throw IOException() // the first exception
            }
            delay(Long.MAX_VALUE)
            println("Unreached!")
        }
        job.join()
    }
    println("<----------------")

    //---------------->
    //Second child throws an exception
    //CoroutineExceptionHandler got java.io.IOException with suppressed [java.lang.ArithmeticException]
    //<----------------
}

@OptIn(DelicateCoroutinesApi::class)
private fun test4_example2() {
    // Cancellation exceptions are transparent and are unwrapped by default:

    println("---------------->")
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) {
            val innerJob = launch {//all this stack if coroutines will get cancelled
                launch {
                    launch {
                        throw IOException() // the origin exception
                    }
                }
            }
            try {
                innerJob.join()
            } catch (ex: CancellationException) {
                println("Rethrowing CancellationException with original cause: $ex")
                throw ex // cancellation exception is rethrown, yet the origin IOException gets to the handler
            }
        }
        job.join()
    }
    println("<----------------")

    //---------------->
    //Rethrowing CancellationException with original cause: kotlinx.coroutines.JobCancellationException: StandaloneCoroutine is cancelling; job=StandaloneCoroutine{Cancelling}@48bac10e
    //CoroutineExceptionHandler got java.io.IOException with suppressed []
    //<----------------
}


/**
 * Supervision
 */

/**
 * Supervision - Supervision job
 */
private fun test5_1() {
    runBlocking {
        val supervisorJob = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisorJob)) {
            // launch the first child - it's exception is ignored for this example (don't do this in the practice!)

            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
            }
            val firstChild = launch(handler) {
                println("The first child is failing")
                throw AssertionError("The first child is cancelled")
            }

            // launch the second child
            val secondChild = launch {
                firstChild.join()
                // Cancellation of the first child is not propagated to the second child
                println("The first child is ${firstChild.isCancelled} but the second child is still active")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The second child is cancelled because the supervisor was cancelled")
                }
            }
            //  wait until the first child fails & completes
            println("First child joined")
            firstChild.join()
            println("Cancelling the supervisor")
            supervisorJob.cancel()
            println("Second child joined")
            secondChild.join()
        }
    }

    // First child joined
    //The first child is failing
    //CoroutineExceptionHandler got java.lang.AssertionError: The first child is cancelled with suppressed []
    //The first child is true but the second child is still active
    //Cancelling the supervisor
    //Second child joined
    //The second child is cancelled because the supervisor was cancelled


    // Cancellation of the child is not propagated to the other child
    // Children are cancelled because the supervisor was cancelled
}

/**
 * Supervision - Supervision scope
 */
private fun test5_2() {
    // Instead of coroutineScope, we can use supervisorScope for scoped concurrency. It propagates the cancellation in one direction only and cancels all its children only if it failed itself. It also waits for all children before completion just like coroutineScope does.
    runBlocking {
        try {
            supervisorScope {
                val child = launch {
                    try {
                        println("The child is sleeping")
                        delay(Long.MAX_VALUE)
                    } finally {
                        println("The child is cancelled")
                    }
                }
                yield()
                // Give our child a change to execute and print using yield
                println("Throwing an exception from the scope")
                throw AssertionError()
            }
        } catch (e: AssertionError) {
            println("Caught AssertionError")
        }
    }

    // The child is sleeping
    //Throwing an exception from the scope
    //The child is cancelled
    //Caught AssertionError
}

/**
 * Supervision scope - Supervision scope - Exceptions in supervised coroutines
 */
private fun test5_2_1() {
    runBlocking {
        // Every child should handle its exceptions by itself via the exception handling mechanism.
        val handler = CoroutineExceptionHandler { _, exception -> println("CoroutineExceptionHandler got $exception") }
        supervisorScope {
            val child = launch(handler) {
                println("the child throws an exception")
                throw AssertionError()
            }
            val child2 = launch(handler) {
                println("the child2 waiting")
                delay(2000L)
                println("the child2 is completed")
            }
            println("The scope supervisorScope is completing")

        }
        println("The scope runBlocking is completed")
    }
    // The scope supervisorScope is completing
    //the child throws an exception
    //CoroutineExceptionHandler got java.lang.AssertionError
    //The scope runBlocking is completed
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5_1()
//    test5_2()
    test5_2_1()
}