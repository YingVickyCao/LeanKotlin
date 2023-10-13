package com.hades.example.leankotlin._4_class_and_object

/**
 * https://kotlinlang.org/docs/inheritance.html#overriding-properties
 */
fun main() {
    // Example 1 : Any is the default supertypes for a class with no supertypes declared
    run {
        var e: Example = Example()
        var e2: Example2 = Example2()
    }

    // Example 2 : Use open to let class can be inherited
    run {
        val sub = Sub();
        println(sub.javaClass.simpleName) // Sub
    }


    // Example 3: Derived class can declare an explicit supertype, and init the base class
    run {
        var e3: Base = Derived(10)
        var e4: Base = Derived2(10, "Li Ming")
    }

    // Example 4 : Overriding methods
    run {
        val shape = Shape()
        shape.draw()        // Shape-draw

        val shape2 = Circle();
        shape2.draw()       // Circle-draw
    }

    // Example 6 : Derived class initialization order
    run {
        val base = Base2("first");
        println(base.name)
        println(base.size)

        val derived = Derived3("first", "second")
        println(derived.name)
        println(derived.lastName)
        println(derived.size)
    }

    // // Example 7 : Calling superclass implementation
    run {
        val rect = Rectangle()
        rect.draw()
        println(rect.borderColor)

        println()
        val filledRectangle = FilledRectangle()
        filledRectangle.draw()
    }

    // Example 8 : Overriding rules
    run {
        val rect = Rectangle2();
        rect.draw()
        rect.start()
        println()

        val square = Square();
        square.draw()
        square.start()
    }
}

// Example 1 : Any is the default supertypes for a class with no supertypes declared
class Example { // Implicitly inherits from Any
// Example is final
}

// Example 2 : By default, Kotlin classes are final - can not be inherited.
// To make a class inherited, use open keyword.
open class Example2 { // class is open for inheritance

}

class Sub : Example2() {

}

// Example 3: Derived class can declare an explicit supertype, and init the base class
open class Base(n: Int) {

}

// Way 1 : Derived class has a primary constructor, so the base class must be inited in that primary constructor according to it's parameters
class Derived(n: Int) : Base(n) {

}

// Way 2 : If Derived class has no primary constructor, then each secondary constructor has to init the base type using `super` keyword or it has delegate to another constructor which does.
class Derived2 : Base {
    constructor(n: Int) : super(n)
    constructor(n: Int, name: String) : super(n)
}

// Example 4 : Overriding methods
open class Shape {
    open fun draw() {
        println("Shape-draw")
    }

    fun fill() {
        println("Shape-fill")
    }
}

class Circle() : Shape() {
    override fun draw() {
        println("Circle-draw")
    }
}

// Example 6 : Derived class initialization order, START
//
// First, Base class - Constructor -> init blocks -> property initializer
// Then,  Derived class - Constructor -> init blocks -> property initializer
open class Base2(val name: String) {
    init {
        println("Initializing a base class ")
    }

    open val size: Int = name.length.also { println("Initializing size in the base class : $it") }
}

class Derived3(name: String, val lastName: String) : Base2(name.replaceFirstChar { it.uppercase() }.also { println("Argument for the base class:$it") }) {
    init {
        println("Initializing a derived class ")
    }

    //  $it : it 是唯一参数（argument）s
    override val size: Int = (super.size + lastName.length).also { println("Initializing size in the derived class:$it") }
}
// Example 6 : Derived class initialization order, END

// Example 7 : Calling superclass implementation, START
open class Rectangle {
    open fun draw() {
        println("draw() a rectangle")
    }

    val borderColor: String get() = "black"
}

class FilledRectangle : Rectangle() {
//    override fun draw() {
//        super.draw()
//        println("Filling the rectangle")
//    }

    override fun draw() {
        val filler = Filter()
        filler.drawAndFill()
    }

    val fillColor: String get() = super.borderColor

    inner class Filter {
        fun fill() {
            println("Filling")
        }

        fun drawAndFill() {
            super@FilledRectangle.draw() // Invoke Rectangle's  implement of draw()
            fill()
            println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}") // Invoke Rectangle's  implement of borderColor's get()
        }
    }
}
// Example 7 : Calling superclass implementation, END

// Example 8 : Overriding rules, START
open class Rectangle2 {
    open fun draw() {
        println("Parent --> draw()")
    }

    open fun start() {
        println("Parent -> start()")
    }
}

interface Polygon4 {
    fun draw() {
        println("Interface -> draw()")
    }

    fun start()
}

class Square : Rectangle2(), Polygon4 {
    // Since both base class and interface implement draw(),so when derived class overrides draw, we can choose to implement whose draw
    override fun draw() {
        super<Rectangle2>.draw()    // Call Rectangle2's draw()
        super<Polygon4>.draw()      // Call Polygon4's draw()
        println("Child -> draw()")
    }

    override fun start() {
        super.start()
        println("Child -> start()")
    }
}
// Example 8 : Overriding rules, END