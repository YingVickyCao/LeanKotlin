package com.hades.example.leankotlin._4_class_and_object._14_object_expressions_and_declarations

// object expressions and object declarations.
// 对象表达式与对象声明 : 需要创建一个对某个类做了轻微改动的类的对象，而不用为之显式声明新的子类
// https://book.kotlincn.net/text/object-declarations.html
// https://kotlinlang.org/docs/object-declarations.html

fun main() {
    test1_1()
    test1_2()
    test1_3()
    test1_4()
}

/**
 * Example 1 : Object expressions
 */
// Object expressions create objects of anonymous classes,Such classes are useful for one-time use.
// Instances of anonymous classes are also called anonymous objects

/**
 * Example 1 : Object expressions - Creating anonymous objects from scratch
 */

internal fun test1_1() {
    val helloWorld = object {
        val hello = "hello"
        val world = "World"
        override fun toString(): String {
            return "$hello $world"
        }
    }
    println(helloWorld) // hello World
}

/**
 * Example 1 : Object expressions - Inheriting anonymous objects from supertypes
 *  创建一个继承自某个（或某些）类型的匿名类的对象
 */

//lambda
//button.setOnClickListener(v -> {
//do some thing
//});
//匿名内部类
//button.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        //do some thing
//    }
//});

// 超类型有构造函数，可以传递适当的构造函数给它。多个超类型用;分割
open class A(x: Int) {
    public open val y: Int = x
}

interface B {
    fun sayHi()
}

fun test1_2() {
    val ab: A = object : A(1), B {
        override val y: Int
            get() = 15

        override fun sayHi() {
            println("Hi, I am A object")
        }

    }
    println(ab.y)
    // ab.sayHi() // error:
}

/**
 * Example 1 : Object expressions - Using anonymous objects as return and value types
 */

// When an anonymous object is used as a type of a local or private but not inline declaration (function or property),
// all its members are accessible via this function or property:

class C {
    private fun getObject() = object {
        val x: String = "X"
    }

    fun printX() {
        println(getObject().x)
    }
}

// If this function or property is public or private inline, its actual type is:
//  (1)Any if the anonymous object doesn't have a declared supertype
//  (1)The declared supertype of the anonymous object, if there is exactly one such type
//  (3)The explicitly declared type if there is more than one declared supertype


interface A2 {
    fun funFromA() {

    }
}

interface B2 {

}

class C2 {
    // return type is Any，x 不能访问
    fun getObject() = object {
        val x: String = "x"
    }

    // return type is A，x 不能访问
    fun getObjectA() = object : A2 {
        override fun funFromA() {
        }

        val x: String = "x"
    }

    // return type is B，x和funFromA 不能访问
    fun getObjectB(): B2 = object : A2, B2 {
        override fun funFromA() {

        }

        val x: String = "x"
    }
}


fun test1_3() {
    val c = C()
    c.printX()


    val c2 = C2()
    // c2.getObject().x // error：x 不能访问

    c2.getObjectA().funFromA() // ok
    // c2.getObjectA().x() // error：x 不能访问

    // c2.getObjectB().funFromA() // error：funFromA 不能访问
    // c2.getObjectB().x   // error：x 不能访问
}

/**
 * Example 1 : Object expressions - Accessing variables from anonymous objects
 */
// 对象表达式中的代码可以访问来自包含它的作用域的变量：

fun countClicks(window: Window) {
    var clickCount = 0
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked() {
            clickCount++
        }
    })
}

open class MouseAdapter {
    open fun mouseClicked() {

    }
}

class Window {
    lateinit var mouseAdapter: MouseAdapter

    fun addMouseListener(mouseAdapter: MouseAdapter) {
        this.mouseAdapter = mouseAdapter
    }
}

fun test1_4() {
    countClicks(Window())
}