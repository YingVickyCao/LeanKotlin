package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-parts.html
// Retrieve collection parts


/**
 * Slice
 */
private fun test1() {
    // slice() returns a list of the collection elements with given indices. The indices may be passed either as a range or as a collection of integer values.
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    println(numbers.slice(1..3)) // [two, three, four]
    println(numbers.slice(0..4 step 2)) // 0, 2, 4 => [one, three, five]
    println(numbers.slice(setOf(3, 5, 0))) // [four, six, one]
}

/**
 * Take and drop
 */
private fun test2() {
    fun test2_example1() {
        // take(): get the specified number of elements starting from the first
        // takeLast(): get the last elements.
        // drop() : take all the elements except a give number of  first
        // dropLast() : take all the elements except a give number of  last
        val numbers = listOf("one", "two", "three", "four", "five", "six")
        println(numbers.take(3)) // [one, two, three]
        println(numbers.takeLast(3)) // [four, five, six]
        println(numbers.drop(1)) // [two, three, four, five, six]
        println(numbers.dropLast(5)) // [one]
    }

    fun test2_example2() {
        // use predicates to define the number of elements for taking or dropping
        // takeWhile() is take() with a predicate: it takes the elements up to but excluding the first one not matching the predicate
        // takeLastWhile() is similar to takeLast(): it takes the range of elements matching the predicate from the end of the collection.
        // dropWhile() is the opposite to takeWhile() with the same predicate: it returns the elements from the first one not matching the predicate to the end.
        // dropLastWhile() is the opposite to takeLastWhile() with the same predicate: it returns the elements from the beginning to the last one not matching the predicate.

        val numbers = listOf("one", "two", "three", "four", "five", "six")

        // takeWhile()
        println(numbers.takeWhile { !it.startsWith('f') }) // [one, two, three]
        // takeLastWhile()
        println(numbers.takeLastWhile { it != "three" }) // [four, five, six]
        // dropWhile()
        println(numbers.dropWhile { it.length == 3 }) // [three, four, five, six]
        // dropLastWhile()
        println(numbers.dropLastWhile { it.contains('i') }) // [one, two, three, four]
    }
//    test2_example1()
    test2_example2()
}

/**
 * Chunked
 */

private fun test3() {
    // chunked() : To break a collection into parts of a given size.
    fun test3_example1() {
        val numbers: List<Int> = (0..13).toList()
        val result: List<List<Int>> = numbers.chunked(3)
        println(result) // [[0, 1, 2], [3, 4, 5], [6, 7, 8], [9, 10, 11], [12, 13]]
    }

    fun test3_example2() {
        // You can also apply a transformation for the returned chunks right away
        val numbers: List<Int> = (0..13).toList()
        println(numbers.chunked(3) { it.sum() }) // [3, 12, 21, 30, 25]
    }
//    test3_example1()
    test3_example2()
}

/**
 * Windowed
 */
private fun test4() {
    fun test4_example1() {
        // windowed(): retrieve all possible ranges of the collection elements of a given size.
        val numbers = listOf("one", "two", "three", "four", "five")
        val result: List<List<String>> = numbers.windowed(3) // [[one, two, three], [two, three, four], [three, four, five]]
        println(result)

        val numbers2 = listOf("one", "two", "three", "four", "five", "six")
        val result2: List<List<String>> = numbers2.windowed(3) // [[one, two, three], [two, three, four], [three, four, five], [four, five, six]]
        println(result2)
    }

    fun test4_example2() {
        // step defines a distance between first elements of two adjacent windows.
        // partialWindows includes windows of smaller sizes that start from the elements at the end of the collection.
        val numbers = (1..10).toList()
        println(numbers.windowed(3, step = 2, partialWindows = true)) // [[1, 2, 3], [3, 4, 5], [5, 6, 7], [7, 8, 9], [9, 10]]
        println(numbers.windowed(3, step = 2, partialWindows = false)) // [[1, 2, 3], [3, 4, 5], [5, 6, 7], [7, 8, 9]]
    }

    fun test4_example3() {
        // you can apply a transformation to the returned ranges right away. To do this, provide the transformation as a lambda function when calling windowed().
        val numbers = (1..10).toList()
        println(numbers.windowed(3, step = 2, partialWindows = true)) // [[1, 2, 3], [3, 4, 5], [5, 6, 7], [7, 8, 9], [9, 10]]
        println(numbers.windowed(3)) // [[1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5, 6], [5, 6, 7], [6, 7, 8], [7, 8, 9], [8, 9, 10]]
        println(numbers.windowed(3) { it.sum() }) // [6, 9, 12, 15, 18, 21, 24, 27]
    }

    fun test4_example4() {
        // To build two-element windows, there is a separate function - zipWithNext(). It creates pairs of adjacent elements of the receiver collection.
        val numbers = listOf("one", "two", "three", "four", "five")
        println(numbers.zipWithNext()) // [(one, two), (two, three), (three, four), (four, five)]
        println(numbers.zipWithNext() { s1, s2 -> s1.length > s2.length }) // [false, false, true, false]
    }
//    test4_example1()
//    test4_example2()
//    test4_example3()
    test4_example4()
}

fun main() {
//    test1()
//    test2()
//    test3()
    test4()
}
