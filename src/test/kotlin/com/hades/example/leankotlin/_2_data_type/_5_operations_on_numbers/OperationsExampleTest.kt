package com.hades.example.leankotlin._2_data_type._5_operations_on_numbers

import kotlin.test.Test
import kotlin.test.assertEquals

// https://kotlinlang.org/docs/operator-overloading.html#augmented-assignments
// https://www.yiibai.com/kotlin/kotlin-operator.html
class OperationsExampleTest {

    /**
     * 算术运算符
     */
    @Test
    fun test_1() {
        val a = 10
        val b = 5
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

        assertEquals(1, 7 % 2)
        assertEquals(1, 7.rem(2))
    }

    /**
     * Indexed access operator
     */
    @Test
    fun test_Indexed_access_operator() {
        val a = arrayOf(1, 2, 3)
        assertEquals(2, a[1])
        assertEquals(2, a.get(1))
    }

    /**
     * in operator
     */
    @Test
    fun test_in_operator() {

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