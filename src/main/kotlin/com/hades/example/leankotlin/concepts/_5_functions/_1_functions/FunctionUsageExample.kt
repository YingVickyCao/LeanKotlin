package com.hades.example.leankotlin._5_functions._1_functions

import java.util.ArrayList

// https://kotlinlang.org/docs/functions.html#function-usage
// https://book.kotlincn.net/text/functions.html
fun main() {

    /**
     * Example 1 :Function usage
     * 函数用法
     */
    run {
        // Kotlin 用关键字fun 表示函数
        fun double(x: Int /* x is named argument*/): Int /* return value is Int type*/ {
            return x * 2;
        }

        println(double(3))                  // 6s
    }

    /**
     * Example 1 : Function usage - Parameters
     * 参数
     */

    run {
        // 函数参数用Pascal 表示法定义——name: type
        fun powerOf(num: Int, exponent: Int): Int {
            return num * exponent
        }

        println(powerOf(4, 5))   // 20)
    }


    /**
     * Example 1 : Function usage - Default arguments
     * 默认参数
     */
    run {
        // 使用默认参数可以较少重载
        fun printString(message: String, prefix: String = "123") {
            println("$message,$prefix")
        }

        printString("Jerry")                // Jerry,123
        printString("Jerry", "56")    // Jerry,56

        /**
         * Default param : off, len
         */
        fun read(b: ByteArray, off: Int = 0, len: Int = b.size) {
            println(b.get(off + 1))
        }

        val bytes = ByteArray(5);   // 0
        read(bytes, 1)  // 10

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


        val a = A();
        a.foo()         // 10
        a.foo(100)  // 100

        val b = B();
        b.foo()         // 10
        b.foo2()        // 10
        b.foo(100)  // 100


        // 如果默认值参数在无默认值参数的前面，必须使用named arguments的方式来调用函数
        fun foo(bar: Int = 0, baz: Int) {
            println("bar = $bar,baz = $baz")
        }

        foo(baz = 1)    // bar = 0,baz = 1

        // 如果默认值参数在无默认值参数的后面，使用正常方式来调用函数
        fun foo2(baz: Int, bar: Int = 0) {
            println("bar = $bar,baz = $baz")
        }
        foo2(1)    // bar = 0,baz = 1


        // TODO:If a default parameter precedes a parameter with no default value, the default value can only be used by calling the function with named arguments:
        fun foo3(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
            println("[foo3] bar = $bar,baz = $baz")
        }
        // [foo3] bar = 1,baz = 1
        // 使用baz 的默认值
        foo3(1) {
            println("hello")
        }

        // [foo3] bar = 0,baz = 1
        // 使用bar 和 baz 的默认值
        foo3(qux = {
            println("hello")
        })

        // [foo3] bar = 0,baz = 1
        // 使用bar 和 baz 的默认值
        foo3() {
            println("hello")
        }
    }

    /**
     * Example 1 : Function usage - Named arguments
     * 具名参数
     */

    run {
        // 调用函数时，指定一个参数或多个参数, 让调用更可读
        fun reformat(str: String, normalizeCase: Boolean = true, upperCaseFirstLetter: Boolean = true, divideByCamelHumps: Boolean = false, wordSeparator: Char = ' ') {

        }

        // pass a variable number of arguments (vararg) with names using the spread operator:
        fun foo4(vararg strings: String) {
            println(strings.contentDeepToString())
        }

        reformat("str2")
        reformat("str2", false)
        reformat("str2", false, true)
        reformat("str2", false, true, false)
        reformat("str2", false, true, false, '-')
        reformat("str", false, upperCaseFirstLetter = false, divideByCamelHumps = true, '-')
        reformat("str", upperCaseFirstLetter = false, wordSeparator = '-')

        foo4(strings = arrayOf("a", "b", "c"))  // [a, b, c]
    }
    /**
     * Example 1 : Function usage - Unit-returning functions
     * 返回 Unit 的函数
     */
    run {
        // 一个函数不返回有用的值时，其返回值是Unit。Unit返回值类型可以省略
// This method does not have return
        fun printInt(id: Int) {
            println(id)
        }

        // This method does not have return
        fun printInt2(id: Int): Unit {
            println(id)
        }

        // this method returns bool value
        fun printCourse(course: String): Boolean {
            //
            println("Course is $course")
            return true
        }

        printInt(5)
        printInt2(5)

        val course = "Math"
        println(printCourse(course)) // true
    }


    /**
     * Example 1 : Function usage - Single-expression functions
     * 单表达式函数
     */
    run {
        //fun doubleValue(x: Int): Int {
        //    return x * 2
        //}

        // 当函数体由单个表达构成时，可省略花括号
        fun doubleValue(x: Int): Int = x * 2
        println(doubleValue(3)) // 6
    }

    /**
     * Example 1 : Function usage - Explicit return types
     * 显式返回类型
     *
     */
    run {
        // 具有块代码体的函数必须指定显式返回类型，如果返回值是Unit，可以省略。
    }

    /**
     * Example 1 : Function usage - Variable number of arguments (varargs)
     * 可变数量的参数（varargs）
     */
    run {
        // 函数的参数（通常是最后一个），用可以vararg修饰，表示可变参数
        // 只有一个参数可以标注为 vararg。
        // 如果vararg 参数不是列表中的最后一个参数， 可以使用具名参数语法传递其后的参数的值
        // 如果参数具有函数类型，则通过在括号外部传一个 lambda。
        // 如果已经有一个数组并希望将其内容传给该函数，那么使用展开操作符（在数组前面加 *）
        fun <T> asList(vararg ts: T): List<T> {
            val result = ArrayList<T>()
            for (t in ts) {
                result.add(t)
            }
            return result
        }

        // 1  2  3
        val list: List<Int> = asList(1, 2, 3)
        for (item in list) {
            print("$item  ")
        }
        println()

        val a = intArrayOf(1, 2, 3)
        println()

        // -1  1  2  3  4
        val list2 = asList(-1, *a.toTypedArray(), 4) // 展开操作符（在数组前面加 *）
        for (item in list2) {
            print("$item  ")
        }
        println()
    }


    /**
     * Example 1 : Function usage - Infix notation
     * 中缀表示法
     */

    run {
        // infix 表示 中缀表示法（忽略该调用的点与圆括号）调用
        // 中缀函数必须满足以下要求：
        // 它们必须是成员函数或扩展函数。
        // 它们必须只有一个参数。
        // 其参数不得接受可变数量的参数且不能有默认值。

        // 中缀函数调用的优先级低于算术操作符、类型转换以及 rangeTo 操作符。
        // 1 shl 2 + 3 等价于 1 shl (2 + 3)
        // 0 until n * 2 等价于 0 until (n * 2)
        // xs union ys as Set<*> 等价于 xs union (ys as Set<*>)

        // 中缀函数调用的优先级高于布尔操作符 && 与 ||、is- 与 in- 检测以及其他一些操作符。
        // a && b xor c 等价于 a && (b xor c)
        // a xor b in c 等价于 (a xor b) in c

        // 1 Define an infix function on Int
        infix fun Int.times(str: String) = str.repeat(this)
        println(2 times "bye ") // bye bye
        // is equal to
        println("bye ".repeat(2))   // bye bye

        // 2 Create a pair using infix function on from standard lib
        val pair = "A" to "BC"
        println(pair)           // (A, BC)
        println(pair.first)     // A
        println(pair.second)    // BC

        val pair2 = "A" to "BC" to "d"
        println(pair2)              // ((A, BC), d)
        println(pair2.first.first)  // A
        println(pair2.first.second) // BC
        println(pair2.second)       // d

        // 3 Custom a pair
        infix fun String.onto(other: String) = Pair(this, other)
        var myPair = "Hello" onto "World"
        println(myPair)         // (Hello, World)
        println(myPair.first)   // Hello
        println(myPair.second)  // World
        println(myPair.second.onto("XY")) // (World, XY)


        infix fun Int.sh(x: Int): Int {
            return this + x //  this 指的是 Int.sh 中 Int
        }
        println(10 sh 20)       // 30
        println(10.sh(20))   // 30  等价

        println(1 sh 2 + 3)     // 6
        println(1 sh (2 + 3))   // 6  等价

        val n: Int = 2
        0 until n * 2
        0 until (n * 2) // 等价

        // TODO: xs union ys as Set<*> 等价于 xs union (ys as Set<*>)
        // xs union ys as Set<*> 等价于 xs union (ys as Set<*>)

        // 中缀函数总是要求指定接收者与参数。当使用中缀表示法在当前接收者上调用方法时，需要显式使用 this。
        class MyStringCollection {
            private infix fun add(str: String) {

            }

            fun build() {
                this add "abc"  // ok
                add("abc")      // ok
                // add "abc"    // 错误：必须指定接收者
            }
        }
    }

}
