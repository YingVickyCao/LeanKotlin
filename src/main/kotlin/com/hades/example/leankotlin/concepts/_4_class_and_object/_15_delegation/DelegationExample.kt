package com.hades.example.leankotlin.concepts._4_class_and_object._15_delegation
// 委托
// https://book.kotlincn.net/text/delegation.html
// https://kotlinlang.org/docs/delegation.html

fun main() {
    test1()
    test2()
}


/**
 * Example 1 : Delegation
 */
// 委托模式可以替代继承

interface Base {
    fun print()
    fun printLine()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }

    override fun printLine() {
        println(x)
    }
}

// Derived 类可以通过将其所有公有成员都委托给指定对象来实现一个接口 Base
// The by-clause in the supertype list for Derived indicates that b will be stored internally in objects of Derived and the compiler will generate all the methods of Base that forward to b.
// b是委托对象，将b委托给了Derived
class Derived(b: Base) : Base by b

fun test1() {
    val b = BaseImpl(10)
    Derived(b).printLine() // 10
}

/**
 * Example 2 : Overriding a member of an interface implemented by delegation
 * 覆盖由委托实现的接口成员
 */

// b是委托对象，将b委托给了Derived2
class Derived2(b: Base) : Base by b {
    // 调用Derived2 的 printLine时，compiler使用的是用override 实现，而不是委托对象中的
    // 以这种方式重写的成员不会在委托对象的成员中调用 ，委托对象的成员只能访问其自身对接口成员实现
    override fun printLine() {
        println("abc")
    }
}

fun test2() {
    val b = BaseImpl(10)
    b.printLine()           // 10
    Derived2(b).printLine()  // abc
}