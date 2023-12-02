package com.hades.example.leankotlin._4_class_and_object._13_inline_value_classes

fun main() {
    /**
     * Example 1 : Inline value classes
     * 内联类
     */
    run {
        test1()
    }

    /**
     * Example 2 : Members
     */

    run {
        test2()
    }
    /**
     * Example 3 : Inheritance
     */
    run {
        test3()
    }

    /**
     * Example 4 : Representation
     */

    /**
     * Example 4 : Representation - Mangling
     */

    /**
     * Example 4 : Representation - Calling from Java code
     */

    /**
     * Example 5 : Inline classes vs type aliases
     */

    /**
     * Example 6 : Inline classes and delegation
     */
}

/**
 * Example 1 : Inline value classes
 * 内联类
 */
// value 和 @JvmInline 来表示标识一个内联类
@JvmInline
internal value class Password(private val s: String)

//  内联类必须含有唯一的一个属性在主构造器中初始化。在运行时，将使用这个唯一属性来表示内联类的实例。

fun test1() {
    // 内联类的使用场景：wrap a value in a class to create a more domain-specific type. 若被包装的是原生类型，那么同时又不会丧失运行时对原始类型的优化因此不会有性能损失。
    // 在运行时，不存在Password类的真实实例对象，securePassword仅仅包含 string
    val securePassword = Password("Don't try this in production")
    println(securePassword)
    // 内联类的主要特性：类的数据被内联到该类使用的地方。类似于内联函数中的代码被内联到该函数调用的地方。
}

/**
 * Example 2 : Members
 */

fun test2() {

}


/**
 * Example 3 : Inheritance
 */
fun test3() {

}

/**
 * Example 4 : Representation
 */

/**
 * Example 4 : Representation - Mangling
 */

/**
 * Example 4 : Representation - Calling from Java code
 */

/**
 * Example 5 : Inline classes vs type aliases
 */

/**
 * Example 6 : Inline classes and delegation
 */

