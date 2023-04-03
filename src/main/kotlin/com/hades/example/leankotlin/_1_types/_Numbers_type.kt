package com.hades.example.leankotlin._1_types

fun main() {
    /**
     * Integer type , Start
     */
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

    // More readable writting
    run {
        val oneMillion = 1_000_000      // int
        val creditCardNumber = 1234_5678_9012_3456L     // long
        val socialSecurityNumber = 999_99_9999L // long
        val hexBytes = 0xFF_EC_DE_5E    // Hexadecimals
        val bytes = 0b11010010_01101001_10010100_10010010   // binary
    }

    // Unsigned integer types
    run {
        println("Unsigned integer types ====>")
        println("UByte :  [" + UByte.MIN_VALUE + "," + UByte.MAX_VALUE + "]")
        println("UShort :  [" + UShort.MIN_VALUE + "," + UShort.MAX_VALUE + "]")
        println("UInt :  [" + UInt.MIN_VALUE + "," + UInt.MAX_VALUE + "]")
        println("ULong :  [" + ULong.MIN_VALUE + "," + ULong.MAX_VALUE + "]")
        println("Unsigned integer types >====")
    }

    /**
     * Integer type , END
     */

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

    /**
     * Numbers representation on the JVM， START
     */
    run {
        // numbers are stored as primitive types : int, double and so on
        // Nullable numbers (Int? or generics ) is boxed in Java classes Integer,  Double and so on.

        // All nullable references to a are actually the same object because of the memory optimization that JVM applies to Integers between -128 and 127. It doesn't apply to the b references, so they are different objects.
        val a: Int = 100
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        println(boxedA === anotherBoxedA)    // true

        val b: Int = 10000
        val boxedB: Int? = b
        val anotherBoxedB: Int? = b
        println(boxedB === anotherBoxedB)    // false
        println(boxedB == anotherBoxedB)     // true

        /**
         * 结论：
         * (1) on JVM runtime, Int -> java int, Double -> java double
         * (2) on JVM runtime, Int? -> java Integer, Double -> java Double
         * (3) on JVM runtime, java Integer has cache [-128,127] (Integer.java -> IntegerCache). If value in this range, Int? -> java Integer -> int, or Int? -> java Integer
         * Byte also has cache [-128,127]
         * Long also has cache [-128,127]
         * === : justify object reference
         * == : justify value
         */
    }

    /**
     * Numbers representation on the JVM， END
     */

    /**
     * Explicit number conversions, START
     */
    // TODO: test result not same with website  https://kotlinlang.org/docs/numbers.html#explicit-number-conversions
    run {
        // smaller types are not subtypes of bigger ones.
//        val a: Int? = 1;   // Integer
//         val b : Long? = a;   // Should : Error : Compile error
//        // print(b == a)    // Should : false
//
//        // smaller types are NOT implicitly converted to bigger types
//        var oneByte: Byte = 1
//        var oneInt: Int = b  // Should : error
//        println(oneInt)     // 1000
//        var oneInt2: Int = b.toInt()  // Int
//        println(oneInt2)    // 1000

        // Auto implicit conversion
        val l = 1L + 3;  // Long + Int -> Long
    }
    /**
     * Explicit number conversions, END
     */
}