package com.hades.example.leankotlin._2_data_type

fun main() {
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