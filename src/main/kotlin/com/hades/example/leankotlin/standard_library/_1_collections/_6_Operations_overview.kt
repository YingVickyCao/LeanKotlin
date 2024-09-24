package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-operations.html
// Operations overview


/**
 * Extension and member functions
 */

// Collection operation : member function / collection interface / extension functions

/**
 * Common operations
 */
private fun test2() {
    // Operations described on these pages return their results without affecting the original collection.
    fun test2_example1() {
        val numbers = listOf("one", "two", "three", "four")
        numbers.filter { it.length > 3 } // filter not affect the origin collection numbers
        println(numbers.size) // 4

        val longerThan3 = numbers.filter { it.length > 3 } // result stored in longerThan3
        println(longerThan3) // [three, four]
    }

    fun test2_example2() {
        // For certain collection operations, there is an option to specify the destination object.
        val numbers = listOf("one", "two", "three", "four")
        val filterResults = mutableListOf<String>()
        numbers.filterTo(filterResults) {
            it.length > 3
        }
        println(filterResults) // [three, four]

        numbers.filterIndexedTo(filterResults) { index, _ ->
            index == 0
        }
        println(filterResults) // [three, four, one]

        // For convenience, these functions return the destination collection back
        val result = numbers.mapTo(HashSet()) {
            it.length
        }
        println(result) // [3, 4, 5]
    }
//    test2_example1()
    test2_example2()
}

/**
 * Write operations
 */
private fun test3() {
    val numbers = mutableListOf("one", "two", "three", "four")
    val sortedNumbers = numbers.sorted() // sorted(): return a new collection
    println(numbers == sortedNumbers) // false

    numbers.sort()  // sort():sort a mutable collection in-place
    println(numbers == sortedNumbers) // true
}

fun main() {
//    test2()
    test3()
}