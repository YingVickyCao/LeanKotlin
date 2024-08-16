package com.hades.example.leankotlin.concepts._1_types._2_data_type._1_primitive_type._numbers

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
}