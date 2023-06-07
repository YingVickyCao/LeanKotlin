package com.hades.example.leankotlin._8_class._5_extension

// https://kotlinlang.org/docs/extensions.html
fun main() {
    var example = ExtensionFunctionExample()
    example.test()
}

public class ExtensionFunctionExample {
    fun test() {

        // Example 1 : Extension function
        run {
            var list = mutableListOf<Int>(1, 2, 3)
            list.swap(1, 2)
            println(list.toString())    // [1, 3, 2]


            list.swap2(1, 2)
            println(list.toString())    // [1, 2, 3]
        }


        // Example 2 : Extensions are resolved statically
        run {
            var shape = Rectangle();
            println(shape.getName())    // Rectangle
            printClassName(shape)       // Shape
            shape.printFunctionType()   // Class method
            shape.printFunctionType(4)   // extension function #4
        }

        // Example 3 : Nullable receiver
        run {
            println(null.toString())    // null

            val str: Any? = "abc"
            println(str.toString())     // abc
        }
    }

    // Example 1 : Extension function, START

    // To declare an extension function, prefix its name with a receiver type, which refers to type being extended
    // Receiver type is the type an extension function
    // MutableList<Int> is the receiver type.

    // Receiver object is passed to a call becomes an implicit this.
    // This inside an extension function corresponds to the receiver object(the one is passed before the dot).
    private fun MutableList<Int>.swap(index1: Int, index2: Int) {
        // MutableList<Int> is the receiver type.
        // MutableList<Int> object is the receiver object, also is an implicit this.
        val temp = this[index1];
        this[index1] = this[index2]
        this[index2] = temp
    }

    private fun <T> MutableList<T>.swap2(index1: Int, index2: Int) { // generics
        val temp = this[index1];
        this[index1] = this[index2]
        this[index2] = temp
    }
    // Example 1 : Extension function, END

    // Example 2 : Extensions are resolved statically, START
    private open class Shape
    private class Rectangle : Shape() {
        fun printFunctionType() { // a member function
            println("Class method")
        }
    }

    private fun Shape.getName(): String {   // an extension function
        return "Shape"
    }

    private fun Rectangle.getName() = "Rectangle"   // an extension function

    // (1) An extension function being called is determined by the type of the expression on which the function is invoked,
    private fun printClassName(s: Shape) {
        println(s.getName())
    }

    // (2) member function > an extension function
    private fun Rectangle.printFunctionType() { // an extension function
        println("extension function")
    }

    // (3) extension functions can overload member functions that have the same name but a different signature
    private fun Rectangle.printFunctionType(value: Int) {
        println("extension function #$value")
    }
    // Example 2 : Extensions are resolved statically, END

    // Example 3 : Nullable receiver, START
    //  Extensions can be defined with a nullable receiver type.
    fun Any?.toString(): String {
        if (this == null) return "null"
        // after the null check, 'this' is autocast to a non-null type, so the toString() below
        // resolves to the member function of the Any class
        return toString()
    }
    // Example 3 : Nullable receiver, END

}