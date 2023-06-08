package com.hades.example.leankotlin._8_class

// https://kotlinlang.org/docs/object-declarations.html#companion-objects
fun main() {
    val example = CompanionObjectExample();
    example.test()
}

class CompanionObjectExample {
    internal fun test() {

    }

    // Example 1 : Companion objects. START
    private class MyClass {
        companion object Factory {
            fun create(): MyClass = MyClass()
        }
    }
    // Example 1 : Companion objects. END
}