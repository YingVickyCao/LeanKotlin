package com.hades.example.leankotlin._8_class

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

// Enum classes
// https://kotlinlang.org/docs/enum-classes.html
fun main() {
    val example = EnumClassExample();
    example.test()
}

class EnumClassExample {
    fun test() {
        // Example 1 : enum classes is the implementation of type-safe enums
        run {
//            val direction1 = Direction.SOUTH
//            val direction2 = Direction.EAST
//            val direction3 = Direction.SOUTH
//            println(direction1.hashCode())      // 1435804085
//            println(direction2.hashCode())      // 1784662007
//            println(direction3.hashCode())      // 1435804085
//
//            println(direction1.toString())      // SOUTH
//            println(direction2.toString())      // EAST
//            println(direction3.toString())      // SOUTH
        }

        // Example 2: Anonymous classes
        run {
//            val state1 = ProtocolState.WAITING
//            val state2 = ProtocolState.TALKING
//            val state3 = state1.signal()    // TALKING
//            println(state1.toString())      // WAITING
//            println(state2.toString())      // TALKING
//            println(state3.toString())      // TALKING
//            println(state1.hashCode())      // 997110508
//            println(state2.hashCode())      // 509886383
//            println(state3.hashCode())      // 509886383
        }

        // Example 3 : Implementing interfaces in enum classes
        // TODO:Implementing interfaces in enum classes
        run {
//            val arithmetics = IntArithmetics.PLUS(13, 31)
//            PLUS(13, 31) = 44
//            TIMES(13, 31) = 403
        }

        // Example 4 : Working with enum constants
        run {
            test_enum_contanst()
        }
    }

    // Example 1 : enum classes, START

    // Enum classes is the implementation of type-safe enums.
    // Each enum constant is an object. Enum constants are separated by commas.
    enum class Direction {
        SOUTH, EAST
    }


    enum class Color(val rgb: Int) {
        RED(1), GREEN(2), BLUE(3)
    }
    // Example 1 : enum classes, END

    // Example 2: Anonymous classes, START
    // Enum constants can declare their own anonymous classes with their corresponding methods, as well as with overriding base methods.
    enum class ProtocolState {
        WAITING {
            override fun signal(): ProtocolState = TALKING
        },
        TALKING {
            override fun signal(): ProtocolState = WAITING
        };

        abstract fun signal(): ProtocolState
    }

    // Example 2: Anonymous classes, END

    // Example 3 : Implementing interfaces in enum classes,START
    enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
        PLUS {
            override fun apply(t: Int, u: Int): Int = t + u
        },
        TIMES {
            override fun apply(t: Int, u: Int): Int = t * u
        };

        override fun applyAsInt(t: Int, u: Int) = apply(t, u)
    }
    // Example 3 : Implementing interfaces in enum classes, END

    // // Example 4 : Working with enum constants, START
    fun test_enum_contanst() {
        val direction1 = Direction.SOUTH

        // Get information of enum constant
        println(direction1.name)            // SOUTH
        println(direction1.ordinal)         // 0

        // loop enum constant values
        val directionList: Array<Direction> = Direction.values()
        // 1435804085
        // 1784662007
        for (item in directionList) {
            println(item.hashCode())
        }

        // get a enum constant by value
        val south = Direction.valueOf("SOUTH") // throws an IllegalArgumentException if the specified name does not match any of the enum constants defined in the class.
        println(south.hashCode())   // 1435804085

        // access the constants in an enum class in a generic way
        printAllValues<RGB>()       // RED, GREEN, BLUE
    }

    enum class RGB {
        RED, GREEN, BLUE
    }

    // access the constants in an enum class in a generic way using the enumValues<T>() and enumValueOf<T>() functions
    inline fun <reified T : Enum<T>> printAllValues() {
        print(enumValues<T>().joinToString { it.name })
    }
    // // Example 4 : Working with enum constants, END
}