package com.hades.example.leankotlin._4_class_and_object._12_enum_classes

import org.w3c.dom.css.RGBColor
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

// https://kotlinlang.org/docs/enum-classes.html
@OptIn(ExperimentalStdlibApi::class)
fun main() {

    /**
     * Example 1 : Enum classes
     */

    run {
        val direction = Direction.NORTH
        println(direction)  // NORTH

        val color = Color.BLUE
        println(color)  // BLUE
    }

    /**
     * Example 2 : Anonymous classes
     */
    run {
        val protocolState = ProtocolState.WAITING
        val protocolState2 = ProtocolState.TALKING
        println(protocolState)  // WAITING
        println(protocolState2) // TALKING

        val protocolState3 = protocolState.signal()
        println(protocolState3) // TALKING

        val protocolState4 = protocolState2.signal()
        println(protocolState4) // WAITING
    }

    /**
     * Example 3 : Implementing interfaces in enum classes
     */
    val arithmetics = IntArithmetics.PLUS
    println(arithmetics.apply(1, 2))        // 3
    println(arithmetics.applyAsInt(1, 2))   // 3

    val arithmetics2 = IntArithmetics.TIMES
    println(arithmetics2.apply(1, 2))       // 2
    println(arithmetics2.applyAsInt(1, 2))   // 2

    /**
     * Example 4 : Working with enum constants
     */
    run {
        // Enum classes 自带方法
        // EnumClass.valueOf(value: String): EnumClass, value 不合法会 IllegalArgumentException
        // EnumClass.values(): Array<EnumClass>
        //RED
        //GREEN
        //BLUE
        //The fist color is RED
        for (color in RGB.values()) {
            println(color.toString())
        }
        println("The fist color is ${RGB.valueOf("RED")}")
//    println("The fist color is ${RGB.valueOf("XYZ")}") // ERROR:Exception in thread "main" java.lang.IllegalArgumentException: No enum constant com.hades.example.leankotlin._4_class_and_object._12_enum_classes.RGB.XYZ

        // In Kotlin 1.9.0, the entries property is introduced as a replacement for the values() function.
        // TODO:  enum entries property
//        for (color in RGB.entries) println(color.toString())

        // Print enum constant properties: name and ordinal
        println(RGB.RED.name)       // RED
        println(RGB.RED.ordinal)    // 0
        println(RGB.GREEN.name)     // GREEN
        println(RGB.GREEN.ordinal)  // 1
        println(RGB.BLUE.name)      // BLUE
        println(RGB.BLUE.ordinal)   // 2

        // Print enumValues<T>
        printAllValues<RGB>()   // RED, GREEN, BLUE
    }
}


/**
 * Example 1 : Enum classes
 */

// enums are type-safe
// Each enum constant is an object
enum class Direction {
    NORTH, SHORT, WEST, EAST
}

// Since each enum is an instance of the enum class, it can be initialized as:

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0xFF0000),
    BLUE(0xFF0000),
}

/**
 * Example 2 : Anonymous classes
 */

// Enum constants can declare their own anonymous classes with their corresponding methods, as well as with overriding base methods.
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },
    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

/**
 * Example 3 : Implementing interfaces in enum classes
 */

// An enum class can implement an interface (but it cannot derive from a class)
enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    };

    override fun applyAsInt(t: Int, u: Int) = apply(t, u)
}
// Every enum constant also has properties: name and ordinal, for obtaining its name and position (starting from 0) in the enum class declaration:


// You can access the constants in an enum class in a generic way using the enumValues<T>() and enumValueOf<T>() functions:
// TODO:inline functions and reified type parameters
inline fun <reified T : Enum<T>> printAllValues() {
    println(enumValues<T>().joinToString { it.name })
}

// In Kotlin 1.9.20, the enumEntries<T>() function is introduced as a future replacement for the enumValues<T>() function.

// TODO:enumEntries not supported at 1.9.20
@OptIn(ExperimentalStdlibApi::class)
//inline fun <reified T : Enum<T>> printAllValues() {
//    println(enumEntries<T>().joinToString { it.name })
//}

/**
 * Example 4 : Working with enum constants
 */

enum class RGB {
    RED, GREEN, BLUE
}