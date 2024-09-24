package com.hades.example.leankotlin.standard_library._1_collections

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f

// https://kotlinlang.org/docs/collection-transformations.html
// Collection transformation operations


/**
 * Map
 */

private fun test1() {
    fun test1_example1() {
        val numbers = setOf(1, 2, 3)
        println(numbers.map { it * 3 }) // [3, 6, 9]
        println(numbers.mapIndexed { index, i -> index * i }) // [0, 2, 6]
    }

    fun test1_example2() {
        val numbers = setOf(1, 2, 3)
        // mapNotNull : filter out the nulls from the result
        println(numbers.mapNotNull { if (it == 2) null else it * 3 }) // [3, 9]
        println(numbers.map { if (it == 2) null else it * 3 }) // [3, null, 9]

        // mapIndexedNotNull : filter out the nulls from the result
        println(numbers.mapIndexedNotNull { index, i -> if (index == 0) null else index * i }) // [0, 2, 6]
        println(numbers.mapIndexed { index, i -> if (index == 0) null else index * i }) // [null, 2, 6]
    }

    fun test1_example3() {
        // Use mapKeys() / mapValues() for transforming maps
        // Both functions use the transformations that take a map entry as an argument, so you can operate both its key and value.
        val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
        println(numbersMap.mapKeys { it.key.uppercase() }) // {KEY1=1, KEY2=2, KEY3=3, KEY11=11}
        println(numbersMap.mapValues { it.value + it.key.length }) // {key1=5, key2=6, key3=7, key11=16}

    }
//    test1_example1()
//    test1_example2()
    test1_example3()
}

/**
 * Zip
 */
private fun test2() {
    // Zipping transformation is building pairs from elements with the same positions in both collections.

    fun test2_example1() {
        // When called on a collection or an array with another collection (or array) as an argument, zip() returns the List of Pair objects. The elements of the receiver collection are the first elements in these pairs.
        val colors = listOf("red", "green", "blue")
        val animals = listOf("fox", "bear", "wolf")
        println(colors zip animals) // [(red, fox), (green, bear), (blue, wolf)]
        println(colors.zip(animals)) // [(red, fox), (green, bear), (blue, wolf)]

        // If the collections have different sizes, the result of the zip() is the smaller size; the last elements of the larger collection are not included in the result.
        val twoAnimals = listOf("fox", "bear")
        println(colors.zip(twoAnimals)) // [(red, fox), (green, bear)]

    }

    fun test2_example2() {
        // You can also call zip() with a transformation function that takes two parameters: the receiver element and the argument element. I
        val colors = listOf("red", "green", "blue")
        val animals = listOf("fox", "bear", "wolf")
        println(colors.zip(animals) { color, animal ->
            "The ${animal.replaceFirstChar { it.uppercase() }} is $color"
        }) // [The Fox is red, The Bear is green, The Wolf is blue]
    }

    fun test2_example3() {
        // unzip
        val numberPairs: List<Pair<String, Int>> = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
        println(numberPairs) // [(one, 1), (two, 2), (three, 3), (four, 4)]

        val unzip: Pair<List<String>, List<Int>> = numberPairs.unzip()
        println(unzip) // ([one, two, three, four], [1, 2, 3, 4])
        println(numberPairs.unzip()) // ([one, two, three, four], [1, 2, 3, 4])
    }
//    test2_example1()
//    test2_example2()
    test2_example3()
}

/**
 * Associate
 */
private fun test3() {
    // Association transformations allow building maps from the collection elements and certain values associated with them.
    // associateWith
    fun test3_example1() {
        // The basic association function associateWith() creates a Map in which the elements of the original collection are keys, and values are produced from them by the given transformation function. If two elements are equal, only the last one remains in the map.
        run {
            val numbers = listOf("one", "two", "three", "four")
            val result: Map<String, Int> = numbers.associateWith { it.length }
            println(result) // {one=3, two=3, three=5, four=4}
            println(numbers.associateWith { it.length }) // {one=3, two=3, three=5, four=4}
        }

        run {
            val numbers = listOf("one", "two", "three", "two")
            val result: Map<String, Int> = numbers.associateWith { it.length }
            println(result) // {one=3, two=3, three=5}
            println(numbers.associateWith { it.length }) // {one=3, two=3, three=5}
        }
    }

    // associateBy()
    fun test3_example2() {
        // For building maps with collection elements as values, there is the function associateBy(). It takes a function that returns a key based on an element's value. If two elements' keys are equal, only the last one remains in the map.
        run {
            val numbers = listOf("one", "two", "three", "four")
            println(numbers.associateBy { it.first().uppercase() }) // {O=one, T=three, F=four}
            println(numbers.associateBy(keySelector = { it.first().uppercase() })) // {O=one, T=three, F=four}
        }
        run {
            val numbers = listOf("one", "two", "three", "two")
            println(numbers.associateBy { it.first().uppercase() }) // {O=one, T=two}
            println(numbers.associateBy(keySelector = { it.first().uppercase() })) // {O=one, T=two}
        }
    }

    // associate()
    fun test3_example3() {
        // Another way to build maps in which both keys and values are somehow produced from collection elements is the function associate().
        // Note that associate() produces short-living Pair objects which may affect the performance. Thus, associate() should be used when the performance isn't critical or it's more preferable than other options.
        val names = listOf("Alice Adams", "Brain Brown", "Clara Campbell")
        println(names.associate { name ->
            name.split(" ").let { it[0] to it[1] }
        }) // {Alice=Adams, Brain=Brown, Clara=Campbell}
    }
//    test3_example1()
//    test3_example2()
    test3_example3()
}

/**
 * Flatten
 */
private fun test4() {
    // flat access to nested collection elements useful.
    // flatten()
    fun test4_example1() {
        val numberSets: List<Set<Int>> = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
        println(numberSets) // [[1, 2, 3], [4, 5, 6], [1, 2]]
        // List<Set<Int>> -> List<Int>
        println(numberSets.flatten()) // [1, 2, 3, 4, 5, 6, 1, 2]
    }

    // flatMap()
    fun test4_example2() {
        // Another function – flatMap() provides a flexible way to process nested collections. It takes a function that maps a collection element to another collection.
        class StringContainer(val values: List<String>)

        val containers: List<StringContainer> = listOf(
            StringContainer(listOf("one", "two", "three")),
            StringContainer(listOf("four", "five", "six")),
            StringContainer(listOf("seven", "eight"))
        )
        val result: List<String> = containers.flatMap { it.values }
        println(result) // [one, two, three, four, five, six, seven, eight]
//        println(containers.flatMap { it.values }) // [one, two, three, four, five, six, seven, eight]
    }
//    test4_example1()
    test4_example2()
}

/**
 * String representation
 */
private fun test5() {
    // retrieve the collection content in a readable format, use functions that transform the collections to strings: joinToString() and joinTo()

    // joinToString(), joinTo()
    fun test5_example1() {
        val numbers = listOf("one", "two", "three", "four")
        println(numbers) // [one, two, three, four]
        println(numbers.joinToString()) // one, two, three, four

        val listString = StringBuilder("The list of numbers:")
        numbers.joinTo(listString)
        println(listString) // The list of numbers:one, two, three, four
    }

    fun test5_example2() {
        // To build a custom string representation, you can specify its parameters in function arguments separator, prefix, and postfix.
        val numbers = listOf("one", "two", "three", "four")
        val result = numbers.joinToString(separator = " | ", prefix = "start: ", postfix = ": end")
        println(result) // start: one | two | three | four: end
    }

    fun test5_example3() {
        // For bigger collections, specify the limit – a number of elements that will be included into result.
        val numbers = (1..100).toList()
        val result = numbers.joinToString(limit = 10, truncated = "<...>")
        // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, <...>
        println(result)
    }

    fun test5_example4() {
        // to customize the representation of elements themselves, provide the transform function.
        val numbers = listOf("one", "two", "three", "four")
        val result = numbers.joinToString { "Element:${it.uppercase()}" }
        // Element:ONE, Element:TWO, Element:THREE, Element:FOUR
        println(result)
    }
    test5_example4()
}

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
    test5()
}