package com.hades.example.leankotlin._4_class_and_object

/**
 * Abstract classes
 * https://kotlinlang.org/docs/classes.html#abstract-classes
 */
fun main() {
    run {
        var p: Polygon = Rect()
        p.draw()    // Draw React

        p.hi()  // Hi React
    }

    run {
        var p: Polygon2 = Polygon2()
        p.draw() // draw Polygon2

        val p2: Polygon2 = BigSpace()
        p2.draw() // draw BigSpace
    }

}

abstract class Polygon {
    abstract fun draw();

    open fun hi() {
        println("Hi Polygon")
    }
}

class Rect : Polygon() {
    override fun draw() {
        println("Draw React")
    }

    override fun hi() {
        println("Hi React")
    }
}


open class Polygon2 {
    open fun draw() {
        println("draw Polygon2")
    }
}

abstract class WildSpace : Polygon2() { // You can override a non-abstract open member with an abstract one
    abstract override fun draw()
}

class BigSpace : WildSpace() {
    override fun draw() {
        println("draw BigSpace")
    }

}