package com.hades.example.leankotlin._5_functions._1_functions

// https://kotlinlang.org/docs/functions.html#function-usage
// https://book.kotlincn.net/text/functions.html
fun main() {

    /**
     * Example 1 :Function usage
     *
     */
    run {
        println(double(3))                  // 6s
    }

    /**
     * Example 1 : Function usage - Parameters
     */

    run {
        println(powerOf(4, 5))   // 20)
    }
    /**
     * Example 1 : Function usage - Default arguments
     */
    run {
        printString("Jerry")                // Jerry,123
        printString("Jerry", "56")    // Jerry,56

        val bytes = ByteArray(5);   // 0
        read(bytes, 1)  // 10

        val a = A();
        a.foo()         // 10
        a.foo(100)  // 100

        val b = B();
        b.foo()         // 10
        b.foo2()        // 10
        b.foo(100)  // 100

        foo(baz = 1)    // bar = 0,baz = 1

        foo2(1)    // bar = 0,baz = 1


        // [foo3] bar = 1,baz = 1
        // 使用baz 的默认值
        foo3(1) {
            println("hello")
        }

        // [foo3] bar = 0,baz = 1
        // 使用bar 和 baz 的默认值
        foo3(
            qux = {
                println("hello")
            }
        )

        // [foo3] bar = 0,baz = 1
        // 使用bar 和 baz 的默认值
        foo3() {
            println("hello")
        }
    }

    /**
     * Example 1 : Function usage - Named arguments
     */

    run { }
    /**
     * Example 1 : Function usage - Unit-returning functions
     */
    run { }


    /**
     * Example 1 : Function usage - Single-expression functions
     */
    run { }

    /**
     * Example 1 : Function usage - Explicit return types
     */

    run { }

    /**
     * Example 1 : Function usage - Variable number of arguments (varargs)
     */
    run { }


    /**
     * Example 1 : Function usage - Infix notation
     */

    run { }

}

/**
 * Example 1 :Function usage
 *
 */

// Kotlin 用关键字fun 表示函数
fun double(x: Int /* x is named argument*/): Int /* return value is Int type*/ {
    return x * 2;
}

/**
 * Example 1 : Function usage - Parameters
 */

// 函数参数用Pascal 表示法定义——name: type
fun powerOf(num: Int, exponent: Int): Int {
    return num * exponent
}

/**
 * Example 1 : Function usage - Default arguments
 */

// 使用默认参数可以较少重载

fun printString(message: String, prefix: String = "123") {
    println("$message,$prefix")
}

/**
 * Default param : off, len
 */
fun read(b: ByteArray, off: Int = 0, len: Int = b.size) {
    println(b.get(off + 1))
}

open class A {
    open fun foo(i: Int = 10) {
        println(i)
    }

    fun foo2(i: Int = 10) {
        println(i)
    }
}

class B : A() {
    override fun foo(i: Int) { // 覆盖方法与基类有相同默认参数值，但语法上必须在签名中去掉默认值
        println(i)
    }
}

// 如果默认值参数在无默认值参数的前面，必须使用named arguments的方式来调用函数
fun foo(bar: Int = 0, baz: Int) {
    println("bar = $bar,baz = $baz")
}

// 如果默认值参数在无默认值参数的后面，使用正常方式来调用函数
fun foo2(baz: Int, bar: Int = 0) {
    println("bar = $bar,baz = $baz")
}

// TODO:If a default parameter precedes a parameter with no default value, the default value can only be used by calling the function with named arguments:
fun foo3(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
    println("[foo3] bar = $bar,baz = $baz")
}


/**
 * Example 1 : Function usage - Named arguments
 */

/**
 * Example 1 : Function usage - Unit-returning functions
 */

/**
 * Example 1 : Function usage - Single-expression functions
 */
/**
 * Example 1 : Function usage - Explicit return types
 */
/**
 * Example 1 : Function usage - Variable number of arguments (varargs)
 */
/**
 * Example 1 : Function usage - Infix notation
 */
