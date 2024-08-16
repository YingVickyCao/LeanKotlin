package com.hades.example.leankotlin._4_class_and_object._11_nested_and_inner_classes

import java.awt.SystemColor.window

// Nested and inner classes (嵌套类与内部类)
// https://kotlinlang.org/docs/nested-classes.html
// https://book.kotlincn.net/text/nested-classes.html
fun main() {
    /**
     * Example 1 : Nested and inner classes
     * 嵌套类与内部类
     */
    run {
        val demo = Outer.Nested().foo()
        println(demo)
    }

    /**
     * Example 2 : Inner classes
     * 内部类
     */
    run {
        val demo = Outer2().Inner().foo()
        println(demo)
    }

    /**
     * Example 3 : Anonymous inner classes
     * 匿名内部类
     */
    run {
        // 使用对象表达式来创建匿名内部类实例
        // window.addMouseListener(object : MouseAdapter() {
        //
        //    override fun mouseClicked(e: MouseEvent) { …… }
        //
        //    override fun mouseEntered(e: MouseEvent) { …… }
        //})
    }
}

/**
 * Example 1 : Nested and inner classes
 * 嵌套类与内部类
 */
// 类可以嵌套在其他类中
internal class Outer {
    private val bar: Int = 1

    class Nested {
        fun foo() = 2
    }
}

// 还可以使用带有嵌套的接口。所有类与接口的组合都是可能的：可以将接口嵌套在类中、将类嵌套在接口中、将接口嵌套在接口中。
internal interface OuterInterface {
    class InnerClass
    interface InnerInterface
}

internal class OuterClass {
    class InnerClass
    interface InnerInterface
}

/**
 * Example 2 : Inner classes
 * 内部类
 */

// 标记为inner的嵌套类能够访问它的外部类的成员。内部类会带有一个外部类对象的引用
// Ref : 参见限定的 this 表达式以了解内部类中的 this 的消歧义用法。 https://book.kotlincn.net/text/this-expressions.html
internal class Outer2 {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
    }
}

/**
 * Example 3 : Anonymous inner classes
 * 匿名内部类
 */