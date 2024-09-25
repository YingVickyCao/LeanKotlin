package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-filtering.html
// Filtering collections


/**
 * Filter by predicate
 */

private fun test1() {
    fun test1_example1() {
        // filter() for Set / List / Map
        val numbers = listOf("one", "two", "three", "four")
        val longerThan3 = numbers.filter {
            it.length > 3
        }
        println(longerThan3) // [three, four]

        val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
        val filteredMap = numbersMap.filter { (key, value) ->
            key.endsWith("1") && value > 10
        }
        // {key11=11}
        println(filteredMap)
    }

    fun test1_example2() {
        // filter() can only check the values of the elements.
        // filterIndexed() can check  the index and the value of an element.
        // filterNot() returns a list of elements for which the predicate yields false
        val numbers = listOf("one", "two", "three", "four")
        val filteredIdx = numbers.filterIndexed { index, s -> (index != 0) && (s.length < 5) }
        val filterNot = numbers.filterNot { it.length <= 3 }

        println(filteredIdx)// [two, four]
        println(filterNot) // [three, four]
    }

    fun test1_example3() {
        // filterIsInstance() returns collection elements of a given type.
        val numbers = listOf(null, 1, "two", 3.0, "four")
        numbers.filterIsInstance<String>().forEach {
            // TWO
            // FOUR
            println(it.uppercase())
        }
    }

    fun test1_example4() {
        // filterNotNull(): returns all non-nullable elements.
        val numbers = listOf(null, "one", "two", null)
        numbers.filterNotNull().forEach {
            println(it.length)
        }
    }

    test1_example4()
}

/**
 * Partition
 */
private fun test2() {
    // partition() – filters a collection by a predicate and keeps the elements that don't match it in a separate list.
    val numbers = listOf("one", "two", "three", "four")
    val (match, rest) = numbers.partition { it.length > 3 }
    println(match) // [three, four]
    println(rest) // [one, two]
}

/**
 * Test predicates
 */
private fun test3() {
    fun test3_example1() {
        // any() returns true if at least one element matches the given predicate.
        // none() returns true if none of the elements match the given predicate.
        // all() returns true if all elements match the given predicate.
        val numbers = listOf("one", "two", "three", "four")
        println(numbers.any { it.endsWith("e") }) // true
        println(numbers.none { it.endsWith("a") }) // true
        println(numbers.all { it.endsWith("e") }) // false
    }

    fun test3_example2() {
        // any() : check collection is not empty
        // none() : check collection is empty

        val numbers = listOf("one", "two", "three", "four")
        val empty = emptyList<String>()
        println(numbers.any()) // true
        println(empty.any()) // false

        println(numbers.none()) // false
        println(empty.none()) // true
    }
//    test3_example1()
    test3_example2()
}

fun main() {
//    test1()
//    test2()
    test3()
}
