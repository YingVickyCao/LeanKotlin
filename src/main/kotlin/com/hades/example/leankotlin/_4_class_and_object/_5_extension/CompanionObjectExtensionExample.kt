package com.hades.example.leankotlin._4_class_and_object._5_extension

// https://kotlinlang.org/docs/extensions.html#companion-object-extensions
fun main() {
    val example = CompanionObjectExtensionExample()
    example.test()
}

private class CompanionObjectExtensionExample {
    fun test() {
        // Example 1 : Companion object extensions
        run { MyClass.print() }
    }

    // Example 1 : Companion object extensions,START
    class MyClass {
        companion object { // will be called Companion
        }
    }

    fun MyClass.Companion.print() {
        println("companion")
    }
    // Example 1 : Companion object extensions,END

    // Example 2 : Scope of extensions
    // ...
}
