package com.hades.example.leankotlin._4_functions

/**
 * Local function:function inside another function
 */
fun main() {
    var result = sum(1, 2, 3)
    println(result) // 6
}

fun sum(num1: Int, num2: Int, num3: Int): Int {
    /**
     * add is local function inside sum function
     */
    fun add(num1: Int, num: Int): Int {
        return num1 + num
    }

    var sum = add(num1, num2);
    sum = add(sum, num3);
    return sum
}