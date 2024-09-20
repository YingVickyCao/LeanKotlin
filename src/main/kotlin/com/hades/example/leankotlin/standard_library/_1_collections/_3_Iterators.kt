package com.hades.example.leankotlin.standard_library._1_collections

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f

// https://kotlinlang.org/docs/iterators.html
// Iterators


/**
 * Iterators
 */
private fun test1() {
    fun test1_example1() {
        // Lopp list using iterator
        val numbers = listOf("one", "two", "three")
        val numbersIterator = numbers.iterator()
        while (numbersIterator.hasNext()) {
            // one
            // two
            // three
            print(numbersIterator.next() + " ") // one two three
        }
    }

    fun test1_example2() {
        // Loop list using for loop
        val numbers = listOf("one", "two", "three")
        for (item in numbers) {
            print("$item ") // one two three
        }
    }

    fun test1_example3() {
        // forEach function can let you automatically iterate a collection and execute the given code for each element.
        val numbers = listOf("one", "two", "three")
        numbers.forEach {
            print("$it  ") // one  two  three
        }
    }
//    test1_example1()
//    test1_example2()
    test1_example3()
}

/**
 * List iterators
 */
private fun test2() {
    // ListIterator supports iterating lists in both directions: forwards and backwards.
    // hasNext() / next(), hasPrevious() / previous()
    val numbers = listOf("one", "two", "three", "four")
    val listIterator = numbers.listIterator()
    while (listIterator.hasNext()) {
        print(listIterator.next() + " ") // one two three four
    }
    println()
    while (listIterator.hasPrevious()) {
        // Index:3,value:four
        // Index:2,value:three
        // Index:1,value:two
        // Index:0,value:one
        println("Index:${listIterator.previousIndex()},value:${listIterator.previous()}")
    }
}

/**
 * Mutable iterators
 */
private fun test3() {
    fun test3_example1() {
        val numbers = mutableListOf("one", "two", "three", "four")
        val mutableIterable: MutableIterator<String> = numbers.iterator()
        println(mutableIterable.next()) // one
        mutableIterable.remove()
        println(numbers) // [two, three, four]
    }

    fun test3_example2() {
        val numbers = mutableListOf("one", "four", "four")
        val mutableListIterable = numbers.listIterator()

        mutableListIterable.next()
        mutableListIterable.add("two")
        println(numbers) // [one, two, four, four]

        mutableListIterable.next()
        mutableListIterable.set("three")
        println(numbers) // [one, two, three, four]
    }
//    test3_example1()
    test3_example2()
}

fun main() {
//    test1()
//    test2()
    test3()
}