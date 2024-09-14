package com.hades.example.leankotlin.official_libraries._1_coroutines

import kotlinx.coroutines.*

// Coroutine context and dispatchers
// https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html

/**
 * Dispatchers and threads
 */

private fun test1() {
    runBlocking {
        // When launch { ... } is used without parameters, it inherits the context (and thus dispatcher) from the CoroutineScope it is being launched from. In this case, it inherits the context of the main runBlocking coroutine which runs in the main thread.
        launch {
            println("main runBlocking : running on thread[${Thread.currentThread().name}]") // main
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined : running on thread[${Thread.currentThread().name}]")  //main
        }

        launch(Dispatchers.Default) {
            println("Defaults : running on thread thread[${Thread.currentThread().name}]")
        }

        launch(newSingleThreadContext("MyOwnedThread")) {
            println("newSingleThreadContext : running on thread[${Thread.currentThread().name}]")
        }

        launch(newFixedThreadPoolContext(1, "MyFixedThread")) {
            println("newFixedThreadPoolContext : running on thread[${Thread.currentThread().name}]")
        }
    }
}

/**
 * Unconfined vs confined dispatcher
 */
private fun test2() {
    println("------------>")
    runBlocking {
        println("runBlocking ------>")
        launch(Dispatchers.Unconfined) {
            println("Unconfined : working in thread(${Thread.currentThread().name})")
            delay(500)
            println("Unconfined : after delay in thread(${Thread.currentThread().name})")
        }

        launch {
            println("main runBlocking : working in thread(${Thread.currentThread().name})")
            delay(1000)
            println("main runBlocking : after delay in thread(${Thread.currentThread().name})")
        }
        println("runBlocking <------")
    }
    println("<------------")
}

/**
 * Jumping between threads
 */
// Run the following code with the -Dkotlinx.coroutines.debug JVM option (see debug):
private fun test3() {
    log("------------>")
    // Uses Use function to rlease threads created with  newSingleThreadContext
    newSingleThreadContext("Ctx1").use { ctx1 ->
        log("newSingleThreadContext Ctx1 ------->")
        newSingleThreadContext("Ctx2").use { ctx2 ->
            log("newSingleThreadContext Ctx2 ------->")
            runBlocking(ctx1) { //using runBlocking with an explicity specified context
                log("Started in ctx1")
                withContext(ctx2) { // change the context
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
            log("newSingleThreadContext Ctx2 <-------")
        }
        log("newSingleThreadContext Ctx1 <-------")
    }
    log("<------------")
}

fun log(msg: String) {
    println("[${Thread.currentThread().name}] $msg")
}


/**
 * Job in the context
 */
private fun test4() {
    runBlocking<Unit> {
        println("coroutineContext is ${coroutineContext}")  // coroutineContext is [BlockingCoroutine{Active}@71dac704, BlockingEventLoop@123772c4]
        println("Job is ${coroutineContext[Job]}")  // Job is BlockingCoroutine{Active}@41a4555e
        println("Job isActive = ${coroutineContext[Job]?.isActive == true}")  // Job is BlockingCoroutine{Active}@41a4555e
    }
}

/**
 * Children of a coroutine
 */
private fun test5() {
    println("----------->")
    runBlocking<Unit> {
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            // this spawns two other jobs
            launch(Job()) {
                println("Job1 : I run in my own job and execute independently.")
                delay(1000)
                println("Job1 : I am not effected by cancellation of the request.")
            }
            // and the other inherits the parent context
            launch {
                delay(100)
                println("job2 : I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled.")
            }
        }
        println("main: waiting")
        delay(500)
        println("main: tired of waiting")
        request.cancel() // cancel processing of the request
        println("main: who has survived request cancellation?")
        delay(1000)
    }
    println("<-----------")
}


/**
 * Parental responsibilities
 */
private fun test6() {
    println("------->")
    runBlocking<Unit> {
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            repeat(3) { i ->
                launch {
                    delay((i + 1) * 200L) // delay 200ms, 400ms,600ms
                    println("Coroutine $i is done")
                }

            }
            println("request: I am done and I don't explicitly join my children that are still active ")
        }
        println("request: waiting")
        request.join() // wait for the completion if the request, including all the children.
        println("Now processing of the request is completed.")
    }
    println("<-------")
}


/**
 * Naming coroutines for debugging
 */
private fun test7() {
    println("----------->")
    runBlocking {
        log("started main coroutine.")
        val v1 = async(CoroutineName("v1coroutine")) {
            delay(500)
            log("Computing v1")
            6
        }
        val v2 = async(CoroutineName("v2coroutine")) {
            delay(500)
            log("Computing v2")
            7
        }
        log("Answer of v1 * v2 = ${v1.await() * v2.await()}")
    }
    println("<-----------")
}

/**
 * Combining context elements
 */
private fun test8() {
    runBlocking {
        //  launch a coroutine with an explicitly specified dispatcher and an explicitly specified name at the same time
        launch(Dispatchers.Default + CoroutineName("test")) {
            println("I'm  working in thread ${Thread.currentThread().name} ")
        }
        launch(Dispatchers.Default) {
            println("I'm  working in thread ${Thread.currentThread().name} ")
        }
    }
}

/**
 * Coroutine scope
 */
class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default)  // set Default as test purpose
    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 200L)
                println("Coroutine $i is done")
            }
        }
    }
}

private fun test8_1() {
    println("----------->")
    runBlocking<Unit> {
        val activity = Activity()
        activity.doSomething()  // run the test function
        println("launched coroutines")
        delay(500L)
        println("Destroying activity")
        activity.destroy() // cancel all coroutines
        delay(1000L) // visually confirm that they don't work
    }
    println("<-----------")
}


/**
 * Coroutine scope - Thread-local data
 */
// For ThreadLocal, the asContextElement extension function is here for the rescue. It creates an additional context element which keeps the value of the given ThreadLocal and restores it every time the coroutine switches its context.
private val threadLocal = ThreadLocal<String?>()
private fun test10() {
    println("----------->")
    runBlocking {
        threadLocal.set("main")
        // TODO: ensurePresent https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/ensure-present.html
        // It's easy to forget to set the corresponding context element. The thread-local variable accessed from the coroutine may then have an unexpected value, if the thread running the coroutine is different. To avoid such situations, it is recommended to use the ensurePresent method and fail-fast on improper usages.
        println("pre-main, current thread :${Thread.currentThread()}, thread local value:${threadLocal.get()}")
        val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
            println("Launch start, current thread: ${Thread.currentThread()}, thread local value:${threadLocal.get()}")
            yield()
            println("after yield, current thread: ${Thread.currentThread()}, thread local value:${threadLocal.get()}")
        }
        job.join()
        println("post-main, current thread :${Thread.currentThread()}, thread local value:${threadLocal.get()}")
    }
    println("<-----------")

    // Use withContext to update the value of the thread-local in a coroutine, see asContextElement for more details.
    // TODO:asContextElement https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/as-context-element.html
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
//    test6()
//    test7()
//    test8()
//    test9()
    test10()
}