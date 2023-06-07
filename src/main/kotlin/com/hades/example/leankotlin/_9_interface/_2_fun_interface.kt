package com.hades.example.leankotlin._9_interface

// https://kotlinlang.org/docs/fun-interfaces.html

fun main() {
    val example = FunctionalInterface();
    example.test()
}

// An interface with only one abstract method is called a functional interface, or a Single Abstract Method (SAM) interface.
// The functional interface can have several non-abstract members but only one abstract member.
// To declare a functional interface in Kotlin, use the fun modifier.
class FunctionalInterface {
    fun test() {
        // Example 1 : functional interface
        run {
            val example = MyRunnable()
            example.invoke()    // invoke
            example.name()      // KRunnable
        }

        // Example 2 : SAM conversions
        run {
            println(isEvent.accept(9))  // false
            println(isEvent2.accept(9)) // false
        }

        // Example 3 : Migration from an interface with constructor function to a functional interface
        run {
//            Printer();
        }
    }

    // Example 1 : functional interface, START
    fun interface KRunnable {
        fun invoke()

        fun name() {
            println("KRunnable")
        }
    }

    class MyRunnable : KRunnable {
        override fun invoke() {
            println("invoke")

        }
    }
    // Example 1 : functional interface, END

    // Example 2 : SAM conversions, START
    // For functional interfaces, use SAM conversions by using lambda expressions
    fun interface IntPredicate {
        fun accept(i: Int): Boolean
    }

    val isEvent = object : IntPredicate {
        override fun accept(i: Int): Boolean {
            return i % 2 == 0
        }
    }

    val isEvent2 = IntPredicate { i -> i % 2 == 0 }
    // Example 2 : SAM conversions, END

    // Example 3 : Migration from an interface with constructor function to a functional interface
    interface Printer {
        fun print()
    }

    // Starting from 1.6.20, Kotlin supports callable references to functional interface constructors, which adds a source-compatible way to migrate from an interface with a constructor function
    // to a functional interface.
    // TODO
    fun Printer(block: () -> Unit): Printer = object : Printer {
        override fun print() {
            block()
        }
    }
}