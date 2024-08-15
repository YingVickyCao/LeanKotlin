package com.hades.example.leankotlin._6_null_safety

import com.hades.example.leankotlin._3_control_flow._2_loop.for_loops
import kotlin.test.*

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

        // A safe call can also be placed on the left side of an assignment.  if one of the receivers in the safe calls chain is null, the assignment is skipped and the expression on the right is not evaluated at all
        // If either `person` or `person.department` is null, the function is not called:
        val person = Employee(null)
        person?.department?.head = Head("samsung")
        assertNull(getName(person))
    }

    /**
     * Nullable receiver
     */
    @Test
    fun test4() {
        // Extension functions can be defined on a nullable receiver. This way you can specify behaviour for null values without the need to use null-checking logic at each call-site.
        data class Person(var name: String?)

        val person: Person? = null
        println(person.toString()) // person is null , does not throw an exception

        //If you want your toString() invocation to return a nullable string, use the safe-call operator ?.
        val name: String? = person?.toString()
        if (null == name) {
            // handle the case where name wa null
        }
    }

    /**
     * Elvis operator
     */
    @Test
    fun test5() {
        var b: String? = null
        val length: Int = if (b != null) b.length else -1
        assertEquals(-1, length)

        // also express this with the Elvis operator ?:
        val length2 = b?.length ?: -1 // if b?.length is not null, Elvis operator returns it, otherwise it returns the expression to the right.
        assertEquals(-1, length2)

        // throw and return can be used on the right-hand side of elvis operator
        fun foo(node: Node): String? {
            val parent = node.parent ?: return null
            val name = node.name ?: return null
            return "$parent:$name"
        }

        val result = foo(Node("a", "b"))
        assertEquals("a:b", result)

        val result2 = foo(Node("a", null))
        assertEquals(null, result2)
    }

    data class Node(var parent: String?, var name: String?)

    /**
     * The !! operator
     */
    @Test
    fun test6() {
        val str: String? = null
//        val l = str!!.length // java.lang.NullPointerException

        val str2: String? = "abc"
        val l2 = str2!!.length
        assertEquals(3, l2)
    }

    /**
     * Safe casts
     */

    @Test
    fun test7() {
        // use safe casts that return null if the clas type cast was not successful
        val a: String? = "abc"
        val aInt: Int? = a as? Int
        assertEquals(null, aInt)

        val b: Int = 5
        val aInt2: Int? = b as? Int
        assertEquals(5, aInt2)
    }

    /**
     * Collections of a nullable type
     */
    @Test
    fun test8() {
        // want to filter non-nullable elements from a collection of elements of a nullable type
        val nullableList: List<Int?> = listOf(1, 2, null, 4)
        val intList: List<Int> = nullableList.filterNotNull()
        assertEquals(listOf(1, 2, 4).toString(), intList.toString())

        val nullableStringList: List<String?> = listOf("a", null, "", "d")
        val stringList: List<String> = nullableStringList.filterNotNull()
        assertEquals(listOf("a", "", "d").toString(), stringList.toString())
    }
}