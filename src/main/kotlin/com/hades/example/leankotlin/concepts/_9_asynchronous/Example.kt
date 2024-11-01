package com.hades.example.leankotlin.concepts._9_asynchronous

import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.concurrent.CompletableFuture


// https://kotlinlang.org/docs/async-programming.html
// Asynchronous programming techniques

// Refs -
// https://betulnecanli.medium.com/callbacks-futures-and-promises-in-kotlin-c0965e74373d

fun main() {
//    test1()
//    test2()
    test3()
}

private class Item {

}

private class Token {

}

private class Post(val token: Token, val item: Item) {

}

// GIVEN : function preparePost is heavy work function, and blocks the main thread
// How to solve this problem?
/**
 * Threading
 */
private fun test1() {
    fun test1_example1() {
        fun preparePost(): Token {
            println("preparePost,start")
            // makes a request and consequently blocks the main thread
//        println("thread name=" + Thread.currentThread().name + ",id=" + Thread.currentThread().id) // thread name=main,id=1
//        Thread.sleep(5000)

            var token: Token? = null
            val thread = Thread(Runnable {
                //        Thread.sleep(5000)
                token = Token()
            })
            thread.start()
            thread.join()
            println("Token@${System.identityHashCode(token)}")
            println("preparePost,end")
            return token!!
        }

        fun submitPost(token: Token, item: Item): Post {
            return Post(token, item)
        }

        fun processPost(post: Post) {
        }

        fun postItem(item: Item) {
            val token = preparePost()
            val post = submitPost(token, item)
            processPost(post)
        }
        postItem(item = Item())
    }

    test1_example1()
}

/**
 * Callbacks : pass a function or a lambda expression as an argument to a function that will be called when the asynchronous operation completes
 */
private fun test2() {
    fun test2_example1() {
        fun preparePostAsync(callback: (Token) -> Unit) {
            // make request and return immediately
            // arrange callback to be invoked later
            val token = Token()
            callback.invoke(token)
        }

        fun submitPostAsync(token: Token, item: Item, callback: (Post) -> Unit) {
            callback.invoke(Post(token, item))
        }

        fun processPost(post: Post) {
            println("processPost,Post@${System.identityHashCode(post)}")
        }

        fun postItem(item: Item) {
            preparePostAsync { token ->
                println("Token@${System.identityHashCode(token)}")
                submitPostAsync(token, item) { post ->
                    println("Post@${System.identityHashCode(post)}")
                    processPost(post)
                }
            }
        }
        postItem(item = Item())
    }

    fun test2_example2() {
        /**
         * @param callback callback function
         * The function calculates the sum of num1 and num2 and then calls the callback function, passing in the result as an argument.
         */
        fun performOperation(num1: Int, num2: Int, callback: (Int) -> Unit) {
            Thread() {
                val result = num1 + num2
                //callback.invoke(result)
                // or
                callback(result)
            }.start()
        }
        //  use this function with a callback
        performOperation(2, 3) { result ->
            println("The result is $result")  // The result is 5
        }

        fun handleResult(result: Int) {
            println("The result is $result") // The result is 5
        }
        // Also passing in a lambda function as the callback
        performOperation(2, 3, ::handleResult)
    }

    // Callback with multiple parameters:
    fun test2_example3() {
        fun processString(str: String, callback: (Int, Int) -> Unit) {
            val length = str.length
            val firstCharAscii = str[0].toInt()
            callback(length, firstCharAscii)
        }
        // str length is 3,ASCII value of first character: 97
        processString("abc") { length, ascii ->
            println("str length is $length,ASCII value of first character: $ascii")
        }
    }

    // Callback with a generic type parameter:
    fun test2_example4() {
        // also define a callback with a generic type parameter
        fun <T> processList(list: List<Int>, callback: (T) -> Unit) {
            val sum = list.sum()
            callback(sum as T)
        }
        processList<Int>(listOf(1, 2, 3, 4)) { sum ->
            println("The sum is $sum")
        }
    }

    // Callbacks with error handling:
    fun test2_example5() {
        //  pass an error as a parameter to the callback function.
        fun readFile(filePath: String, calllack: (String?, String?) -> Unit) {
            try {
                val file = File(filePath)
                val data = file.readText()
                calllack(data, null)
            } catch (ex: Exception) {
                calllack(null, ex.message)
            }
        }

        // Error reading file:abx.txt (No such file or directory)
        readFile("abx.txt") { data, error ->
            if (error != null) {
                println("Error reading file:$error")
            } else {
                println("Data reading from file:$data")
            }
        }
    }
//    test2_example1()
//    test2_example2()
//    test2_example3()
//    test2_example4()
    test2_example5()
}

/**
Futures, promises, and others
 */
private fun test3() {
    fun test3_example1() {
        fun preparePostAsync(): Token {
            // makes request and returns a promise that is completed later
            println("preparePost,start")
            Thread.sleep(5000)
            var token: Token? = null
            val thread = Thread(Runnable {
                token = Token()
            })
            thread.start()
            thread.join()
            println("Token@${System.identityHashCode(token)}")
            println("preparePost,end")
            return token!!
        }

        fun submitPost(token: Token, item: Item): Post {
            return Post(token, item)
        }

        fun processPost(post: Post) {
        }

        fun postItem(item: Item) {
//        val token = preparePost()
//        val post = submitPost(token, item)
//        processPost(post)
        }
        postItem(item = Item())
    }

    // Futures
    // Futures are a way to represent a value that may not yet be available. In Kotlin, futures are typically implemented using the CompletableFuture class, which allows you to perform operations on the future's value before it is available. You can use futures to execute a task asynchronously and then use the result when it becomes available.
    fun test3_example2() {
        val future = CompletableFuture.supplyAsync {
            // This simulates an expensive operation
            Thread.sleep(1000)
            "Hello, World"
        }
        future.thenAccept { result ->
            println("Async operation completed with result: $result")
        }
        println("Waiting for async operation to complete ...")
        // This is necessary because CompletableFuture runs its tasks in a thread pool, and the main thread of the program would exit immediately if we didn't wait for the async operation to complete.
        Thread.sleep(2000)

        // Print:
        // Waiting for async operation to complete ...
        //Async operation completed with result: Hello, World
    }

    // Futures
    fun test3_example3() {
        // an example of using CompletableFuture to perform two async operations in parallel and combine their results
        val future1 = CompletableFuture.supplyAsync {
            // This simulates an expensive operation
            Thread.sleep(1000)
            // 15,ForkJoinPool.commonPool-worker-1
//            System.err.println("${Thread.currentThread().id},${Thread.currentThread().name}")
            "Hello"
        }
        val future2 = CompletableFuture.supplyAsync {
            // This simulates another expensive operation
            Thread.sleep(1000)
            "world"
        }

        val combined = future1.thenCombine(future2) { result1, result2 ->
            "$result1 $result2"
        }
        combined.thenAccept { result ->
            println("Async operation completed with result: $result")
        }
        println("Waiting for async operation to complete ...")
        Thread.sleep(2000)

        // print:
        // Waiting for async operation to complete ...
        // Async operation completed with result: Hello world
    }

    // Promises
    // Promises are similar to futures, but they provide a way to produce a value instead of just consuming it. In Kotlin, you can use the Promise class to create a promise that will be resolved when the value becomes available. Promises are typically used in scenarios where you need to execute a long-running task and provide a result to another part of your code when it completes.
    fun test3_example4() {
        // an example of using CompletableFuture in Kotlin to create and use a promise
        val promise = CompletableFuture<String>()
        Thread {
            // Simulate an expensive operation
            Thread.sleep(1000)
            promise.complete("Hello, world")
        }.start()
        promise.thenAccept { result ->
            println("Async operation completed wih result: $result")
        }
        println("Waiting for async operation to complete ...")
        //  use a Thread.sleep method to wait for 2 seconds before the program exits. This is necessary because the main thread of the program would exit immediately if we didn't wait for the async operation to complete.
        Thread.sleep(2000)

        // print
        // Waiting for async operation to complete ...
        // Async operation completed wih result: Hello, world
    }

    // Promises : Promise is only available in Kotlin when targeting JS
    fun test3_example5() {
        // example of using the Promise interface from the kotlinx.coroutines.future package

//        runBlocking<Unit> {
//            val promise = Promise<String>
//        }
    }
//    test3_example1()
//    test3_example2()
//    test3_example3()
    test3_example4()
}


/**
 * Reactive extensions
 */
private fun test4() {

}

/**
 * Coroutines
 */
private fun test5() {

}
