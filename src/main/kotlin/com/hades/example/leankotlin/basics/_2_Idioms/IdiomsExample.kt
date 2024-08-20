package com.hades.example.leankotlin.basics._2_Idioms

import com.hades.example.leankotlin.concepts._1_types._2_data_type._2_object_type.array

/**
 * Create DTOs
 */
private data class Customer(var name: String, val email: String)

private fun test1() {
    run {
        val customer1 = Customer("A", "hi@abc.com")
        println(customer1.name) // A
        println(customer1.email) // "hi@abc.com"
        println(customer1.toString()) // Customer(name=A, email=hi@abc.com)
        println(customer1.component1()) // A
        println(customer1.component2()) // hi@abc.com
        customer1.name = "B"
    }

    run {
        val customer1 = Customer("A", "hi@abc.com")
        val customer2 = Customer("A", "hi@abc.com")
        val customer3 = Customer("B", "hi@abc.com")
        println(customer1 == customer2) // equal,true
        println(customer1 == customer3) // equal,false
        println(customer1 === customer2) // false
        println(customer1 === customer3) // false
        println(customer1.hashCode()) // -1103913579
        println(customer2.hashCode()) // -1103913579
        println(customer3.hashCode()) // -1103913548

        val customer4 = customer1.copy()
        println(customer1 == customer4) // equal,true
        println(customer1 === customer4)// false
    }
}

/**
 * Default values for function parameters
 */
private fun test2() {
    fun foo(x: Int = 0, y: String = "") {
        println("($x,$y)")
    }
    foo()               // (0,)
    foo(1, "a")
    foo(y = "a")        // (0,a)
    foo(1)           // (1,)
    foo(x = 1)          // (1,)
}

/**
 * Filter a list
 */
private fun test3() {
    run {
        val list = arrayOf(1, -10, 3, -2, 30)
        val positions = list.filter { x -> x > 0 }
        println(positions) // [1, 3, 30]
    }
    // OR
    run {
        val list = arrayOf(1, -10, 3, -2, 30)
        val positions = list.filter { it > 0 }
        println(positions) // [1, 3, 30]
    }
}

/**
 * Check the presence of an element in a collection
 */

private fun test4() {
    val emails = arrayOf("jane@example.com", "abc@example.com")
    if ("john@example.com" !in emails) {
        println("john@example.com not in list")
    }

    if ("jane@example.com" in emails) {
        println("jane@example.com in list")
    }
}

/**
 * String interpolation
 */
private fun test5() {
    // Reads a string and returns null if the input cannot be converted to an integer.
    val wrongInt = readln().toIntOrNull()
    println(wrongInt)

    // Reads a string that can be converted into an integer and returns an integer.
    val correctInt = readln().toIntOrNull()
    println(correctInt)
}

/**
 * Instance checks
 */

private fun test6() {
    class Foo
    class Bar

    val list = arrayOf(Foo(), Bar(), String())
    for (item in list) {
        println("item=${item::class.java.simpleName}")
        when (item) {
            is Foo -> println("Foo")
            is Bar -> println("Bar")
            else -> println("others")
        }
    }
}

/**
 * Read-only list
 */
private fun test7() {
    val list = arrayOf("A", "B")
    println(list)
}

/**
 * Read-only map
 */

private fun test8() {
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    println(map) // {a=1, b=2, c=3}
    // For readonly only map, can sonly get value by key
    println(map["b"]) // 2
//    map["a"] = 10
}


/**
 *Access a map entry
 */
private fun test9() {
    val map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
    println(map) // {a=1, b=2, c=3}
    // For mutable map, can get value or set value by key
    println(map["b"]) // 2
    map["a"] = 10
    map["z"] = 10
    println(map) // {a=10, b=2, c=3, z=10}
}

/**
 * Traverse a map or a list of pairs
 */
private fun test10() {
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    // a -> 1
    // b -> 2
    // c -> 3
    for ((k, v) in map) {
        println("$k -> $v")
    }
}

/**
 * Iterate over a range
 */
private fun test11() {
    // 1  2  3  4  5  6  7  8  9  10
    for (i in 1..10) {// [1,10], closed-end range: includes 10
        print("$i  ")
    }
    println()
    // 1  2  3  4  5  6  7  8  9
    for (i in 1..<10) { // [1,10), open-end range: does not include 10
        print("$i  ")
    }
    println()

    // 2  4  6  8  10
    for (i in 2..10 step 2) { // [1,10] with step 2
        print("$i  ")
    }
    println()

    // 10  9  8  7  6  5  4  3  2  1
    for (i in 10 downTo 1) { // [10,1]
        print("$i  ")
    }
    println()

    // 1  2  3  4  5  6  7  8  9  10
    (1..10).forEach { // [1,10]
        print("$it  ")
    }
}


/**
 * Lazy property
 */
private fun test12() {
    val p: String by lazy { // the value is computed only on the fast access
        "abc"
    }
    println(p)
}

/**
 * Extension functions
 */

private fun test13() {
    fun String.changeToUppercase(): String {
        return uppercase()
    }

    val result = "Convert this to camelcase".changeToUppercase()
    println(result) // CONVERT THIS TO CAMELCASE
}

/**
 * Create a singleton
 */
private fun test14() {
    println(Resource.name)
}

object Resource {
    val name = "Resource"
}

/**
 * Use inline value classes for type-safe values
 */
private fun test15() {
    val employeeId = EmployeeId("id_10")
    val customerId = CustomerId("custom_id_10")
    println(employeeId) // EmployeeId(id=id_10)
    println(customerId) // CustomerId(id=custom_id_10)
    println(employeeId.javaClass.simpleName)    // EmployeeId
    println(customerId.javaClass.simpleName)    // CustomerId
}

@JvmInline
value class EmployeeId(private val id: String)

@JvmInline
value class CustomerId(private val id: String)

/**
 * Instantiate an abstract class
 */
private fun test16() {
    abstract class MyAbstractClass {
        abstract fun sleep()
    }

    val myObject = object : MyAbstractClass() {
        override fun sleep() {
            println("A is sleeping")
        }
    }
    myObject.sleep() // A is sleeping
}

/**
 * If-not-null shorthand
 */
private fun test17() {
    var str: String? = "a"
    // length is printed if str is not null
    // str?.length : return length if str is not null
    println(str?.length) // 1
    str = null

    // length is not printed if str is null
    println(str?.length) // null
}

/**
 * If-not-null-else shorthand
 */
private fun test18() {
    var str: String? = "a"
    // if str is not null, print length, else print "empty"
    println(str?.length ?: "empty") // 1
    str = null
    println(str?.length ?: "empty") // "empty"

    // To calculate a more complicated fallback value in code block, use run
    val strSize: Int = str?.length ?: run {
        //mock run
        -1
    }
    println(strSize) // 0
}

/**
 * Execute a statement if null
 */
private fun test19() {
    val values = mapOf("a" to "1", "email" to "abc@example.com")
    val email = values["email"] ?: throw IllegalArgumentException("email is missing")
    println(email) // abc@example.com
    val name = values["name"] ?: throw IllegalArgumentException("name is missing") // Exception in thread "main" java.lang.IllegalArgumentException: name is missing
    println(name)
}

/**
 * Get first item of a possibly empty collection
 */
private fun test20() {
    val emails = arrayOf<String>()
    val mainEmail = emails.firstOrNull() ?: ""
//    val mainEmail = emails.first() ?: "" // Exception in thread "main" java.util.NoSuchElementException: Array is empty.
    println(mainEmail)
}

/**
 * Execute if not null
 */
private fun test21() {
    var value: String? = null
    value?.let {
        // execute this block if not null
        println("[1]$value")
    }
    value = "abc"
    value?.let {
        // execute this block if not null
        println("[2]$value")
    }
}

/**
 * Map nullable value if not null
 */
private fun test22() {
    val value: String? = null
    fun transformValue(value: String): String {
        return value.uppercase()
    }

    val defaultValue = "empty"
    val mapped = value?.let { transformValue(it) } ?: defaultValue
    println(mapped) // "empty"
}

/**
 * Return on when statement
 */
private fun test23() {
    println(transform("Red")) // 0
    println(transform("Alpha")) // java.lang.IllegalArgumentException: Invalid color param value
}

private fun transform(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> {
            throw IllegalArgumentException("Invalid color param value")
        }
    }
}

/**
 * try-catch expression
 */
private fun test24() {
    @Throws(ArithmeticException::class)
    fun count() {
        // mock count
        throw ArithmeticException("mocked ArithmeticException ")
    }

    @Throws(IllegalArgumentException::class)
    fun test() {
        val result = try {
            count()
        } catch (ex: ArithmeticException) {
            throw IllegalArgumentException(ex)
        }
    }
    try {
        test()
    } catch (ex: IllegalArgumentException) {
        println(ex)
    }
}

private fun test25() {
    val x = 2
    val y = if (x == 1) {
        "one"
    } else if (x == 2) {
        "two"
    } else {
        "other"
    }
    println(y)
}

/**
 * if expression
 */

fun main() {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
//    test6()
//    test7()
//    test8()
//    test9()
//    test10()
//    test11()
//    test12()
//    test13()
//    test14()
//    test15()
//    test16()
//    test17()
//    test18()
//    test19()
//    test20()
//    test21()
//    test22()
//    test23()
//    test24()
    test25()
}