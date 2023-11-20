package com.hades.example.leankotlin._4_class_and_object._4_interface

fun main() {
    /**
     * Example 1 : Implementing interfaces
     */
    val child = Child()
    child.bar()


    /**
     * Example 2 : Properties in interfaces
     */
    val child2 = Child2()
    child2.foo()

    /**
     * Example 3 : Interfaces Inheritance
     */
    val employee = Employee("fist", "last")
    println(employee.firstName)
    println(employee.lastName)
    println(employee.name)

    /**
     * Example 4 : Resolving overriding conflicts
     */
    val c = C()
    c.foo()
    c.bar()
    println()

    val d = D()
    d.foo()
    println()
    d.bar()

}

/**
 * Example 1 : Implementing interfaces
 */


interface MyInterface {
    fun bar()
    fun foo()
}

// A class or object can implement one or more interfaces:
class Child : MyInterface {
    override fun bar() {
        println("bar")
    }

    override fun foo() {
        println("foo")
    }
}

/**
 * Example 2 : Properties in interfaces
 */

interface MyInterface2 {
    // property declared in an interface is abstract
    val prop: Int

    // property declared in an interface provides implementations for accessors
    val properWithImplementation: String
        get() = "foo"

    fun foo() {
        println(prop)
    }
}

class Child2 : MyInterface2 {
    override val prop: Int = 29
}

/**
 * Example 3 : Interfaces Inheritance
 */

interface Named {
    val name: String
}

// An interface can derive from other interfaces, meaning it can both provide implementations for their members and declare new functions and properties.
interface Person : Named {
    val firstName: String
    val lastName: String
    override val name: String
        get() = "$firstName $lastName"
}

// Quite naturally, classes implementing such an interface are only required to define the missing implementations:
// implementing 'name' is not required
data class Employee(override val firstName: String, override val lastName: String) : Person {

}

/**
 * Example 4 : Resolving overriding conflicts
 */
// When you declare many types in your supertype list, you may inherit more than one implementation of the same method:
interface A {
    fun foo() {
        println("A - foo")
    }

    fun bar()
}

interface B {
    fun foo() {
        println("B - foo")
    }

    fun bar() {
        println("B - bar")
    }
}

class C : A {
    override fun bar() {
        println("C - bar")
    }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super.bar() // super.bar() is bar of b
//        super<B>.bar() // 可以省略<B>
    }
}