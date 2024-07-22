package com.hades.example.leankotlin._5_functions._4_operator_overloading


/*
 * 操作符重载
 */
fun main() {
    val example: OperatorOverloadingExample = OperatorOverloadingExample()
    example.example1_1()
}

class OperatorOverloadingExample {
    /**
     * Example 1 : Unary operations
     */
    /**
     * Example 1 : Unary operations - Unary prefix operators: +a /-a / !a
     */
    data class Point(var x: Int, var y: Int)

    operator fun Point.unaryMinus() = Point(-x, -y)
    operator fun Point.unaryPlus(): Point {
        x += 1
        y += 1
        return this
    }

    operator fun Point.not() :Point{
       x.inc()
        val z:Int = 4
        return this
    }

    fun example1_1() {
        println(-Point(10, 20)) // -a : Point(x=-10, y=-20)
        println(+Point(10, 20)) // +a : Point(x=20, y=30)
    }

    /**
     * Example 1 : Unary operations - Increments and decrements : a++ / a--
     */
//    operator fun Point.inc() = Point(x + 1, y + 1)

    fun example1_2() {

    }
}