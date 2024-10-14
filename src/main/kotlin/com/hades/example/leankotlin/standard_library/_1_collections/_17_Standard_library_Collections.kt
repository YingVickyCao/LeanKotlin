package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/set-operations.html
// Set-specific operations


/**
 * Set-specific operations
 */

private fun test1() {

    fun test1_example1() {
        // union() :merge two collection into one
        val numbers = setOf("one", "two", "three")
        println(numbers union setOf("four", "five")) // [one, two, three, four, five]
        println(setOf("four", "five") union numbers) // [four, five, one, two, three]
    }

    fun test1_example2() {
        // intersect() : find an intersection between two collections.
        // subtract() : find collection elements not present in another collection.
        val numbers = setOf("one", "two", "three")
        println(numbers intersect setOf("two", "one")) // [one, two]
        println(numbers subtract setOf("three", "four")) // [one, two]
        println(numbers subtract setOf("four", "three")) // [one, two]
        println(setOf("four", "three") subtract numbers) // [four]
    }

    fun test1_example3() {
        // union(): also to find the elements present in either one of two collection but not in their intersection
        val number = setOf("one", "two", "three")
        val number2 = setOf("three", "four")

        // merge differences
        println((number - number2) union (number2 - number)) // [one, two, four]
    }

    fun test1_example4() {
        // also apply union() / intersect() / subtract() to list, however their result is always a Set
        val list1 = listOf(1, 1, 2, 3, 5, 8, -1)
        val list2 = listOf(1, 1, 2, 2, 3, 5)

        // result of intersecting two lists is a set
        println(list1 intersect list2) // [1, 2, 3, 5]

        // equal element are merged into one
        println(list1 union list2) // [1, 2, 3, 5, 8, -1]
    }
    test1_example1()
    test1_example2()
    test1_example3()
    test1_example4()
}

fun main() {
    test1()
}