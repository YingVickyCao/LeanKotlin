package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-aggregate.html
// Aggregate operations

/**
 * Aggregate operations
 */

private fun test1() {
    // aggregate operations – operations that return a single value based on the collection content.
    fun test1_example1() {
        // minOrNull() : return the smallest element.  On empty collections, they return null.
        // maxOrNull() : return the largest element.  On empty collections, they return null.
        // average() returns the average value of elements in the collection of numbers.
        // count() returns the number of elements in a collection.
        val numbers = listOf(6, 42, 10, 4)
        println(numbers.count()) // 4
        println(numbers.maxOrNull()) // 42
        println(numbers.minOrNull()) // 4
        println(numbers.average()) // 15.5
    }

    fun test1_example2() {
        // There are also functions for retrieving the smallest and the largest elements by certain selector function or custom Comparator:
        // maxByOrNull() and minByOrNull() take a selector function and return the element for which it returns the largest or the smallest value.
        //maxWithOrNull() and minWithOrNull() take a Comparator object and return the largest or smallest element according to that Comparator.
        //maxOfOrNull() and minOfOrNull() take a selector function and return the largest or the smallest return value of the selector itself.
        //maxOfWithOrNull() and minOfWithOrNull() take a Comparator object and return the largest or smallest selector return value according to that Comparator.
        val numbers = listOf<Int>(5, 42, 10, 4)
        println(numbers.minByOrNull { it % 3 }) // 42
        println(numbers.minByOrNull { it }) // 4

        val strings = listOf("one", "two", "three", "four")
        val longestString = strings.maxWithOrNull(compareBy { it.length })
        println(longestString) // three

        println(numbers.maxOfOrNull { it }) // 42
        println(strings.maxWith(compareBy { it.length })) // three

//        class Person(var name: String, var age: Int)
//
//        val list = listOf(
//            Person("Alice", 30),
//            Person("Bob", 25),
//            Person("Charline", 35)
//        )
        // TODO: maxOfWithOrNull example
    }

    fun test1_example3() {
        // sumOf()
        // Besides regular sum(), there is an advanced summation function sumOf() that takes a selector function and returns the sum of its application to all collection elements. Selector can return different numeric types: Int, Long, Double, UInt, and ULong (also BigInteger and BigDecimal on the JVM).
        val numbers = listOf(5, 42, 10, 4)
        println(numbers.sum()) // 61
        println(numbers.sumOf { it }) // 61
        println(numbers.sumOf { it * 2 }) // 122
        println(numbers.sumOf { it.toDouble() / 2 }) // 30.5
    }
//    test1_example1()
//    test1_example2()
    test1_example3()
}

/**
 * Fold and reduce
 */
private fun test2() {
    // unctions reduce() and fold() that apply the provided operation to the collection elements sequentially and return the accumulated result. The operation takes two arguments: the previously accumulated value and the collection element.

    fun test2_example1() {
        // fold() takes an initial value and uses it as the accumulated value on the first step
        val numbers = listOf(5, 2, 10, 4)
        println(numbers.reduce { sum, element -> sum + element }) // 21
        println(numbers.fold(0) { sum, element -> sum + element }) // 21

        //incorrect: the first element isn't doubled in the result
        println(numbers.reduce { sum, element -> sum + element * 2 }) // 37
        println(numbers.fold(0) { sum, element -> sum + element * 2 }) // 42
    }

    fun test2_example2() {
        // To apply a function to elements in the reverse order, use functions reduceRight() and foldRight(). They work in a way similar to fold() and reduce() but start from the last element and then continue to previous.
        val numers = listOf(5, 2, 10, 4)
        //incorrect: the last element isn't doubled in the result
        println(numers.foldRight(0) { element, sum -> sum + element * 2 }) // 42
        println(numers.reduceRight({ element, sum -> sum + element * 2 }))// 38
    }

    fun test2_example3() {
        // apply operations that take element indices as parameters.
        // All reduce operations throw an exception on empty collections. To receive null instead, use their *OrNull() counterparts:
        //reduceOrNull()
        //reduceRightOrNull()
        //reduceIndexedOrNull()
        //reduceRightIndexedOrNull()
        val numbers = listOf(5, 2, 10, 4)
        val sumEven = numbers.foldIndexed(0) { index: Int, sum: Int, element: Int -> if (index % 2 == 0) sum + element else sum }
        println(sumEven) // 15

        val sumEven2 = numbers.reduceIndexed { index: Int, sum: Int, element: Int -> if (index % 2 == 0) sum + element else sum }
        println(sumEven2) // 15

        val sumEventRight = numbers.foldRightIndexed(0) { index: Int, element: Int, sum: Int -> if (index % 2 == 0) sum + element else sum }
        println(sumEventRight) // 15

        // //incorrect: the last element included in the result
        val sumEventRight2 = numbers.reduceRightIndexed { index: Int, element: Int, sum: Int -> if (index % 2 == 0) sum + element else sum }
        println(sumEventRight2) // 19
    }

    fun test2_example4() {
        // For cases where you want to save intermediate accumulator values, there are functions runningFold() (or its synonym scan()) and runningReduce().
        val numbers = listOf(0, 1, 2, 3, 4, 5)
        // N = 6
        println(numbers.runningReduce { sum, item -> sum + item }) // [0, 1, 3, 6, 10, 15]
        // N = 7
        println(numbers.runningFold(0) { sum, item -> sum + item }) // [0, 0, 1, 3, 6, 10, 15]

        // If you need an index in the operation parameter, use runningFoldIndexed() or runningReduceIndexed().
        val number2 = listOf(1, 2, 3, 4, 5)
        println(number2.runningReduceIndexed { index: Int, sum: Int, element: Int -> sum + element }) // [1, 3, 6, 10, 15]
        println(number2.runningFoldIndexed(0) { index: Int, sum: Int, element: Int -> sum + element * (index + 1) }) // [0, 1, 5, 14, 30, 55]
        println(number2.runningFoldIndexed(0) { index: Int, sum: Int, element: Int -> sum + element }) // [0, 1, 3, 6, 10, 15]
    }
//    test2_example1()
//    test2_example2()
//    test2_example3()
    test2_example4()
}

fun main() {
//    test1()
    test2()
}