package com.hades.example.leankotlin._4_class_and_object._6_visibility_modifiers

// https://kotlinlang.org/docs/visibility-modifiers.html#constructors
fun main() {
    /**
     * Example 1 :  Packages
     */

    /**
     * Example 2 :  Class members
     */

    /**
     * Example 3 :  Modules
     */
}

/**
 * Example 1 :  Packages
 */
// There are four visibility modifiers in Kotlin: private, protected, internal, and public. The default visibility is public.

// Functions, properties, classes, objects, and interfaces can be declared at the "top-level" directly inside a package:
fun baz() {}
class Bar {}

// default is public.
// private : visible inside the file that contains the declaration
// internal : visible in the same module
// protected : not available for top-level declarations


/**
 * Example 2 :  Class members
 */

/**
 * Example 3 :  Modules
 */