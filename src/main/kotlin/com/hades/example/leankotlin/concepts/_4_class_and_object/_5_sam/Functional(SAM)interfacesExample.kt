package com.hades.example.leankotlin.concepts._4_class_and_object._5_sam

// https://kotlinlang.org/docs/fun-interfaces.html
fun main() {
    /**
     * Example 1 : SAM conversions
     */

    println("Is 7 event? - ${isEven.accept(7)}") // false
    println("Is 7 event? - ${isEven2.accept(7)}") // false
    println("Is 7 event? - ${isEven3.accept(7)}") // false
    /**
     * Example 2 : Migration from an interface with constructor function to a functional interface
     */

    /**
     * Example 3 : Functional interfaces vs. type aliases
     */
    println("Is 7 event? - ${isEven4(7)}")

}

/**
 * Example 1 : SAM conversions
 */

// An interface with only one abstract method is called a functional interface, or a Single Abstract Method (SAM) interface. The functional interface can have several non-abstract members but only one abstract member.

fun interface kRunable {
    fun invoke()
}
// For functional interfaces, you can use SAM conversions that help make your code more concise and readable by using lambda expressions.
// Instead of creating a class that implements a functional interface manually, you can use a lambda expression. With a SAM conversion, Kotlin can convert any lambda expression whose signature matches the signature of the interface's single method into the code, which dynamically instantiates the interface implementation.

// For example, consider the following Kotlin functional interface:
fun interface IntPredicate {
    fun accept(i: Int): Boolean
}

// If you don't use a SAM conversion, you will need to write code like this:
val isEven = object : IntPredicate { // Creating an instance of a class
    override fun accept(i: Int): Boolean {
        return i % 2 == 0
    }

}

// By leveraging Kotlin's SAM conversion, you can write the following equivalent code instead:
val isEven2 = IntPredicate { i -> i % 2 == 0 }

// 等价于
val isEven3 = IntPredicate { it % 2 == 0 }

/**
 * Example 2 : Migration from an interface with constructor function to a functional interface
 */


interface Printer {
    fun print()
}

// TODO: fun Printer(block: () -> Unit): Printer = object : Printer {
@Deprecated(message = "Your message about the deprecation", level = DeprecationLevel.HIDDEN)

fun Printer(block: () -> Unit): Printer = object : Printer {
    override fun print() = block()
}

// With callable references to functional interface constructors enabled, this code can be replaced with just a functional interface declaration:
fun interface Printer2 {
    fun print()
}

// Its constructor will be created implicitly, and any code using the ::Printer function reference will compile.
fun addPrinter(printer2: Printer2) {

}

/**
 * Example 3 : Functional interfaces vs. type aliases
 */
// You can also simply rewrite the above using a type alias for a functional type
typealias IntPredicate3 = (i: Int) -> Boolean

val isEven4: IntPredicate3 = { it % 2 == 0 }