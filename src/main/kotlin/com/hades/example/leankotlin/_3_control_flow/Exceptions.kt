package com.hades.example.leankotlin._3_control_flow

import java.lang.IllegalArgumentException

fun main() {
    f1()
    f2()
    f3()
    f4()
}

// try - (catch or finally)
fun f1() {
    println("f1() --->")

    /*
    try:1
    catch:This is an exception
    finally
     */
    try {
        println("try:1")
        throwAnException()
        println("try:2")
    } catch (ex: Exception) {
        println("catch:" + ex.message)
        println("catch:" + ex.stackTrace.contentToString())
        println("catch:" + ex.cause)
    } finally {
        println("finally")
    }
    println("f1() <---")
}


// Try is an expression, so it can return value from try block or catch block, finally block not affect the result of the expression
fun f2() {
    println("f2() --->")
    /**
    f2_1 : result is 2
    f2_2 : result is 3
    f2_3 : result is 3
     */
    f2_1()
    f2_2()
    f2_3()
    println("f2() <---")
}

fun f2_1() {
    val result: Int? = try {
        2
    } catch (ex: Exception) {
        null
    }
    println("f2_1 : result is " + result)    // 2
}

fun f2_2() {
    val result: Int? = try {
        throwAnException()
        2
    } catch (ex: Exception) {
        3
    }
    println("f2_2 : result is " + result) // 3
}

fun f2_3() {
    val result: Int? = try {
        throwAnException()
        2
    } catch (ex: Exception) {
        3
    } finally {
        4
    }
    println("f2_3 : result is " + result) // 3
}


fun throwAnException() {
    throw Exception("This is an exception")
}

// throw is an expression in Kotlin, so can use it as part of an expression
fun f3() {
    println("f3() --->")
    /**
     * Li Ming
     */
    val name = "Li Ming"
    val s = name ?: throw IllegalArgumentException("Name required")
    println(s)
    println("f3() <---")
}

fun f4() {
    println("f4() --->")

    /**
     * java.lang.IllegalArgumentException: Name required
     */
    try {
        val name: String? = null
        val s = name ?: fail()
        println(s)
    } catch (ex: Exception) {
        println(ex)
    }


    val x = null        // x has the type  Nothing?
    println(x is Nothing?)

    val list = listOf(null) // list has the type  List<Nothing?>
    println(list is List<Nothing?>)

    println("f4() <---")
}

// Nothing type : throw expression has the type Nothing. This type has no values and is used to mark code locations that can never be reached
fun fail(): Nothing {
    throw IllegalArgumentException("Name required")
}
