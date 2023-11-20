package com.hades.example.leankotlin._4_class_and_object._5_sam

// https://kotlinlang.org/docs/fun-interfaces.html
fun main() {
    /**
     * Example 1 : SAM conversions
     */


    /**
     * Example 2 : Migration from an interface with constructor function to a functional interface
     */


    /**
     * Example 3 : Functional interfaces vs. type aliases
     */

}


/**
 * Example 1 : SAM conversions
 */
// An interface with only one abstract method is called a functional interface, or a Single Abstract Method (SAM) interface. The functional interface can have several non-abstract members but only one abstract member.

fun interface kRunable {
    fun invoke()
}


/**
 * Example 2 : Migration from an interface with constructor function to a functional interface
 */


/**
 * Example 3 : Functional interfaces vs. type aliases
 */