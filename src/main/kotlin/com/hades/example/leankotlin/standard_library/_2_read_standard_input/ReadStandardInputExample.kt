package com.hades.example.leankotlin.standard_library._2_read_standard_input

fun main() {
    /**
     * Read from the standard input
     * https://kotlinlang.org/docs/basic-syntax.html#read-from-the-standard-input
     * https://kotlinlang.org/docs/read-standard-input.html
     *
     * Not safe usage:
     * readln()
     * readln().toInt()
     *
     * Safe usage:
     * readlnOrNull()
     * readlnOrNull().toIntOrNull()
     */
    run {
//        val input = readln()
//        println("your input is :$input\n")
    }

    // To work with data types other than strings, you can convert the input using conversion functions like .toInt(), .toLong(), .toDouble(), .toFloat(), or .toBoolean(). It is possible to read multiple inputs of different data types and store each input in a variable
    run {
//        val myInt = readln().toInt() // Error:Exception in thread "main" java.lang.NumberFormatException: For input string: "false"
//        println(myInt)
//        val myBool = readln().toBoolean()
//        println(myBool)
    }

    // Reads the input, assuming the elements are separated by spaces, and converts them into integers. For example: 1 2 3
    run {
//        val numbers = readln().split(' ').map { it.toInt() } // 1 2 3
//        println(numbers) // [1, 2, 3]
    }

    run {
//        val numbers = readln().split(',').map { it.toInt() } // 1,2,3
//        println(numbers) // [1, 2, 3]
    }

    // Handle standard input safely
    run {
        val wrongInt = readln().toIntOrNull() // hello
        println(wrongInt)   // null

        val correctInt = readln().toIntOrNull() // 10
        println(correctInt)
    }
}