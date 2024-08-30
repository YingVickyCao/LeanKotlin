package com.hades.example.leankotlin.coroutines.CoroutineAndChannelsTutorial

import kotlinx.coroutines.*

/**
 * Cancelling coroutine execution
 */
private fun test1() {
    println("---->")
    runBlocking {
        val job = launch {
            repeat(1000) { i ->
                println("job: ${Thread.currentThread().name}: sleeping $i")
                delay(500L)
            }
        }
        println("${Thread.currentThread().name}: waiting")
        delay(1300L)
        println("${Thread.currentThread().name}: tried of waiting. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // isActive=true,isCancelled=false,isCompleted=false
        job.cancel() // cancel the job
        println("${Thread.currentThread().name}: called. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // job isActive=false,isCancelled=true,isCompleted=false
        job.join()  // wait for the job's completion
//        job.cancelAndJoin() // equal
        println("${Thread.currentThread().name}:quite. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // job isActive=false,isCancelled=true,isCompleted=true
    }
    println("<----")
}

/**
 * Cancellation is cooperative
 */
private fun test2() {
    // if a coroutine is working in a computation and does not check for cancellation, then it cannot be cancelled.
    test2_example1()
    test2_example2()
}

// job.cancelAndJoin() invoked, but still print sleeping,until the job completeds by itself after five iterations.
private fun test2_example1() {
    println("---->")
    runBlocking {
        println("runBlocking -->${Thread.currentThread().name}")
        val starTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            println("launch:--> ${Thread.currentThread().name}")
            var nextPrintTime = starTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
                //print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: ${Thread.currentThread().name}: sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
            println("launch:<-- ${Thread.currentThread().name}")
        }
//        println("${Thread.currentThread().name}: waiting")
        delay(1300L)
        println("${Thread.currentThread().name}: tried of waiting. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // isActive=true,isCancelled=false,isCompleted=false
        job.cancelAndJoin()
        println("${Thread.currentThread().name}:<--. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // job isActive=false,isCancelled=true,
        // isCompleted=true
    }
    println("<----")
}


// The same problem can be observed by catching a CancellationException and not rethrowing it:
private fun test2_example2() {
    println("---->")
    runBlocking {
        println("runBlocking -->${Thread.currentThread().name}")
        val starTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            repeat(5) { i -> // computation loop, just wastes CPU
                try {
                    println("job: ${Thread.currentThread().name}: sleeping $i")
                    delay(500)
                } catch (e: Exception) {
                    println(e)
                }
            }
            println("launch:<-- ${Thread.currentThread().name}")
        }
//        println("${Thread.currentThread().name}: waiting")
        delay(1300L)
        println("${Thread.currentThread().name}: tried of waiting. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // isActive=true,isCancelled=false,isCompleted=false
        job.cancelAndJoin()
        println("${Thread.currentThread().name}:<--. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // job isActive=false,isCancelled=true,
        // isCompleted=true
    }
    println("<----")
}


/**
 * Making computation code cancellable
 */
private fun test3() {
    test3_solution1()
//    test3_solution2()
}

//The first one is to periodically invoke a suspending function that checks for cancellation. There is a yield function that is a good choice for that purpose.=> yield()
private fun test3_solution1() {
    println("---->")
    runBlocking {
        println("runBlocking -->${Thread.currentThread().name}")
        val starTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            println("launch:--> ${Thread.currentThread().name}")
            var nextPrintTime = starTime
            var i = 0
            var isCancelled = false
            while (i < 5 && !isCancelled) { // computation loop, just wastes CPU
                try {
                    yield()
                } catch (ex: Exception) {
                    isCancelled = true
                }
                //print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: ${Thread.currentThread().name}: sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
            println("launch:<-- ${Thread.currentThread().name}")
        }
//        println("${Thread.currentThread().name}: waiting")
        delay(1300L)
        println("${Thread.currentThread().name}: tried of waiting. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // isActive=true,isCancelled=false,isCompleted=false
        job.cancelAndJoin()
        println("${Thread.currentThread().name}:<--. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // job isActive=false,isCancelled=true,
        // isCompleted=true
    }
    println("<----")
}

// The other one is to explicitly check the cancellation status.  => CoroutineScope.isActive
private fun test3_solution2() {
    println("---->")
    runBlocking {
        println("runBlocking -->${Thread.currentThread().name}")
        val starTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            println("launch:--> ${Thread.currentThread().name}")
            var nextPrintTime = starTime
            var i = 0
            while (isActive) { // cancellabled computation loop
                //print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: ${Thread.currentThread().name}: sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
            println("launch:<-- ${Thread.currentThread().name}")
        }
//        println("${Thread.currentThread().name}: waiting")
        delay(1300L)
        println("${Thread.currentThread().name}: tried of waiting. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // isActive=true,isCancelled=false,isCompleted=false
        job.cancelAndJoin()
        println("runBlocking <-- ${Thread.currentThread().name}. job isActive=${job.isActive},isCancelled=${job.isCancelled},isCompleted=${job.isCompleted}") // job isActive=false,
        // isCancelled=true,
        // isCompleted=true
    }
    println("<----")
}

/**
 * Closing resources with finally
 */
// Kotlin's use function execute their finalization actions normally when a coroutine is cancelled
private fun test4() {
    println("---->")
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: sleeping $i, isActive=${isActive}")
                    delay(500L) // CancellationException because the coroutine running this code is cancelled.
                }
            } finally {
                println("job: finally. isActive=${isActive}")
            }
        }
        delay(1300L)
        println("main: tried of waiting")
        job.cancelAndJoin()
        println("main:quite")
    }
    println("<----")
}

/**
 * Run non-cancellable block
 */
// isActive=true -> isActive=false -> isActive=true
private fun test5() {
    println("---->")
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: sleeping $i, isActive=${isActive}")
                    delay(500L) // CancellationException because the coroutine running this code is cancelled.
                }
            } finally {
                println("job: running finally before NonCancellable, isActive=${isActive}")
                withContext(NonCancellable) {
                    println("job: running finally isActive=${isActive}")
                    delay(1000L)
                    println("job: delayed for 1 sec because I'm non-cancellable. isActive=${isActive}")
                }
                println("job: finally. isActive=${isActive}")
            }
        }
        delay(1300L)
        println("main: tried of waiting. isActive=${isActive}")
        job.cancelAndJoin()
        println("main:quite. isActive=${isActive}")
    }
    println("<----")
}

/**
 * Timeout
 */
private fun test6() {
//    test6_v1()
//    test6_v2()
    test6_v3()
}

private fun test6_v1() {
    println("--->")
    runBlocking {
        val result = withTimeout(1300L) { //TimeoutCancellationException
            repeat(1000) { i ->
                println("sleeping $i")
                delay(500L)
            }
            "Done"
        }
        println(result)  // not invoked because TimeoutCancellationException throw by withTimeout
    }
    println("<---")
}

private fun test6_v2() {
    println("--->")
    runBlocking {
        try {
            val result = withTimeout(1300L) { //TimeoutCancellationException
                repeat(1000) { i ->
                    println("sleeping $i")
                    delay(500L)
                }
                "Done"
            }
            println(result)  // not invoked because TimeoutCancellationException throw by withTimeout
        } catch (ex: TimeoutCancellationException) {
            println(ex)
        }
    }
    println("<---")
}

private fun test6_v3() {
    println("--->")
    runBlocking {
        val result = withTimeoutOrNull(1300L) { //TimeoutCancellationException
            repeat(1000) { i ->
                println("sleeping $i")
                delay(500L)
            }
            "Done"
        }
        println(result)  // null
    }
    println("<---")
}


/**
 * Asynchronous timeout and resources
 */
private fun test7() {

}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
//    test6()
    test7()
}