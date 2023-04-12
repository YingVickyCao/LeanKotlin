package com.hades.example.leankotlin._1_basic_types

/**
 * https://kotlinlang.org/docs/characters.html
 */
fun main() {
    run {
        val aChar: Char = 'a'
        println(aChar)

        // 自动识别为 char
        val bChar = 'a'
        println(bChar)
    }

    // Special characters start from an escaping backslash \.
    run {
        println('\t')
    }
}