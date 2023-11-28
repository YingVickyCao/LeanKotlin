package com.hades.example.leankotlin._5_functions._2_lambdas


// https://kotlinlang.org/docs/lambdas.html
// https://book.kotlincn.net/text/lambdas.html
fun main() {

    /**
     *Example 1 : Higher-order functions and lambdas
     */
    run {
        // 为了调用fold，需要传递一个函数类型的实例作为参数
        val items = listOf(1, 2, 3, 4, 5)

        // lambdas表达式是{}括起来的代码块
        val sum1 = items.fold(0,
            // 如果一个lambda表达式有参数，前面是参数，后面跟 ->
            { acc: Int, i: Int ->
                print("acc=$acc,i=$i, ")
                val result = acc + i
                println("result=$result")
                // lambda表达式中最后一个表达式是返回值
                result

            })

        // 等价写法
        val sum2 = items.fold(0) { acc: Int, i: Int ->
            print("acc=$acc,i=$i, ")
            val result = acc + i
            println("result=$result")
            result

        }
        println(sum1)   // 15
        println(sum2)

        val joinedString1 = items.fold("Elements:",
            // lambda表达式的参数类型能够推到出来，那么参数类型是可选的，可以省略
            { acc, i ->
                acc + " " + i
            })

        // 等价
        val joinedString2 = items.fold("Elements:") { acc, i ->
            "$acc $i"
        }
        println(joinedString1)  // Elements: 1 2 3 4 5
        println(joinedString2)  // Elements: 1 2 3 4 5

        // TODO: 函数引用也可以用于高阶函数调用
        val product = items.fold(1, Int::times)
        println(product)    // 120
    }

    /**
     * Example 2 : Higher-order functions
     */

    /**
     * Example 3 : Function types
     */
    // 函数类型

    /**
     * Example 3 : Function types - Instantiating a function type
     */
    // 函数类型实例化

    run {
        val repeatFun: String.(Int) -> String = { times ->
            this.repeat(times)
        }
        val repeatFunTwoParameters: (String, Int) -> String = repeatFun // ok

        fun runTransformation(f: (String, Int) -> String): String {
            return f("hello", 3)
        }

        val result = runTransformation(repeatFun)
        println(result) // hellohellohello
    }
    /**
     * Example 3 : Function types - Invoking a function type instance
     */
    // 函数类型实例调用
    run {
        // TODO: String::plus
        val stringPlus: (String, String) -> String = String::plus
        val intPlus: Int.(Int) -> Int = Int::plus

        println(stringPlus.invoke("<", ">"))    // <>
        println(stringPlus("<", ">"))           // <>

        println(intPlus.invoke(1, 1))          // 2
        println(intPlus(1, 1))                 // 2
    }


    /**
     * Example 3 : Function types - Inline functions
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Lambda expression syntax
     *
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Passing trailing lambdas
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - it: implicit name of a single parameter
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Returning a value from a lambda expression
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Underscore for unused variables
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Destructuring in lambdas
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Anonymous functions
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Closures
     */

    /**
     * Example 4 : Lambda expressions and anonymous functions - Function literals with receiver
     */
}

/**
 *Example 1 : Higher-order functions and lambdas
 */
// Kotlin中的函数，可以存储在变量与数据结构中，也可以作为参数传递给其他的Higher-order functions以及作为其他 Higher-order functions的返回值。

/**
 * Example 2 : Higher-order functions
 */

// 高阶函数是将函数用作参数或返回值的函数。

// combine 具有函数类型(R,T)->R，因此fold可以接受一个函数作为参数，combine是fold的一个函数类型的参数
fun <T, R> Collection<T>.fold(initial: R, combine: (acc: R, nextElement: T) -> R): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

/**
 * Example 3 : Function types
 */

// 1 (parameter types) ->  return type:
// (A,B) -> C ：表示 参数类型列表为A、B，返回类型为 C
// () -> A ： 表示 参数类型列表为空，返回类型为 A

// 2 function type having an additional receiver type
// A.(B) -> C  : 表示函数类型可以有一个额外的接收者类型。在A 的接收者对象上以一个 B 类型参数来调用并返回一个 C 类型值的函数。

// 3 Suspending function having suspend modifier.挂起函数属于函数类型的特殊种类，它的表示法中有一个 suspend 修饰符
// suspend () -> Unit 或者 suspend A.(B) -> C

// 例子
// (x:Int， y:Int)->Point

// 4 ((Int,Int) -> Int)?  ： 使用圆括号指定函数类型为可空

// 5 函数类型与圆括号进行接合 ： (Int) -> ((Int) -> Unit)  等价于 (Int) -> (Int) -> Unit

// 6 通过使用类型别名给函数类型起一个别称
//typealias clickHandler = (MouseEvent.Button, ClickEvent )-> Unit

/**
 * Example 3 : Function types - Instantiating a function type
 */

// 如何函数类型的实例
// 1 使用函数字面值的代码块，采用以下形式之一：
//  (1)lambda 表达式: { a, b -> a + b },
//  (2)匿名函数: fun(s: String): Int { return s.toIntOrNull() ?: 0 }
//  带有接收者的函数字面值可用作带有接收者的函数类型的值。
// 2 使用已有声明的可调用引用：
//  (1)顶层、局部、成员、扩展函数：::isOdd、 String::toInt，
//  (2)顶层、成员、扩展属性：List<Int>::size，
//  (3)构造函数：::Regex
//  这包括指向特定实例成员的绑定的可调用引用：foo::toString。
// 3 使用实现函数类型接口的自定义类的实例：
class IntTransformer : (Int) -> Int {
    override fun invoke(x: Int): Int {
        TODO("Not yet implemented")
    }
}

val initFunction: (Int) -> Int = IntTransformer()

// 如果有足够信息，编译器可以推断变量的函数类型：
val a = { i: Int -> i + 1 } // （Int）-> Int

// 带与不带接收者的函数类型非字面值可以互换，其中接收者可以替代第一个参数，反之亦然。例如，(A, B) -> C 类型的值可以传给或赋值给期待 A.(B) -> C 类型值的地方，反之亦然


/**
 * Example 3 : Function types - Invoking a function type instance
 */
// 函数类型的值，可以通过invoke(...)操作符调用。 f.invoke(x) 或直接f(x)
// 如果该值具有接收者类型，那么应该将接收者对象作为第一个参数传递。 调用带有接收者的函数类型值的另一个方式是在其前面加上接收者对象， 就好比该值是一个扩展函数：1.foo(2)。

/**
 * Example 3 : Function types - Inline functions
 */
// Ref : https://book.kotlincn.net/text/inline-functions.html
// Ref : https://kotlinlang.org/docs/inline-functions.html#instantiating-a-function-type


/**
 * Example 4 : Lambda expressions and anonymous functions
 */
// lambda 表达式与匿名函数是函数字面值，函数字面值即没有声明而是立即做为表达式传递的函数
// e.g.,  max(strings, { a, b -> a.length < b.length })
// 函数 max 是一个高阶函数，因为它接受一个函数作为第二个参数。 其第二个参数是一个表达式，它本身是一个函数，称为函数字面值，它等价于以下具名函数：
//fun compare(a: String, b: String): Boolean = a.length < b.length

/**
 * Example 4 : Lambda expressions and anonymous functions - Lambda expression syntax
 *
 */
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
// Lambda
// 函数体跟在{}后面
//


/**
 * Example 4 : Lambda expressions and anonymous functions - Passing trailing lambdas
 */


/**
 * Example 4 : Lambda expressions and anonymous functions - it: implicit name of a single parameter
 */


/**
 * Example 4 : Lambda expressions and anonymous functions - Returning a value from a lambda expression
 */

/**
 * Example 4 : Lambda expressions and anonymous functions - Underscore for unused variables
 */

/**
 * Example 4 : Lambda expressions and anonymous functions - Destructuring in lambdas
 */

/**
 * Example 4 : Lambda expressions and anonymous functions - Anonymous functions
 */

/**
 * Example 4 : Lambda expressions and anonymous functions - Closures
 */

/**
 * Example 4 : Lambda expressions and anonymous functions - Function literals with receiver
 */