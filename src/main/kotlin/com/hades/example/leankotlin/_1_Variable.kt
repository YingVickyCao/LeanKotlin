package com.hades.example.leankotlin._1_basic_types

fun main() {
    // 1 Variable and constant
    run {
        var age: Int = 18
        val num = 10
        // Compile error : Val cannot be reassigned
        // num = 20
    }
}