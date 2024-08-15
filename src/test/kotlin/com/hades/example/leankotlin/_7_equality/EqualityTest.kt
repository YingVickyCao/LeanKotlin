package com.hades.example.leankotlin._7_equality

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
            class Point(val x: Int, val y: Int) // value class

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

            assertEquals(true, p1.equals(p1)) // using equals is same with ==
            assertEquals(true, p1.equals(p2))
        }

        run {
            
        }

        // When overriding the equals() function, you should also override the hashCode() function to keep consistency between equality and hashing and ensure a proper behavior of these functions.
    }

    /**
     * Referential equality
     */
    @Test
    fun test2() {

    }

    /**
     * Floating-point numbers equality
     */
    @Test
    fun test3() {

    }

    /**
     * Array equality
     */
    @Test
    fun test4() {

    }
}