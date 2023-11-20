package com.hades.example.leankotlin._4_class_and_object._7_extension

// https://kotlinlang.org/docs/extensions.html
fun main() {
    /**
     * Example 1 : Extension functions
     */
    run {
        //    val list = MutableList<Int>(3) { i -> (i + 1) * 10 }  // 等价
//    val list = MutableList(3) { (it + 1) * 10 }           // 等价
        val list = mutableListOf(10, 20, 30)                    // 等价
        println(list)   // [10, 20, 30]
        list.swap(0, 2) // 'this' inside 'swap()' will hold the value of 'list'
        println(list)   // [30, 20, 10]
    }
    /**
     * Example 2 : Extensions are resolved statically
     */

    run {
        val shape = Shape()
        println(shape.getName())        // share

        val rectangle = Rectangle()
        println(rectangle.getName())    // rectangle

        printClassName(shape)           // share


        val example = Example()
        example.printFunctionType()     // Class method
        example.printFunctionType(5) // Extension method #5
    }
    /**
     * Example 3 : Nullable receiver
     */

    run {
        val str: String? = null
        println(str.toString()) // nul
    }


    /**
     * Example 4 : Extension properties
     */
    run {
        val list = mutableListOf(10, 20, 30)
        println(list.lastIndex) // 2

        val shape = Shape();
        println(shape.lastIndex)    // 5
    }

    /**
     * Example 5 : Companion object extensions
     */

    run {
        println(MyClass.Companion)  // com.hades.example.leankotlin._4_class_and_object._7_extension.MyClass$Companion@1936f0f5
        MyClass.printCompanion()    // Companion
    }

    /**
     * Example 6 : Scope of extensions
     */

    /**
     * Example 7 : Declaring extensions as members
     */

    run {
        val host = Host("myHost")
        host.printHostname()    // myHost
        println()

        val connection = Connection(host, 443)
        connection.connect()    // myHost:443 )
        // TODO: can not invoke connection.getConnectionString()
        // connection.getConnectionString()
        println()


        BaseCaller().call(Base())       // Base extension function  in BaseCaller
        DerivedCaller().call(Base())    // Base extension function  in DerivedCaller
        DerivedCaller().call(Derived()) // Base extension function  in DerivedCaller
    }

    /**
     * Example 8 : Note on visibility
     */
}

// Kotlin provides the ability to extend a class or an interface with new functionality without having to inherit from the class or use design patterns such as Decorator. This is done via special declarations called extensions.

// extension function or extension properties that let you define new properties for existing classes

/**
 * Example 1 : Extension functions
 */

// To declare an extension function, prefix its name with a receiver type, which refers to the type being extended. The following adds a swap function to MutableList<Int>:
// This keyword inside an extension function corresponds to the receiver object (the one that is passed before the dot).
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val temp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = temp
}

/**
 * Example 2 : Extensions are resolved statically
 */
// Extension functions are dispatched statically.
open class Shape
class Rectangle : Shape()

fun Shape.getName() = "share"
fun Rectangle.getName() = "rectangle"
fun printClassName(s: Shape) {
    println(s.getName())
}

// If a class has a member function, and an extension function is defined which has the same receiver type, the same name, and is applicable to given arguments, the member always wins
class Example {
    fun printFunctionType() {
        println("Class method")
    }
}

fun Example.printFunctionType() {
    println("Extension method")
}

// it's perfectly OK for extension functions to overload member functions that have the same name but a different signature
fun Example.printFunctionType(i: Int) {
    println("Extension method #$i")
}


/**
 * Example 3 : Nullable receiver
 */

// Note that extensions can be defined with a nullable receiver type. These extensions can be called on an object variable even if its value is null. If the receiver is null, then this is also null. So when defining an extension with a nullable receiver type, we recommend performing a this == null check inside the function body to avoid compiler errors.

// You can call toString() in Kotlin without checking for null, as the check already happens inside the extension function:
//fun Any?.toString(): String {
//    if (null == this) {
//        return "nul"
//    }
//    return toString()
//}

/**
 * Example 4 : Extension properties
 */

// Kotlin supports extension properties
val <T> List<T>.lastIndex: Int  // 为List扩展出属性lastIndex
    get() = size - 1

val Shape.lastIndex: Int        // 为Shape扩展出属性lastIndex
    get() = 5

/**
 * Example 5 : Companion object extensions
 */

// If a class has a companion object defined, you can also define extension functions and properties for the companion object.
class MyClass {
    companion object {} // will be called "Companion"
}

fun MyClass.Companion.printCompanion() {
    println("Companion")
}

/**
 * Example 6 : Scope of extensions
 */

// In most cases, you define extensions on the top level, directly under packages:
// To use an extension outside its declaring package, import it at the call site:

/**
 * Example 7 : Declaring extensions as members
 */
// You can declare extensions for one class inside another class. Inside such an extension, there are multiple implicit receivers - objects whose members can be accessed without a qualifier. An instance of a class in which the extension is declared is called a dispatch receiver, and an instance of the receiver type of the extension method is called an extension receiver.

class Host(val hostName: String) {
    fun printHostname() {
        print(hostName)
    }
}

class Connection(val host: Host, val port: Int) {
    private fun printPort() {
        print(port)
    }

    private fun Host.printConnectionString() {
        printHostname() // calls Host.printHostname()
        print(":")
        printPort()     // calls Connection.printPort()
    }

    fun connect() {
        host.printConnectionString()
    }

    // In the event of a name conflict between the members  of a dispatch receiver and an extension receiver, the extension receiver takes precedence. To refer to the member of the dispatch
    // receiver, you can use the qualified this syntax.
    fun Host.getConnectionString() {
        toString()         // calls Host.toString()
        this@Connection.toString()  // calls Connection.toString()
    }
}

// Extensions declared as members can be declared as open and overridden in subclasses. This means that the dispatch of such functions is virtual with regard to the dispatch receiver type, but static with regard to the extension receiver type.
open class Base
class Derived : Base()
open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function  in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function  in BaseCaller")
    }

    fun call(base: Base) {
        base.printFunctionInfo()    // call the extension function
    }
}


class DerivedCaller : BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function  in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function  in DerivedCaller")
    }
}
/**
 * Example 8 : Note on visibility
 */
// Extensions utilize the same visibility modifiers as regular functions declared in the same scope would.