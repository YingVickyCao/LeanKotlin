package com.hades.example.leankotlin._6_null_safety

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NullSafetyTest {
    /**
     * Nullable types and non-nullable types
     */
    @Test
    fun test1() {
        // kotlin distinguish  nullable references and non-nullable references.
        var str: String = "abc"
        // Null can not be a value of a non-null type String
//        str = null // Error: compilation error
        assertEquals(3, str.length)

        var b: String? = "abc"
        assertNotNull(b)
        assertEquals("abc", b)
        b = null
        assertNull(b)
//        b.length // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?
    }

    /**
     * Checking for null in conditions
     */
    @Test
    fun test2() {
        var b: String? = "abc"
        // check whether b is null
        val length = if (b != null) b.length else -1
        assertEquals(3, length)

        if (b != null && b.length > 0) {
            println("length is ${b.length}")
        } else {
            println("Empty string")
        }
    }


    /**
     * Safe calls
     */
    @Test
    fun test3() {
        val a = "kotlin"
        val b: String? = null
        println(b?.length) // use safe call operator ?. for nullable variable
        println(a?.length) // Unnecessary safe call

        // Safe calls are useful in chains
        // bob?.department?.head?.name
        // Such a chain returns null if any of the properties in it is null.

        data class Head(var name: String?)
        data class Department(var head: Head?)
        data class Employee(var department: Department?)

        val name = "JKS"
        val e1: Employee? = null
        val e2 = Employee(null)
        val e3 = Employee(Department(null))
        val e4 = Employee(Department(Head(null)))
        val e5 = Employee(Department(Head(name)))

        fun getName(e: Employee?): String? {
            return e?.department?.head?.name
        }
        assertNull(getName(e1))
        assertNull(getName(e2))
        assertNull(getName(e3))
        assertNull(getName(e4))
        assertEquals(name, getName(e5))

        // To perform a certain operation only for non-null values, you can use the safe call operator together with let
        val listWithNulls: List<String?> = listOf("Kotlin", null)
        for (item in listWithNulls) {
            item?.let { println(it) }
        }
    }
}