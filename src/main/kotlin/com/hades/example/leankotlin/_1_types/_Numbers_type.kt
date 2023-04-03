package com.hades.example.leankotlin._1_types

fun main() {
    // Integer type , Start
    run {
        // 自动类型推断
        // If it is not exceeding the range of Int, the type is Int. If it exceeds, the type is Long
        var age2 = 18   // Int
        val threeBillion = 3000000000 // Long

        // 明确表示类型
        var oneLong = 1L // long
        var oneLong2: Long = 1L // long
        val oneByte: Byte = 1
    }

    // Literal constants for numbers - integral values
    run {
        println(123)  // int is Decimals
        println(123L)   // Long
        println(0x0F)   // int is Hexadecimals
        println(0b00001011)   // int is binary
        // Octal literals are not supported in Kotlin.
    }

    // More readable warting
    run {
        val oneMillion = 1_000_000      // int
        val creditCardNumber = 1234_5678_9012_3456L     // long
        val socialSecurityNumber = 999_99_9999L // long
        val hexBytes = 0xFF_EC_DE_5E    // Hexadecimals
        val bytes = 0b11010010_01101001_10010100_10010010   // binary
    }
    // Integer type , END

    // Floating type , START
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
    // Floating type , END

    // Numbers representation on the JVM
    run{
        // numbers are stored as primitive types : int, double and so on
        // Nullable numbers (Int? or generics ) is boxed in Java classes Integer,  Double and so on.

        // All nullable references to a are actually the same object because of the memory optimization that JVM applies to Integers between -128 and 127. It doesn't apply to the b references, so they are different objects.
        val a:Int = 100
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        println(boxedA === anotherBoxedA)    // true

        val b:Int = 10000
        val boxedB: Int? = b
        val anotherBoxedB: Int? = b
        println(boxedB === anotherBoxedB)    // false
        println(boxedB == anotherBoxedB)     // true

        /**
         * 结论：
         * (1) on JVM runtime, Int -> java int, Double -> java double
         * (2) on JVM runtime, Int? -> java Integer, Double -> java Double
         * (3) on JVM runtime, java Integer has cache [-128,127] (Integer.java -> IntegerCache). If value in this range, Int? -> java Integer -> int, or Int? -> java Integer
         * Byte also has cache [-128,127],
         * === : justify object reference
         * == : justify value
         */
    }

}