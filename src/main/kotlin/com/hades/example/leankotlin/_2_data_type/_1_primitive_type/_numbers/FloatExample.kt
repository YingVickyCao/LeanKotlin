package com.hades.example.leankotlin._2_data_type._1_primitive_type._numbers

fun main() {
    /**
     * Floating type , START
     */
    run {
        // 自动类型推断
        var oneDouble = 3.14

        // 明确表示类型
        var oneDouble2: Double = 3.14
        var oneDouble3 = 3.14F
    }

    // Floating type - Double
    run {
        val pi = 3.14 // double
        val pi2: Double = 3.14

        // Error : Compile error : The integer literal does not conform to the expected type Double
        //val  pi3:Double = 1
        val pi3: Double = 1.0
    }

    // Floating type - Float
    run {
        val pi = 3.14f // float
        val pi2: Float = 3.14F

        // Error : Compile error : Conflicting declarations: val pi3: Float, val pi3: Double
//        val  pi3:Float = 1
        val pi3: Float = 1.0f
    }

    // Literal constants for numbers -floating-point
    run {
        println(123.5)  // Double by default
        println(123.5e10) // 1.235E12   Double by default
        println(123.5f)     // Float
        println(123.5F) // Float
    }
    /**
     * Floating type , END
     */
}