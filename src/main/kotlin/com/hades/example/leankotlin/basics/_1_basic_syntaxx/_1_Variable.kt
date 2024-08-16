package com.hades.example.leankotlin.basics._1_basic_syntaxx

fun main() {
    // 1 Variable and constant
    run {
        var age: Int = 18
        val num = 10
        // Compile error : Val cannot be reassigned
        // num = 20
    }
}