package com.hades.example.leankotlin._4_class_and_object._5_extension

// https://kotlinlang.org/docs/extensions.html#extension-properties
fun main() {
    val example = ExtensionPropertyExample()
    example.test()
}

class ExtensionPropertyExample {
    internal fun test() {
        var list = mutableListOf<Int>(1, 2, 3)
        println(list.lastIndex) // 2
    }

    // Example 1 : Extension properties, START
    // initializers are not allowed for extension properties. Their behavior can only be defined by explicitly providing getters/setters.
    val <T> List<T>.lastIndex: Int
        get() = size - 1

    // Example 1 : Extension properties, END
}