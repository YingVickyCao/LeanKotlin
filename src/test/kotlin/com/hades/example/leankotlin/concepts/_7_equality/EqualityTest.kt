package com.hades.example.leankotlin.concepts._7_equality

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * https://kotlinlang.org/docs/equality.html#structural-equality
 * https://book.kotlincn.net/text/equality.html
 * == (Structural equality):  a check for the equals() function
 * ===(Referential equality) : a check for two references pointing to the same object
 */
class EqualityTest {
    /**
     * Structural equality
     */
    @Test
    fun test1() {
        // translated "a == b" => "a?.equals(b) ?: (b === null)"
        run {
            val a: String? = "a"
            var b: String? = "b"
            assertEquals(false, a == b)
        }
        run {
            val a: String? = null
            var b: String? = null
            assertEquals(true, a == b)
        }

        run {
            val a: String? = "a"
            var b: String? = "a"
            assertEquals(true, a == b)
        }

        run {
            val a: String? = null
            var b: String? = "c"
            assertEquals(false, a == b)
        }
        // By default, the equals() function implements referential equality. However, classes in Kotlin can override the equals() function to provide a custom equality logic and, in this way, implement structural equality.
        // Distinctly, non-data classes do not override the equals() function by default. Instead, non-data classes implement referential equality behavior inherited from the Any class.
        run {
            class Point(val x: Int, val y: Int)

            val p1 = Point(1, 2)
            val p2 = Point(1, 2)
            assertEquals(true, p1 == p1)
            assertEquals(false, p1 == p2)

            assertEquals(true, p1.equals(p1)) // using equals is same with ==
            assertEquals(false, p1.equals(p2))
        }

        // Value classes and data classes are two specific Kotlin types that automatically override the equals() function. That's why they implement structural equality by default.
        run {
            data class Point(val x: Int, val y: Int)

            val p1 = Point(1, 2)
            val p2 = Point(1, 2)
            assertEquals(true, p1 == p1)
            assertEquals(true, p1 == p2)

            assertEquals(true, p1.equals(p1))
            assertEquals(true, p1.equals(p2))
        }

        run {
            class Point(val x: Int, val y: Int) {
                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false

                    other as Point

                    if (x != other.x) return false
                    if (y != other.y) return false

                    return true
                }

                override fun hashCode(): Int {
                    var result = x
                    result = 31 * result + y
                    return result
                }
            }

            val p1 = Point(1, 2)
            val p2 = Point(1, 2)
            assertEquals(true, p1 == p1)
            assertEquals(true, p1 == p2)

            assertEquals(true, p1.equals(p1))
            assertEquals(true, p1.equals(p2))
        }

        // When overriding the equals() function, you should also override the hashCode() function to keep consistency between equality and hashing and ensure a proper behavior of these functions.
    }

    /**
     * Referential equality
     */
    @Test
    fun test2() {
        // Referential equality verifies the memory addresses of two objects to determine if they are the same instance.
        run {
            var a = "Hello"
            var b = a
            var c = "world"
            var d = "world"
            assertEquals(true, a === b)
            assertEquals(false, a === c)
            assertEquals(true, c === d)
        }

        run {
            data class Point(val x: Int, val y: Int)

            val p1 = Point(1, 2)
            val p2 = Point(1, 2)
            val p3 = Point(2, 2)
            assertEquals(true, p1 === p1)
            assertEquals(false, p1 === p2)
            assertEquals(false, p2 === p3)
        }

        // For values represented by primitive types at runtime (for example, Int), the === equality check is equivalent to the == check.
        run {
            var a = 1
            var b = a
            var c = 2
            var d = 2
            assertEquals(true, a === b)
            assertEquals(false, a === c)
            assertEquals(true, c === d)
        }
    }

    /**
     * Floating-point numbers equality
     */
    @Test
    fun test3() {
        // NaN: Not-a-Number (NaN)
        assertEquals(false, Double.NaN == Double.NaN)
        assertEquals(false, Float.NaN > Float.POSITIVE_INFINITY)
        assertEquals(true, -0.0 == 0.0)
    }

    /**
     * Array equality
     */
    @Test
    fun test4() {

    }
}