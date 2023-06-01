package com.hades.example.leankotlin._7_functions

/**
 * Infix functions:
 * member functions and extension with a single param.
 */
fun main() {
    // 1 Define an infix function on Int
    infix fun Int.times(str: String) = str.repeat(this)
    println(2 times "bye ") // bye bye
    // is equal to
    println("bye ".repeat(2))   // bye bye

    // 2 Create a pair using infix function on from standard lib
    val pair = "A" to "BC"
    println(pair)           // (A, BC)
    println(pair.first)     // A
    println(pair.second)    // BC

    val pair2 = "A" to "BC" to "d"
    println(pair2)              // ((A, BC), d)
    println(pair2.first.first)  // A
    println(pair2.first.second) // BC
    println(pair2.second)       // d

    // 3 Custom a pair
    infix fun String.onto(other: String) = Pair(this, other)
    var myPair = "Hello" onto "World"
    println(myPair)         // (Hello, World)
    println(myPair.first)   // Hello
    println(myPair.second)  // World
    println(myPair.second.onto("XY")) // (World, XY)
}