package com.hades.example.leankotlin.concepts._1_types._2_data_type._1_primitive_type

// https://kotlinlang.org/docs/booleans.html
fun main() {
    // Boolean, Boolean?
    run {
        var b1: Boolean? = null
        println(b1) // null
        b1 = true
        println(b1) // true
        b1 = false  // false
        println(b1)

        println()

        val b2: Boolean = true
        val b3: Boolean = false
        println(b2)
        println(b3)

    }

    // 自动识别为Bool
    run {
        val b2 = true
        val b3 = false
        println(b2)
        println(b3)
    }


    // Built-in operations on booleans
    run {
        val bTrue: Boolean = true;
        val bFalse: Boolean = false;
        val bNull: Boolean? = null
        println(bTrue || bFalse)    // true
        println(bTrue && bFalse)    // false
        println(!bFalse)            // true
        println(!bTrue)             // false
    }
}