package com.hades.example.leankotlin._10_operations

// Operations on numbers
fun main() {
    run {
        println(1 + 3)    // 4
        println(1 / 3)    // 0
    }

    // Division of integers
    run {
        println(1.0 / 3)  // 0.3333333333333333
//        println(5/2 == 2.5)   // Error : Compile error ,  Operator '==' cannot be applied to 'Int' and 'Double'
        println((5 / (2.toDouble())) == 2.5)   // true
        println(5 / 2 == 3) // false
        println(5 / 2 == 2) // true
    }

    // Bitwise operations
    run {
        println(4 shl 2)     // 16      signed <<
        println(4 shr 2)     // 1       signed >>
        println(4 ushr 2)    // 1       unsigned >>
        println(0b1 and 0b0)   // 0     AND
        println(0b1 or 0b0)    // 1     OR
        println(0b0 xor 0b0)    // 0    XOR
        println(0b1 xor 0b0)    // 1
        println(0b1.inv())    // -2     ^
        println(0b0.inv())    // -1
    }

    // Floating-point numbers comparison
    run {
        val d1 = 1.0;
        val d2 = 1.0;
        val d3 = 2.0;
        // Equality checks: a == b and a != b
        println(d1 == d2)   // true
        println(1.0 == 1.1) // false
        println(d1 != d2)   // false

        // Comparison operators: a < b, a > b, a <= b, a >= b
        println(d1 < d2)    // false
        println(d1 > d2)    // false
        println(d1 <= d2)   // true
        println(d1 >= d2)   // true

        // Range instantiation and range checks: a..b ([a,b]), x in a..b, x !in a..b https://www.educba.com/kotlin-range/
        var range = 1..3
        for (x in range) {
            // 1
            // 2
            // 3
            println(x)
        }
        println()


        for (x in range.reversed()) {
            // 3
            // 2
            // 1
            println(x)
        }
        println()

        for (x in 1..3) { // [1,3]
            // x=1
            // x=2
            // x=3
            println("x=$x")
        }
        println()

        for (x in 1.rangeTo(3)) { // [1,3]
            // x=1
            // x=2
            // x=3
            println("x=$x")
        }
        println()

        for (x in 3..1) {
            // print nothing
            println("x=$x")
        }
        println()

        for (x in 1..10 step 2) { // [1,10] with step 2
            // x=1
            // x=3
            // x=5
            // x=7
            // x=9
            println("x=$x")
        }
        println()


        for (x in 3 downTo 1) {  // [3,1]
            // 3
            // 2
            // 1
            println(x)
        }
        println()

        for (x in 5 downTo 1 step 2) {  // [5,1] with step 2
            // 5
            // 3
            // 1
            println(x)
        }
        println()


        for (x in 1 until 3) {  // [1,3)
            // 3
            // 2
            // 1
            println(x)
        }
        println()

        for (x in 'a'..'c') {    // ['a','c']
            // a
            // b
            // c
            println(x)
        }
        println()

        for (x in 'c'..'a') {
            // print nothing
            println(x)
        }
        println()


        // TODO: not same with website https://kotlinlang.org/docs/numbers.html#floating-point-numbers-comparison
        println(-0.0 == 0.0)    // true
        println(-0.0 < 0.0)     // false
    }
}