package com.hades.example.leankotlin._5_functions._3_inline_functions

import com.hades.example.leankotlin._4_class_and_object._13_inline_value_classes.asNullable

// 内联函数
// https://book.kotlincn.net/text/inline-functions.html
// https://kotlinlang.org/docs/inline-functions.html


fun main() {
    example1()
    example2()
    example3()
    example4()
    example5()
    example6()
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

inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {

}

//fun multiply(inlined: (Int, Int) -> Int):Int{
//
//}

fun example2() {
    // 12
    val num1 = 2;
    val num2 = 3
//     multiply({ num1, num2 ->
//        println("start,$num1,$num2")
//        num1 + num2
//    })
}

/**
 * Example 3 : Non-local returns
 */
fun example3() {

}

/**
 * Example 4 : Reified type parameters
 */
fun example4() {

}

/**
 * Example 5 : Inline properties
 */
fun example5() {

}

/**
 * Example 6 : Restrictions for public API inline functions
 */
fun example6() {

}