package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-plus-minus.html
// Plus and minus operators

/**
 * Plus and minus operators
 */
private fun test1() {
    // plus (+) and minus (-) operators are defined for collections.
    val numbers = listOf("one", "two", "three", "four")
    val five = numbers + "five"
    println(five) // [one, two, three, four, five]

    val minusList = numbers - listOf("three", "four")
    println(minusList) // [one, two]

    val minusList2 = numbers - listOf("three", "six")
    println(minusList2) // [one, two, four]

    // TODO: plus(+) / minus(-) fir map
    // TODO:  plusAssign (+=) and minusAssign (-=)
}

fun main() {
    test1()
}