package com.hades.example.leankotlin.standard_library._1_collections

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f

// https://kotlinlang.org/docs/collection-elements.html
// Retrieve single elements

/**
 * Retrieve by position
 */
private fun test1() {
    fun test1_example1() {
        // contains()
        // in
        // containsAll()
        val numbers = listOf("one", "two", "three", "four", "five", "six")
        // contains : return true if there is a collection element that equals the function argument.
        println(numbers.contains("four")) // true
        println("zero" in numbers) // false
        // Call contains in operation with the in keyword.
        println("two" in numbers) // true

        println(numbers.containsAll(listOf("four", "two"))) // true
        println(numbers.containsAll(listOf("one", "zero"))) // false
    }

    fun test1_example2() {
        // There are also useful aliases for retrieving the first and the last element of the collection: first() and last().
        // first()
        // last()
        val numbers = listOf("one", "two", "three", "four", "five")
        println(numbers.first()) // one
        println(numbers.last()) // five

        val numbers2 = listOf<String>()
        println(numbers2.first()) //  java.util.NoSuchElementException: List is empty.
    }

    fun test1_example3() {
        // To avoid exceptions when retrieving element with non-existing positions, use safe variations of elementAt():
        // elementAtOrNull() returns null when the specified position is out of the collection bounds.
        // elementAtOrElse() additionally takes a lambda function that maps an Int argument to an instance of the collection element type. When called with an out-of-bounds position, the elementAtOrElse() returns the result of the lambda on the given value.
        val numbers = listOf("one", "two", "three", "four", "five")
        println(numbers.elementAtOrNull(1)) // two
        println(numbers.elementAtOrNull(5)) // null

        println(numbers.elementAtOrElse(1) { index -> // two
            "The value for index $index is undefined."
        })
        println(numbers.elementAtOrElse(5) { index -> // The value for index 5 is undefined.
            "The value for index $index is undefined."
        })
    }
//    test1_example1()
//    test1_example2()
    test1_example3()
}

/**
 * Retrieve by condition
 */
private fun test2() {
    fun test2_example1() {
        // Functions first() and last() also let you search a collection for elements matching a given predicate.
        // first()
        // last()
        val numbers = listOf("one", "two", "three", "four", "five", "six")
        println(numbers.first() { it.length > 3 }) // three
        println(numbers.first() { it.length > 6 }) //  java.util.NoSuchElementException: Collection contains no element matching the predicate.
        println(numbers.last() { it.startsWith("f") }) // five
        println(numbers.last() { it.startsWith("g") }) // java.util.NoSuchElementException: List contains no element matching the predicate.
    }

    fun test2_example2() {
        // If no elements match the predicate, both functions throw exceptions. To avoid them, use firstOrNull() and lastOrNull() instead: they return null if no matching elements are found.
        // firstOrNull()
        // lastOrNull()
        val numbers = listOf("one", "two", "three", "four", "five", "six")
        println(numbers.firstOrNull() { it.length > 3 }) // three
        println(numbers.firstOrNull() { it.length > 6 }) // null
        println(numbers.lastOrNull() { it.startsWith("f") }) // five
        println(numbers.lastOrNull() { it.startsWith("g") }) // null
    }

    fun test2_example3() {
        // Use the aliases if their names suit your situation better:
        //find() instead of firstOrNull()
        //findLast() instead of lastOrNull()
        val numbers = listOf(1, 2, 3, 4)
        println(numbers.find { it % 2 == 0 }) // 2
        println(numbers.find { it > 10 }) // null

        println(numbers.firstOrNull() { it % 2 == 0 }) // 2
        println(numbers.firstOrNull { it > 10 }) // null

        println(numbers.findLast { it > 0 }) // 4
        println(numbers.findLast { it > 10 }) // null

        println(numbers.lastOrNull { it > 0 }) // 4
        println(numbers.lastOrNull { it > 10 }) // null
    }
//    test2_example1()
//    test2_example2()
    test2_example3()
}

/**
 * Retrieve with selector
 */
private fun test3() {
    // firstNotNullOf(): map the collection before retrieving the element
    // It combines 2 actions:
    // Maps the collection with the selector function
    // Returns the first non-null value in the result
    // firstNotNullOf() throws the NoSuchElementException if the resulting collection doesn't have a non-nullable element. Use the counterpart firstNotNullOfOrNull() to return null in this case.
    val list = listOf<Any>(0, "true", false)
    val longEnough = list.firstNotNullOf { item -> item.toString().takeIf { it.length > 4 } }
    println(longEnough) // false

//    val longEnough2 = list.firstNotNullOf { item -> item.toString().takeIf { it.length > 10 } }
//    println(longEnough2)  // java.util.NoSuchElementException: No element of the collection was transformed to a non-null value.

    val longEnough3 = list.firstNotNullOfOrNull { item -> item.toString().takeIf { it.length > 10 } }
    println(longEnough3) // null
}

/**
 * Random element
 */
private fun test4() {
    val numbers = listOf(1, 2, 3, 4)
    // 2
    // 1
    println(numbers.random())
}

/***
 * Check element existence
 */
private fun test5() {
    fun test5_example1() {
        // To check the presence of multiple instances together at once, call containsAll() with a collection of these instances as an argument.
        // contains()
        // in
        val numbers = listOf("one", "two", "three", "four", "five", "six")
        println(numbers.contains("four")) // true
        println("zero" in numbers) // false
        println("four" in numbers) // true
    }

    fun test5_example2() {
        // Additionally, you can check if the collection contains any elements by calling isEmpty() or isNotEmpty().
        val numbers = listOf("one", "two", "three", "four", "five", "six")
        println(numbers.isEmpty()) // false
        println(numbers.isNotEmpty()) // true

        val empty = emptyList<String>()
        println(empty.isEmpty()) // true
        println(empty.isNotEmpty()) // false
    }
//    test5_example1()
    test5_example2()
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
    test5()
}