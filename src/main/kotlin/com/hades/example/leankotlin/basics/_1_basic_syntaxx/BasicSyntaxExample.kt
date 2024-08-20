/**
 *Package definition and imports
 */
package com.hades.example.leankotlin.basics._1_basic_syntaxx

import com.hades.example.leankotlin.concepts._4_class_and_object._1_class.Shape
import com.hades.example.leankotlin.concepts._4_class_and_object._7_extension.lastIndex

/**
 * Program entry point: main function
 */
fun main() {
//fun main(args: Array<String>) {
//    println(args.contentToString())
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
//    test6()
//    test7()
//    test8()
//    test9()
//    test10()
//    test11()
//    test12()
//    test13()
    test14()
}

/**
 * Print to the standard output
 */
private fun test1() {
    println("Hello world")
    println(10)
    println()
}

/**
 *  Read from the standard input
 *  Ref : [com.hades.example.leankotlin.standard_library._2_read_standard_input.ReadStandardInputExample]
 */
private fun test2() {
    println("Enter:")
    val input = readln()
    print("Input:")
    print(input)
}

/**
 * Functions
 * Ref - [com.hades.example.leankotlin.concepts._5_functions]
 */
private fun test3() {
    run {
        fun sum(a: Int, b: Int): Int {
            return a + b
        }
        println(sum(1, 3))
    }
    // function body can be an expression
    run {
        //        fun sum(a: Int, b: Int): Int = a + b // ok
        fun sum(a: Int, b: Int) = a + b // ok: Its return type is inferred:
        println(sum(1, 3))
    }

    // A function can return no meaning value
    run {
        fun printSum(a: Int, b: Int): Unit {
            println("sum of $a and $b is ${a + b}")
        }
        printSum(1, 3)
    }

    // Unit return value can be omitted
    run {
        fun printSum(a: Int, b: Int) {
            println("sum of $a and $b is ${a + b}")
        }
        printSum(1, 3)
    }
}

/**
 * Variables
 * Ref -  Properties [com.hades.example.leankotlin.concepts._4_class_and_object._3_properties]
 */
private fun test4() {
    // val: immutable and read-only variables
    val x: Int = 5

    // var: mutable and can be reassigned variables
    var y: Int = 5
    y = 7

    // automatically
    val z = 5
}

/**
 * Creating classes and instances
 */
private fun test5() {
    // default constructor with parameters
    class Rectangle(val height: Double, val length: Double) : Shape() {
        val perimeter = (height + length) * 2
    }

    val rectangle: Rectangle = Rectangle(2.0, 5.0)
    println("perimeter is ${rectangle.perimeter}")
    println("width is ${rectangle.height}")
    println("height is ${rectangle.length}")

    // Classes are final by default; to make a class inheritable, mark it as open:
    open class Shape
}

/**
 * Comments
 */
/**
 * multi-line (block) comments
 */
// single-line comment

/**
 * String templates
 */
private fun test6() {
    var a = 1
    val s1 = "a is $a"
    println(s1)

    a = 2
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)
}

/**
 * Conditional expressions
 */
private fun test7() {
    run {
        fun maxOf(a: Int, b: Int): Int {
            if (a > b) {
                return a
            } else {
                return b
            }
        }

        val result = maxOf(2, 4)
        println(result)
    }

    // 'Return' can be lifted out of 'if'
    run {
        fun maxOf(a: Int, b: Int): Int {
            return if (a > b) {
                a
            } else {
                b
            }
        }

        val result = maxOf(2, 4)
        println(result)
    }

    // In Kotlin, if can also be used as an expression:
    run {
        fun maxOf(a: Int, b: Int): Int = if (a > b) a else b
        val result = maxOf(2, 4)
        println(result)
    }
}

/**
 * for loop
 */
private fun test8() {
    val items = listOf("apple", "kiwifruit")
    for (item in items) {
        println(item)
    }

    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }
}

/**
 * while loop
 */
private fun test9() {
    val items = listOf("apple", "kiwifruit")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }
}

/**
 * when expression
 */
private fun test10() {
    fun describe(obj: Any): String =
        when (obj) {
            1 -> "One"
            "hello" -> "world"
            is Long -> "Long"
            !is String -> "Not a string"
            else -> {
                "Unknown"
            }
        }
    println(describe(1))
    println(describe("hello"))
    println(describe(10L))
    println(describe(false))
}


/**
 * Ranges
 */
private fun test11() {
    // Check if a number is within a range using in operator:
    fun isRange(x: Int) {
        val y = 9
        if (x in 1..y) {
            println(" $x fits in range [1,$y]")
        }
    }
    isRange(0)
    isRange(1)
    isRange(2)
    isRange(8)
    isRange(9)
    isRange(10)

    // Check if a number is out of range:
    val list = listOf("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {// [0,list.lastIndex]
        println("-1 is out of range")
    }
    if (list.size !in list.indices) { // [0,list.lastIndex]
        println("list size is out of valid list indices range, too")
    }

    // Iterate over a range:
    for (x in 1..5) { // [1,5] : 1 2 3 4 5
        print("$x ")
    }

    println()
    // over a progression
    for (x in 1..10 step 2) { // [1,10] :1 3 5 7 9
        print("$x ")
    }
    println()

    for (x in 9 downTo 2 step 3) { // [9,2]: 9 6 3
        print("$x ")
    }
}

/**
 * Collections
 */

private fun test12() {
    // Iterate over a collection
    run {
        val items = arrayOf(1, 3)
        for (item in items) {
            println(item)
        }
    }

    // Check if a collection contains an object using in operator:
    run {
        val items = arrayOf("orange", "apple")
        when {
            "orange" in items -> println("juicy")
            "apple" in items -> println("apple in fine too")
        }
    }

    // Use lambda expressions to filter and map collections:
    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    val filtered = mutableListOf<String>()
    fruits.filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.uppercase() }
        .forEach {
            println(it)
            filtered.add(it)
        }
    println(filtered)
}

/**
 * Nullable values and null checks
 */
private fun test13() {
    fun parseInt(str: String): Int? { // return nullable int
        return try {
            str.toInt()
        } catch (ex: Exception) {
            null
        }
    }

    run {
        fun printProduct(arg1: String, arg2: String) {
            val x = parseInt(arg1)
            val y = parseInt(arg2)
            if (x != null && y != null) {
                println(x * y)
            } else {
                println("$arg1 or $arg2 is not a number")
            }
        }
        printProduct("2", "5")
        printProduct("2", "abc")
    }

    // OR
    run {
        fun printProduct(arg1: String, arg2: String) {
            val x = parseInt(arg1)
            val y = parseInt(arg2)
            if (x == null) {
                println("Wrong number format in arg1:$arg1")
                return
            }
            if (y == null) {
                println("Wrong number format in arg2:$arg2")
                return
            }
            println(x * y)
        }

        printProduct("2", "5")
        printProduct("2", "abc")
    }
}

/**
 * Type checks and automatic casts
 */
private fun test14() {
    // is
    run {
        fun getStringLength(obj: Any): Int? {
            if (obj is String) {
                // obj is automatically cast to String in this branch
                return obj.length
            }
            // obj is still of type Any outside of the type-check branch
            return null
        }
        println(getStringLength("abc"))
        println(getStringLength(10))
    }
    // OR
    run {
        fun getStringLength(obj: Any): Int? {
            if (obj !is String) {
                return null
            }
            // obj is automatically cast to String in this branch
            return obj.length
        }
        println(getStringLength("abc"))
        println(getStringLength(10))
    }

    // OR
    run {
        fun getStringLength(obj: Any): Int? {
            // obj is automatically cast to String on the right-hand side of &&
            if (obj is String && obj.length > 0) {
                return obj.length
            }
            return null
        }
        println(getStringLength("abc"))
        println(getStringLength(10))
    }
}