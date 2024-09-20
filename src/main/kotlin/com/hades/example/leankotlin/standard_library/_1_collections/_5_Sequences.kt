package com.hades.example.leankotlin.standard_library._1_collections

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f
import com.hades.example.leankotlin.concepts._4_class_and_object._16_delegated_properties.resourceDelegate

// https://kotlinlang.org/docs/sequences.html
// Sequences


/**
 * Construct
 */

/**
 * Construct - From elements
 */

private fun test1_1() {
    // Use sequenceOf() to create a sequence
    val numbersSequence = sequenceOf("four", "three", "two", "one")
    numbersSequence.forEach {
        print("$it  ") // four  three  two  one
    }
}

/**
 * Construct - From an Iterable
 */
private fun test1_2() {
    // Use asSequence() to create sequence
    val numbers = listOf("One", "two", "three")
    val numbersSequence = numbers.asSequence()
    numbersSequence.forEach {
        print("$it  ") // One  two  three
    }
}

/**
 * Construct - From a function
 */

private fun test1_3() {
    // create a sequence by building it with a function

    // you can specify the first element as an explicit value or a result of a function call. The sequence generation stops when the provided function returns null.
    val oddNumbers = generateSequence(2 /* specify the first element as explicit value */) { it + 2 } // it is the previous element
    println(oddNumbers.take(5).toList()) // [2, 4, 6, 8, 10]

    // To create a finite sequence with generateSequence(), provide a function that returns null after the last element you need.
    val oddNumberLessThan10 = generateSequence(1) {
        if (it < 8) it + 2 else null
    } // 1 3 5 7 9
    println(oddNumberLessThan10.toList()) // [1, 3, 5, 7, 9]
    println(oddNumberLessThan10.count()) // 5

}

/**
 * Construct - From chunks
 */

private fun test1_4() {
    // his function takes a lambda expression containing calls of yield() and yieldAll() functions. They return an element to the sequence consumer and suspend the execution of sequence() until the next element is requested by the consumer.
    val oddNumbers = sequence<Int> {
//        println("yield(1)")
        yield(1) // 1
//        println("yieldAll(listOf(3, 5))")
        yieldAll(listOf(3, 5)) // 3,5
//        println("yieldAll(generateSequence(7) { it + 2 })")
        yieldAll(generateSequence(7) { it + 2 }) // 7,9
    }
//    println("take")
    println(oddNumbers.take(5).toList()) // [1, 3, 5, 7, 9]

    // take
    //yield(1)
    //yieldAll(listOf(3, 5))
    //yieldAll(generateSequence(7) { it + 2 })
    //[1, 3, 5, 7, 9]
}

/**
 * Sequence operations
 */

/**
 * Sequence processing example
 */

/**
 * Sequence processing example - Iterable
 */
private fun test3_1() {
    fun test3_1_example1() {
        val words: List<String> = "The quick brown fox jumps over the lazy dog".split(" ")
        println(words) // [The, quick, brown, fox, jumps, over, the, lazy, dog]

        val filteredWords = words.filter {
            it.length > 3  // filter out the item if the length > 3
        }
        println(filteredWords) // [quick, brown, jumps, over, lazy]

        val lengthList = filteredWords.map {
            it.length
        }
        println(lengthList) // [5, 5, 5, 4, 4]

        val takeList = lengthList.take(4)
        println(takeList) // [5, 5, 5, 4]
    }

    fun test3_1_example2() {
        val words: List<String> = "The quick brown fox jumps over the lazy dog".split(" ")
        val lengthList = words.filter {
            println("filter: $it")
            it.length > 3
        }.map {
            println("length:${it.length}")
            it.length
        }.take(4)
        println("Lengths of first 4 words longer than 3 charts:")
        println(lengthList) // [5, 5, 5, 4]


        // filter: The
        //filter: quick
        //filter: brown
        //filter: fox
        //filter: jumps
        //filter: over
        //filter: the
        //filter: lazy
        //filter: dog
        //length:5
        //length:5
        //length:5
        //length:4
        //length:4
        //Lengths of first 4 words longer than 3 charts:
        //[5, 5, 5, 4]
    }

//    test3_1_example1()
    test3_1_example2()
}

/**
 * Sequence processing example - Sequence
 */

private fun test3_2() {
    val words: List<String> = "The quick brown fox jumps over the lazy dog".split(" ")
    // Convert List to a Sequence
    val wordsSequence = words.asSequence()
    val lengthSequence = wordsSequence.filter {
        println("filter: $it")
        it.length > 3
    }
        .map {
            println("length:${it.length}")
            it.length
        }
        .take(4)
    println("Lengths of first 4 words longer than 3 charts:")
    println(lengthSequence.toList()) // [5, 5, 5, 4]

    // Lengths of first 4 words longer than 3 charts:
    //filter: The
    //filter: quick
    //length:5
    //filter: brown
    //length:5
    //filter: fox
    //filter: jumps
    //length:5
    //filter: over
    //length:4
    //[5, 5, 5, 4]
}

fun main() {
//    test1_1()
//    test1_2()
//    test1_3()
//    test1_4()
//    test3_1()
    test3_2()
}