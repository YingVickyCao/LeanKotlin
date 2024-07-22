package com.hades.example.leankotlin._5_functions._3_inline_functions

// 内联函数
// https://book.kotlincn.net/text/inline-functions.html
// https://kotlinlang.org/docs/inline-functions.html


fun main() {
//    example1()
//    example2()
    example3()
//    example4()
//    example5()
//    example6()
}
// 使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 闭包那些在函数体内会访问到的变量的作用域。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。
// 但是在许多情况下通过内联化 lambda 表达式可以消除这类的开销。
/**
 * Example 1 : Inline functions
 * 内联函数
 */
// 什么是内联函数？调用内联函数的地方，不是通过方法间压栈进栈的方式，而是直接将函数方法体中的代码移动到调用的地方。从而减少函数调用以及对象生成。
// 内联的本质？在编译期间，把调用这个函数的地方用这个函数的方法体进行替换。
// 内联可能导致生成的代码增加。

// 什么时候应该用内联？什么时候不用内联？
// 适合带有lambda参数的函数
// 避免内联过大函数
private fun sum(num: Int, num2: Int): Int {
    return num + num2
}

// Warning : Expected performance impact from inlining is insignificant. Inlining works best for functions with parameters of functional types
private inline fun sum2(num: Int, num2: Int): Int {
    return num + num2
}

private inline fun foo(body: () -> Unit) {
    body.invoke()
}

fun example1() {
    sum(1, 2)
    sum2(1, 2)

    // 内联函数适合带有lambda参数的函数
    foo { println("1344") } // 1344
}

/**
 * Example 2 : noinline
 */
// 用noinline 标记不希望内联的函数参数
inline fun foo(inlinedFunc: () -> Unit, noinline notInlinedFunc: () -> Unit) {

}

fun multiply(n1: Int, n2: Int, inlined: (Int, Int) -> Int): Int {
    return inlined.invoke(n1, n2)
}

fun example2() {
    val result = multiply(2, 3, inlined = { n1, n2 ->
        n1 * n2
//        return@multiply n1 * n2
    })
    println("multiply result=$result")
}

/**
 * Example 3 : Non-local returns
 */
fun example3() {
    // A bare return is forbidden inside a lambda because a lambda cannot make the enclosing function return
    fun multiply2(n1: Int, n2: Int, callback: (Int, Int) -> Int): Int {
        return callback.invoke(n1, n2)
    }

    val result = multiply2(2, 3, callback = { n1, n2 ->
        n1 * n2
        // cannot return
    })
    println("multiply result=$result")

    // But if the function the lambda is passed to is inlined, the return can be inlined
    val result2 = multiply(2, 3, inlined = { n1, n2 ->
//        n1 * n2 // ok
        return@multiply n1 * n2 //ok
    })
    println("multiply result=$result2")
}

/**
 * Example 4 : Reified type parameters
 */
fun example4() {

}

/**
 * Example 5 : Inline properties
 */
// The inline modifier can be used on accessors of properties that don't have backing fields.
// inline accessors are inlined as regular inline functions
class Foo
class Stu {
    val foo: Foo
        inline get() = Foo() // annotate get property accessors as inline
}

fun example5() {
}

/**
 * Example 6 : Restrictions for public API inline functions
 */
fun example6() {
    // public API : public or protected : can be public API inline functions.
    // non-public-API :private or internal
}