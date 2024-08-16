package com.hades.example.leankotlin.concepts._4_class_and_object._1_class

fun main() {
    val rect: Rectangle = Rectangle()
    rect.draw()     // Rectangle invoked

    val shape: WildShape = ThirdShape()
    shape.draw()    // ThirdShape draw()
}

abstract class Polygon {
    abstract fun draw()
}

class Rectangle : Polygon() {
    override fun draw() {
        println("Rectangle invoked")
    }
}


// You can override a non-abstract open member with an abstract one.
//  open关键字 表示这个class 或 functions 可以被override
open class Shape {
    open fun draw() {
        println("draw")
    }
}

abstract class WildShape : Shape() {
    abstract override fun draw();
}

class ThirdShape : WildShape() {
    override fun draw() {
        println("ThirdShape draw()")
    }

}