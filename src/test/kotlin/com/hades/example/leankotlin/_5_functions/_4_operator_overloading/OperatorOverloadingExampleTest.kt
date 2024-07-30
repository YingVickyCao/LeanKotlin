package com.hades.example.leankotlin._5_functions._4_operator_overloading

import kotlin.test.*

// https://kotlinlang.org/docs/operator-overloading.html#augmented-assignments
// https://www.yiibai.com/kotlin/kotlin-operator.html
// https://book.kotlincn.net/text/operator-overloading.html
// 操作符
// 操作符重载
class OperatorOverloadingExampleTest {
    /**
     * Unary operations - Unary prefix operators
     * 一元操作 - 一元前缀操作符
     */
    @Test
    fun test_UnaryPrefixOperator() {
        run {
            val a = 10
            assertEquals(10, +a)
            assertEquals(10, a.unaryPlus())
            assertEquals(-10, -a)
            assertEquals(-10, a.unaryMinus())
        }

        run {
            val a = -10
            assertEquals(-10, +a)
            assertEquals(-10, a.unaryPlus())
            assertEquals(10, -a)
            assertEquals(10, a.unaryMinus())
        }

        run {
            val flag = true
            assertEquals(false, !flag)
            assertEquals(false, flag.not())
        }
    }

    /**
     * 一元前缀操作符重载
     */
    @Test
    fun test_UnaryPrefixOperator_Operator_Overloading() {
        data class Point(var x: Int, var y: Int)

        operator fun Point.unaryMinus(): Point {
            return Point(-x, -y)
        }

        operator fun Point.unaryPlus(): Point {
            return Point(+x, +y)
        }

        run {
            val point = Point(-10, -20)
            val result = +point
            assertEquals(-10, result.x)
            assertEquals(-20, result.y)
        }

        run {
            val point = Point(10, 20)
            val result = -point
            assertEquals(-10, result.x)
            assertEquals(-20, result.y)
        }
    }

    /**
     * Unary operations - Increments and decrements
     * 一元操作 - 递增与递减
     */
    @Test
    fun test_Increments_and_decrements() {
        run {
            var a = 5
            val result = ++a
            assertEquals(6, result)
            assertEquals(6, a)
        }

        run {
            var a = 5
            val result = a++
            assertEquals(5, result)
            assertEquals(6, a)
        }

        run {
            var a = 5
            val result = --a
            assertEquals(4, result)
            assertEquals(4, a)
        }

        run {
            var a = 5
            val result = a--
            assertEquals(5, result)
            assertEquals(4, a)
        }
    }

    @Test
    fun test_Increments_and_decrements_Overloading() {
        // They shouldn't mutate the object on which the inc or dec was invoked.
        data class Point(var x: Int)

        //a++
        operator fun Point.inc(): Point {
            return Point(x.inc())
        }

        // a--
        operator fun Point.dec(): Point {
            return Point(x.dec())
        }

        run {
            var a = Point(5)
            val result = ++a
            assertEquals(6, result.x)
            assertEquals(6, a.x)
        }

        run {
            var a = Point(5)
            val result = --a
            assertEquals(4, result.x)
            assertEquals(4, a.x)
        }

        run {
            var a = Point(5)
            val result = a--
            assertEquals(5, result.x)
            assertEquals(4, a.x)
        }
    }


    /**
     * Binary operations - Arithmetic operators
     * 二元操作 - 算术运算符
     */
    @Test
    fun test_ArithmeticOperators() {
        val a = 10
        val b = 5
        val c = 7
        val d = 2
        assertEquals(15, a + b)
        assertEquals(15, a.plus(b))

        assertEquals(5, a - b)
        assertEquals(5, a.minus(b))

        assertEquals(50, a * b)
        assertEquals(50, a.times(b))

        assertEquals(2, a / b)
        assertEquals(2, a.div(b))

        assertEquals(0, a % b)
        assertEquals(0, a.rem(b))

        assertEquals(1, c % d)
        assertEquals(1, c.rem(d))
    }


    @Test
    fun test_ArithmeticOperators_Overloading() {
        data class Point(var x: Int) {
            operator fun plus(p: Point): Point {
                return Point(x + p.x)
            }

            operator fun minus(p: Point): Point {
                return Point(x - p.x)
            }

            operator fun times(p: Point): Point {
                return Point(x * p.x)
            }

            operator fun div(p: Point): Point {
                return Point(x / p.x)
            }

            operator fun rem(p: Point): Point {
                return Point(x % p.x)
            }
        }

        val a = Point(10)
        val b = Point(5)
        val c = Point(7)
        val d = Point(2)
        assertEquals(15, (a + b).x)
        assertEquals(15, (a.plus(b)).x)

        assertEquals(5, (a - b).x)
        assertEquals(5, (a.minus(b)).x)

        assertEquals(50, (a * b).x)
        assertEquals(50, (a.times(b)).x)

        assertEquals(2, (a / b).x)
        assertEquals(2, (a.div(b)).x)

        assertEquals(0, (a % b).x)
        assertEquals(0, (a.rem(b)).x)

        assertEquals(1, (c % d).x)
        assertEquals(1, (c.rem(d)).x)
    }

    /**
     * Binary operations - in operator x
     * 二元操作 - in 操作符
     */
    @Test
    fun test_in_operator() {
        val data: Array<Int> = arrayOf(1, 10)
        assertTrue(1 in data)
        assertFalse(2 in data)
        assertTrue(2 !in data)
    }

    @Test
    fun test_in_operator_Overloading() {
        data class Points(val ints: Array<Int>) {
            operator fun contains(x: Int): Boolean {
                return ints.contains(x)
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Points

                return ints.contentEquals(other.ints)
            }

            override fun hashCode(): Int {
                return ints.contentHashCode()
            }
        }

        val data = Points(arrayOf(1, 10))
        assertTrue(1 in data)
        assertFalse(2 in data)
        assertTrue(2 !in data)
    }

    /**
     * Binary operations - Indexed access operator
     * 二元操作 - 索引访问操作符
     */
    @Test
    fun test_Indexed_access_operator() {
        val a = arrayOf(1, 2, 3)
        assertEquals(2, a[1])
        assertEquals(2, a.get(1))
    }

    /**
     * invoke operator
     */
    @Test
    fun test_invoke_operator() {
        data class Stu(val name: String, val age: Int) {
            operator fun invoke(): String { //  重载invoke运算符
                return "$name - $age"
            }

            operator fun invoke(age: Int): Boolean { //  重载invoke运算符
                return age > 10
            }
        }

        val stu = Stu("SY", 24)
        assertEquals("SY - 24", stu.invoke()) // 正常调用
        assertEquals("SY - 24", stu())  // 约定后带调用
        assertEquals(true, stu.invoke(20))  // 正常调用
        assertEquals(true, stu(20)) // 约定后带调用
    }

    /**
     * Augmented assignments
     */
    @Test
    fun test_Augmented_assignments() {
        run {
            var a = 5
            val b = 10
            a += b
            assertEquals(15, a)
            assertEquals(15, 5.plus(10))
        }
        run {
            var a = 5
            val b = 10
            a -= b
            assertEquals(-5, a)
            assertEquals(-5, 5.minus(10))
        }

        run {
            var a = 5
            val b = 10
            a *= b
            assertEquals(50, a)
            assertEquals(50, 5.times(10))
        }

        run {
            var a = 11
            val b = 5
            a /= b
            assertEquals(2, a)
            assertEquals(2, 11.div(5))
        }

        run {
            var a = 11
            val b = 5
            a %= b
            assertEquals(1, a)
            assertEquals(1, 11.rem(5))
        }
    }

    /**
     * Equality and inequality operators
     */
    @Test
    fun test_Equality_and_inequality_operators() {
        assertEquals(true, 5 == 5)
        assertEquals(true, 5.equals(5))

        assertEquals(true, 5 != 10)
        assertEquals(true, !5.equals(10))
    }


    /**
     * Comparison operators
     */
    @Test
    fun test_comparison_operators() {
        assertEquals(true, 10 > 5)
        assertEquals(true, 10.compareTo(5) > 0)

        assertEquals(true, 5 < 10)
        assertEquals(true, 5.compareTo(10) < 0)

        assertEquals(true, 10 >= 5)
        assertEquals(true, 10.compareTo(5) >= 0)

        assertEquals(true, 5 <= 10)
        assertEquals(true, 5.compareTo(10) <= 0)
    }
}