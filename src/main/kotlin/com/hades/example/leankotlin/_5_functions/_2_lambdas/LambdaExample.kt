package com.hades.example.leankotlin._5_functions._2_lambdas


// https://kotlinlang.org/docs/lambdas.html
// https://book.kotlincn.net/text/lambdas.html
fun main() {

    /**
     *Example 1 : Higher-order functions and lambdas
     * 高阶函数与 lambda 表达式
     */
    run {
        // Kotlin中的函数，可以存储在变量与数据结构中，也可以作为参数传递给其他的Higher-order functions以及作为其他 Higher-order functions的返回值。

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
     * 高阶函数
     */

    run {
        // 高阶函数是将函数用作参数或返回值的函数。

        // combine 具有函数类型(R,T)->R，因此fold可以接受一个函数作为参数，combine是fold的一个函数类型的参数
        fun <T, R> Collection<T>.fold(initial: R, combine: (acc: R, nextElement: T) -> R): R {
            var accumulator: R = initial
            for (element: T in this) {
                accumulator = combine(accumulator, element)
            }
            return accumulator
        }
    }
    /**
     * Example 3 : Function types
     * 函数类型
     */
    run {
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
    }

    /**
     * Example 3 : Function types - Instantiating a function type
     * 函数类型实例化
     */

    run {
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
     * 函数类型实例调用
     */
    run {
        // 函数类型的值，可以通过invoke(...)操作符调用。 f.invoke(x) 或直接f(x)
        // 如果该值具有接收者类型，那么应该将接收者对象作为第一个参数传递。 调用带有接收者的函数类型值的另一个方式是在其前面加上接收者对象， 就好比该值是一个扩展函数：1.foo(2)。

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
     * 内联函数
     */
    // Ref : https://book.kotlincn.net/text/inline-functions.html
    // Ref : https://kotlinlang.org/docs/inline-functions.html#instantiating-a-function-type


    /**
     * Example 4 : Lambda expressions and anonymous functions
     * Lambda 表达式与匿名函数
     */
    run {
        // lambda 表达式与匿名函数是函数字面值，函数字面值即没有声明而是立即做为表达式传递的函数
        // e.g.,  max(strings, { a, b -> a.length < b.length })
        // 函数 max 是一个高阶函数，因为它接受一个函数作为第二个参数。 其第二个参数是一个表达式，它本身是一个函数，称为函数字面值，它等价于以下具名函数：
        //fun compare(a: String, b: String): Boolean = a.length < b.length
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - Lambda expression syntax
     * Lambda 表达式语法
     */
    run {
        val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
        // Lambda 表达式语法：
        // Lambda表达式在{}中。
        // 函数体跟在 -> 后面
        // 如果推断出该lambda的返回值不是Unit，那么该lambda主体中的最后一个（或可能是单个）表达式会视为返回值

        println(sum(1, 5))
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - Passing trailing lambdas
     * TODO:传递末尾的 lambda 表达式
     */
    run {
        // 如果函数的最后一个参数是函数，那么作为相应参数传入的lambda表达式可以放在圆括号之外. 称为 拖尾 lambda 表达式 :
        val items = arrayListOf(1, 3)
        val product = items.fold(1) { acc, e ->
            acc * e
        }

        // 如果该lambda表达式是调用时唯一的参数，圆括号也可以完全省略：
        // run{println("hi")}
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - it: implicit name of a single parameter
     * it：单个参数的隐式名称
     */

    run {
        // 当一个lambda表达式只有一个参数时，该参数会隐式声明为it. 那么参数和->可以省略

        //val printInt: (Int) -> Unit = { x: Int -> println(x) }
        val printInt: (Int) -> Unit = { println(it) } //  等价

        printInt(5)
        printInt(5)
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - Returning a value from a lambda expression
     * TODO: 从 lambda 表达式中返回一个值
     */
    run {
        // 可以使用限定的返回语法从 lambda 显式返回一个值。 否则，将隐式返回最后一个表达式的值。

        val ints: ArrayList<Int> = arrayListOf(-1, 1, 2)

        // 2种写法等价
//        ints.filter {
//            val shouldFilter = it > 0
//            shouldFilter
//        }

        ints.filter {
            val shouldFilter = it > 0
            return@filter shouldFilter
        }
        println("ints is $ints")


        val strings = arrayListOf<String>("abc", "xy799", "abcde")

        // 这一约定连同在圆括号外传递 lambda 表达式一起支持 LINQ-风格 的代码：
        val result = strings.filter { it.length == 5 }.sortedBy { it }.map { it.uppercase() }
        println(result) // [ABCDE, XY799]
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - Underscore for unused variables
     * 下划线用于未使用的变量
     */
    run {
        // 如果 lambda 表达式的参数未使用，那么可以用下划线取代其名称：
        val map = hashMapOf<String, Int>()
        map.put("a", 1)
        map.put("b", 2)
        map.forEach { (_, value) ->
            println("$value")
        }
    }
    /**
     * Example 4 : Lambda expressions and anonymous functions - Destructuring in lambdas
     */
    // 在 lambda 表达式中解构
    // Ref : https://book.kotlincn.net/text/destructuring-declarations.html

    /**
     * Example 4 : Lambda expressions and anonymous functions - Anonymous functions
     * TODO:匿名函数
     */
    run {
        //  lambda表达式中因为函数类型可以自动推断出来，因此可以不指定函数的返回类型
        // 如何lambda表达式中显式指定函数的返回类型？用语法：匿名函数

        // 匿名函数的返回类型推断机制与正常函数一样：对于具有表达式函数体的匿名函数将自动推断返回类型，但具有代码块函数体的返回类型必须显式指定（或者已假定为 Unit）。

        // 当匿名函数作为参数传递时，需将其放在括号内。 允许将函数留在圆括号外的简写语法仅适用于 lambda 表达式。
        // Lambda表达式与匿名函数之间的另一个区别是非局部返回的行为。 一个不带标签的 return 语句总是在用 fun 关键字声明的函数中返回。这意味着 lambda 表达式中的 return 将从包含它的函数返回，而匿名函数中的 return 将从匿名函数自身返回。

        // 匿名函数，它的函数体是一个表达式
        fun(x: Int, y: Int): Int = x + y

        // 匿名函数，它的函数体也可以是一个代码块
        fun(x: Int, y: Int): Int {
            return x + y
        }

        // 参数和返回类型的指定方式与常规函数相同，除了能够从上下文推断出的参数类型可以省略：
        val ints = arrayListOf(-1, 1, 3)
        ints.filter(fun(item) = item > 0)

        // TODO: 如何调用filter
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - Closures
     * TODO:Closures （闭包）
     */
    run {
        // Lambda 表达式或者匿名函数（以及局部函数和对象表达式） 可以访问其闭包 ，其中包含在外部作用域中声明的变量。
        // 在 lambda 表达式中可以修改闭包中捕获的变量：
        var sum = 0
        val ints = arrayListOf(1, 2, 3, -1)
        ints.filter { it > 0 }.forEach { sum += it } //  在lambda
        println(sum)  // 6


        // 扩展：什么是闭包？
        // https://segmentfault.com/a/1190000013333527
        // https://blog.csdn.net/zhongyili_sohu/article/details/105421554
        // 闭包的概念1：函数里面声明函数，函数里面返回函数，就是闭包
        // 闭包的概念2:一个函数A可以另一个函数B的局部变量，即使另一个函数B执行完了也没关系。把满足这种条件的函数A叫做闭包

        // 闭包的作用？
        // 函数内部的局部变量的状态保存住了（变量的值就是状态）
        // 闭包能够读取其他函数内部变量的函数

        // 普通函数不带状态：调用完毕后，其里面所有内容会被释放掉。下次调用时，里面的变量会再次声明
        fun test() {
            var a = 3
            a++
            println(a)
        }

        // 闭包让函数带状态：调用完毕后，其里面所有内容不会被释放掉。下次调用时，里面的变量不用再次声明
        fun test2(): () -> Unit {
            var a = 3
            return fun() {
                a++
                println(a)
            }
        }


        // 扩展
        test()  // 4
        test()  // 4
        test()  // 4
        println()

        val test2 = test2()
        test2() // 5
        test2() // 6
        test2() // 7
    }

    /**
     * Example 4 : Lambda expressions and anonymous functions - Function literals with receiver
     * TODO:带有接收者的函数字面值
     */
    run {
        // TODO: 如何调用sum
        val sum: Int.(Int) -> Int = { other ->
            plus(other) // 接收者对象上调用了 plus
        }

        // 匿名函数语法允许你直接指定函数字面值的接收者类型
        val sum2 = fun Int.(other: Int): Int = this + other

        // 当接收者类型可以从上下文推断时，lambda 表达式可以用作带接收者的函数字面值。 One of the most important examples of their usage is type-safe builders:
        class HTML {
            fun body() {

            }
        }

        fun html(init: HTML.() -> Unit): HTML {
            val html = HTML()  // 创建接收者对象
            html.init()     // 将该接收者对象传给该 lambda
            return html
        }

        html {       // 带接收者的 lambda 由此开始
            body()   // 调用该接收者对象的一个方法
        }
    }
}



