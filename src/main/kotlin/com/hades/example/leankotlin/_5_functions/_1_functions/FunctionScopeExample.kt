package com.hades.example.leankotlin._5_functions._1_functions

// https://kotlinlang.org/docs/functions.html#function-scope
// https://kotlinlang.org/docs/functions.html#function-scope
fun main() {
    /**
     * Example 2 :Function scope
     *
     */
    /**
     * Example 2 :Function scope - Local functions
     *
     */
    run {
        val result = sum(1, 2, 3)
        println(result) // 6
    }


    /**
     * Example 2 :Function scope - Member functions
     *
     */

    /**
     * Example 2 :Function scope - Generic functions
     *
     */
    /**
     * Example 2 :Function scope - Tail recursive functions
     *
     */

    println(findFixPoint(5.0))  // 0.7390851332712491
    println(findFixPoint2(5.0)) // 0.7390851331706995
}

/**
 * Example 2 :Function scope
 *
 */
/**
 * Example 2 :Function scope - Local functions
 *
 */
// Local function (局部函数) : 一个函数在另一个函数内部
// A local function can access local variables of outer functions (the closure). In the case above, visited can be a local variable:
fun sum(num1: Int, num2: Int, num3: Int): Int {
    /**
     * add is local function inside sum function
     */
    var visited = false
    fun add(num1: Int, num: Int): Int {
        println(visited)
        return num1 + num
    }

    var sum = add(num1, num2);
    visited = true
    sum = add(sum, num3);
    return sum
}

/**
 * Example 2 :Function scope - Member functions
 *
 */
class Sample {
    fun foo() { // 成员函数是在类或对象内部定义的函数
        println("foo")
    }
}

/**
 * Example 2 :Function scope - Generic functions
 *
 */
// 函数可以有泛型参数
fun <T> singletonList(item: T): List<T> {
    return emptyList()
}

/**
 * Example 2 :Function scope - Tail recursive functions
 *
 */
// 尾递归函数
// 对于某些使用循环的算法，可以使用尾递归替代而不会有堆栈溢出的风险。 当一个函数用 tailrec 修饰符标记并满足所需的形式条件时，编译器会优化该递归， 留下一个快速而高效的基于循环的版本

// 计算余弦的不动点（fixpoint of cosine）
// 它只是重复地从 1.0 开始调用 Math.cos， 直到结果不再改变，对于这里指定的 eps 精度会产生 0.7390851332151611 的结果。
val eps = 1E-10
tailrec fun findFixPoint(x: Double = 1.0): Double = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))

// 相当于传统风格的代码
private fun findFixPoint2(x: Double = 1.0): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (Math.abs(x - y) < eps) return x
        x = Math.cos(x)
    }
}
// TODO:  tailrec
// 要符合 tailrec 修饰符的条件的话，函数必须将其自身调用作为它执行的最后一个操作。在递归调用后有更多代码时， 不能使用尾递归，不能用在 try/catch/finally 块中，也不能用于 open 的函数。 目前在 Kotlin for the JVM 与 Kotlin/Native 中支持尾递归。