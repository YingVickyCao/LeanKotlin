package com.hades.example.leankotlin._4_class_and_object._2_inheritance

// https://kotlinlang.org/docs/inheritance.html
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

    // Example 5 : Overriding properties
    val shape3 = Shape3()
    println(shape3.vertexCount) // 4
    val rectangle3 = Rectangle3()
    println(rectangle3.vertexCount) // 10

    val rectangle4 = Rectangle4(20)
    println(rectangle4.vertexCount) // 20

    val polygon4 = Polygon4();
    println(polygon4.vertexCount)   // 5
    polygon4.vertexCount = 100
    println(polygon4.vertexCount) // 100

    // Example 6 : Derived class initialization order
//    val base2 = Base2("hi", 5);
//    println(base2.name)
//    println(base2.size)

    val derived3 = Derived3("cat", "red", 20)
    println(derived3.name)
    println(derived3.size)
    println(derived3.lastName)

    // Example 7 : Calling the superclass implementation
    val rectangle2 = Rectangle2()
    println(rectangle2.borderColor)
    rectangle2.draw()

    val filledRectangle = FilledRectangle()
    println(filledRectangle.borderColor)    // black
    println(filledRectangle.fillColor)  // black
    filledRectangle.draw()

    filledRectangle.drawAndFill()

    //  Example 8 : Overriding rules
    val square = Square()
    square.draw()
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


open class Shape3 {
    open val vertexCount: Int = 4
}

class Rectangle3 : Shape3() {
    // 使用 override 来 override superclass class 的 val Properties。
    // 必须是compatible type

    // Way 1 : declared property can be overridden by a property with an initializer
//    override val vertexCount: Int = 10

    // Way 2 : declared property can be overridden by a property with a get method
    override val vertexCount: Int
        get() = 10
}

interface Shape4 {
    val vertexCount: Int
}

// Way 3 : you can use the override keyword as part of the property declaration in a primary constructor
class Rectangle4(override val vertexCount: Int = 4) : Shape4 {

}

// Way 4 : You can also override a val property with a var property, but not vice versa.
// 使用var override superclass class 的 a val property，会增加一个set 方法
class Polygon4 : Shape4 {
    override var vertexCount: Int = 5
}

/**
 * Example 6 : Derived class initialization order
 *
 */

/*
初始化顺序：
Init a base class
Init size in the base class:2
Init a base class part 2

Primary constructor -> {Initializer blocks and Property initializers according to  defined order } -> Secondary constructors
 */
open class Base2(var name: String) { // 1
    init {
        println("Init a base class part 1. name is $name") // 2
    }

    open var size: Int = name.length.also { // 3
        println("Init size in the base class:$it")
    }

    constructor(name: String, size: Int) : this(name) { // 5
        this.size = size
        println("Init the secondary of a base class")
    }

    init { // 4
        println("Init a base class part 2")
    }
}

/*
初始化顺序：
Argument for the base class: Cat
Init a base class part 1. name is Cat
Init size in the base class:3
Init a base class part 2
Init a derived class part 1
Int size in the derived class:6
Init a derived class part 2
Init the secondary of a derived class


base class - Primary constructor
-> base class - {Initializer blocks and Property initializers according to  defined order }
-> base class - Secondary constructors

derived class - Primary constructor
-> derived class - {Initializer blocks and Property initializers according to  defined order }
-> derived class - Secondary constructors

 */
class Derived3(name: String, val lastName: String) : Base2(name.replaceFirstChar { it.uppercase() }.also { println("Argument for the base class: $it") }) {
    constructor(name: String, lastName: String, size: Int) : this(name, lastName) { // 5
        this.size = size
        println("Init the secondary of a derived class")
    }

    init {
        println("Init a derived class part 1")
    }

    override var size: Int = (super.size + lastName.length).also {
        println("Int size in the derived class:$it")
    }

    init {
        println("Init a derived class part 2")
    }
}

/**
 * Example 7 : Calling the superclass implementation
 */

open class Rectangle2 {

    open fun draw() {
        println("draw in Rectangle")
    }

    val borderColor: String get() = "black"
//    val borderColor: String
//        get() {
//            return "black"
//        }
}

class FilledRectangle : Rectangle2() {
    override fun draw() {
        //  使用super 调用super class的方法
        super.draw()
        println("draw in FilledRectangle")
    }

    //  使用super 调用super class的field
    val fillColor: String get() = super.borderColor

    fun drawAndFill() {
        val filler = Filler()
        filler.drawAndFill()
    }

    inner class Filler {
        private fun fill() {
            println("fill")
        }

        fun drawAndFill() {
            // 调用Rectangle2的draw()方法
            super@FilledRectangle.draw()
            fill()
            // 调用外部类的superclass implementation ? `super@Outer`
            // Uses Rectangle2's implementation of borderColor's get(
            println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}")
        }
    }
}


/**
 * Example 8 : Overriding rules
 *
 */

open class Rectangle5 {
    open fun draw() {
        println("Rectangle5 - draw")
    }
}

interface Polygon {
    fun draw() { // interface members are 'open' by default
        println("Polygon - draw")
    }
}

class Square() : Rectangle5(), Polygon {
    override fun draw() {
        // 当 class 继承了多个实现时，用super<Base>指定 supertype name
        super<Rectangle5>.draw()
        super<Polygon>.draw()
    }
}