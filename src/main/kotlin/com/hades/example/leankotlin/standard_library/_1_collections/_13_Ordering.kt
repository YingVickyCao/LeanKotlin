package com.hades.example.leankotlin.standard_library._1_collections

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f

// https://kotlinlang.org/docs/collection-ordering.html
// Ordering


/**
 * Ordering
 */
private fun test1() {
    fun test1_example1() {
        // Comparable
        class Version(val major: Int, val minor: Int) : Comparable<Version> {
            override fun compareTo(other: Version): Int {
                return when {
                    this.major != other.major -> return this.major.compareTo(other.major)
                    this.minor != other.minor -> return this.minor.compareTo(other.minor)
                    else -> 0
                }
            }
        }
        println(Version(1, 2) > Version(1, 3)) // false
        println(Version(2, 0) > Version(1, 5)) // true
    }

    fun test1_example2() {
        // Comparator
        // custom order for a type, create a Comparator for it. Comparator
        val lengthComparator = Comparator { o1: String, o2: String -> o1.length - o2.length } //  arrange strings by their length
        println(listOf("aaa", "bb", "c").sortedWith(lengthComparator))
    }

    fun test1_example3() {
        // compareBy()
        // A shorter way to define a Comparator is the compareBy() function from the standard library.
        println(listOf("aaa", "bb", "c").sortedWith(compareBy { it.length })) // [c, bb, aaa]
        println(listOf("aaa", "bb", "c").sortedWith(compareBy { -it.length })) // [aaa, bb, c]
    }

//    test1_example1()
//    test1_example2()
    test1_example3()
}

/*
* Natural order
*/
private fun test2() {
    // The basic functions sorted() and sortedDescending() return elements of a collection sorted into ascending and descending sequence according to their natural order.
    // sorted()
    val numbers = listOf("one", "two", "three", "four")
    println("Sorted ascending:${numbers.sorted()}") // [four, one, three, two]
    println("Sorted descending:${numbers.sortedDescending()}") // [two, three, one, four]
}

/**
 * Custom orders
 */
private fun test3() {
    fun test3_example1() {
        // For sorting in custom orders or sorting non-comparable objects, there are the functions sortedBy() and sortedByDescending()
        val numbers = listOf("one", "two", "three", "four")

        val sortedNumbers = numbers.sortedBy { it.length }
        println("Sorted by length ascending $sortedNumbers") // [one, two, four, three]


        val sortedList = numbers.sortedByDescending { it.last() }
        println("Sorted by last letter descending : $sortedList") // [four, two, one, three]
    }

    fun test3_example2() {
        // sortedWith
        // To define a custom order for the collection sorting, you can provide your own Comparator. To do this, call the sortedWith() function passing in your Comparator. W
        val numbers = listOf("one", "two", "three", "four")
        val result = numbers.sortedWith(compareBy { it.length })
        println("Sorted by length ascending:$result") // [one, two, four, three]
    }
//    test3_example1()
    test3_example2()
}

/**
 * Reverse order
 */
private fun test4() {
    fun test4_example1() {
        // reversed(): retrieve collection in reversed order
        val numbers = listOf("one", "two", "three", "four")
        println(numbers.reversed()) // [four, three, two, one]
    }

    fun test4_example2() {
        // asReversed(): returns a reversed view of the same collection instance, so it may be more lightweight and preferable than reversed() if the original list is not going to change.
        val numbers = listOf("one", "two", "three", "four")
        val reversedNumbers = numbers.asReversed()
        println(reversedNumbers) // [four, three, two, one]
    }

    fun test4_example3() {
        // asReversed():
        // If the original list is mutable, all its changes reflect in its reversed views and vice versa.
        // However, if the mutability of the list is unknown or the source is not a list at all, reversed() is more preferable since its result is a copy that won't change in the future.
        val numbers = mutableListOf("one", "two", "three", "four")
        val reservedNumbers = numbers.asReversed()
        println(reservedNumbers) // [four, three, two, one]
        println(numbers) // [one, two, three, four]

        numbers.add("five")
        println(reservedNumbers) // [five, four, three, two, one]
        println(numbers) // [one, two, three, four, five]
    }
//    test4_example1()
//    test4_example2()
    test4_example3()
}

/*
* Random order
*/
private fun test5() {
    val numbers = listOf("one", "two", "three", "four")
    // [four, two, one, three]
    // [one, four, two, three]
    println(numbers.shuffled())
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
    test5()
}