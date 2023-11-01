package com.hades.example.leankotlin._4_class_and_object._2_inheritance

fun main() {
    // Example 1 : Implicitly inherits from Any
    val example = Example();
    example.hashCode()
    example.toString()
    example.equals("ac");

    // Example 2 : Class is open for inheritance
    // Example 3 : How derived class to initialize the base type
    val derived = Derived(5);
    println(derived.p)  // 5

    val derived2 = Derived2(10)
    println(derived2.p) // 10

    val derived2_2 = Derived2(10, "China")
    println(derived2_2.p) // 10
    println(derived2_2.name)

    // Example 4 : Overriding methods
    val circle = Circle();
    circle.draw()
    circle.fill()
}

/**
 * Example 1 : Implicitly inherits from Any
 *
 * Any -
 * hashCode()
 * toString()
 * equals()
 */
// Example 没有添加open 关键字，因此不能被继承
class Example {
}

/**
 * Example 2 : Class is open for inheritance
 */
open class Base(val p: Int) {

}


/**
 * Example 3 : How derived class to initialize the base type
 */
// If the derived class has a primary constructor, the base class can (and must) be initialized in that primary constructor according to its parameters
class Derived(p: Int) : Base(p) {

}

// If the derived class has no primary constructor, then each secondary constructor has to initialize the base type using the super keyword,
// or it has to delegate to another constructor which does.
class Derived2 : Base {
    var name: String = ""

    constructor(p: Int) : super(p)  // If derived class has no primary constructor, 那么 base class 必须被初始化 p
    constructor(p: Int, name: String) : super(p) {
        this.name = name
    }
}

/**
 * Example 4 : Overriding methods
 */

open class Shape {
    open fun draw() {
        println("Shape - draw")
    }

    fun fill() {
        println("Shape - fill")
    }
}

class Circle() : Shape() {
    // 因为Shape 用 open 修饰了 draw，Circle 才能用 override （重写）draw
    override fun draw() {
        println("Circle - draw")
    }
}

final class Shape2 {
    open fun draw() {
        println("Shape - draw")
    }

    fun fill() {
        println("Shape - fill")
    }
}

// final class 不可以被继承。
// open 修饰符对final class 无效
// Compile Error:  This type is final, so it cannot be inherited from
//class Circle2() : Shape2() {
//
//}

open class Rectangle() : Shape() {
    // 用final修饰fun可以防止被重写
    final override fun draw() {
        println("Rectangle - draw")
    }
}

class RedRectangle() : Rectangle() {
    // 不能Rectangle 的draw，因为Rectangle用 final 禁止 re-overriding
}

/**
 * Example 5 : Overriding properties
 */