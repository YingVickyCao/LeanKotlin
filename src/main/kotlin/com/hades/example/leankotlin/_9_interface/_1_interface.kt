package com.hades.example.leankotlin._9_interface

// https://kotlinlang.org/docs/interfaces.html
fun main() {
    val example = InterfaceExample()
    example.test()
}

class InterfaceExample {
    fun test() {
        // Example 2 : Implementing a interface
        run {
//            val apple: IFruit = Apple()
//            apple.name()
//            println(apple.price())
//
//            val banana: IFruit = Banana();
//            banana.name()
//            println(banana.price())
        }

        // Example 3 : Properties in interfaces
        run {
//            val child1 = Child()
//            child1.foo()
//            println(child1.prop)
//            println(child1.propertyWithImplementation)
//
//            println()
//
//            val child2 = Child2(29)
//            println(child2.prop)
//            println(child2.propertyWithImplementation)
//            child2.foo()
        }

        run {
//            val employee = Employee("fist", "last", "position 1")
//            println(employee.firstName)
//            println(employee.lastName)
//            println(employee.name)
//            println(employee.position)
        }


        run {
            val c = C();
//            c.bar()
//            c.foo()

            val d = D()

            // Way 1 :
            // foo in D
            // bar in D

            // Way 2 :
            // foo in A
            // foo in B
            // bar in B
            d.foo()
            d.bar()
        }
    }


    // interface vs abstract class
    // interface : not store state  ?

    // Example 1 : Define a interface
    interface IFruit {
        fun name()
        fun price(): Int { // optional body
            return 1
        }
    }


    // Example 2 : Implementing a interface, START
    class Apple : IFruit {
        override fun name() {
            println("Apple")
        }
    }

    class Banana : IFruit {
        override fun name() {
            println("Banana")
        }

        override fun price(): Int {
            return 5
        }
    }
    // Example 2 : Implementing a interface, END

    // Example 3 : Properties in interfaces, START
    // (1) A property declared in an interface can either be abstract or provide implementations for accessors.
    // (2) Properties declared in interfaces cannot have hacking fields.
    interface MyInterface {
        val prop: Int
        val propertyWithImplementation: String
            get() = "foo"

        fun foo() {
            println(prop)
        }
    }


    // Way 1 : must override val defines in interface
    class Child : MyInterface {
        override val prop: Int
            get() = 29
    }

    // Way 2 : must override val defines in interface, START
    class Child2(override val prop: Int) : MyInterface {

    }
    // Example 3 : Properties in interfaces, END

    // Example 4 : Interfaces Inheritance, START
    interface Named {
        val name: String
    }

    interface Person : Named {
        val firstName: String
        val lastName: String
        override val name: String
            get() = "$firstName $lastName"
    }

    // class implementing such as interface are only required to define missing implements
    data class Employee(override val firstName: String, override val lastName: String, val position: String) : Person {
        // implementing name is not required
    }
    // Example 4 : Interfaces Inheritance, END

    // Example 5 : Resolving overriding conflicts, START
    // D class derived from multiple interface, we should specify how the D should implement theme for both methods for which inherited as a single implementation (bar())
    // and to those for which inherited multiple implements(foo())
    interface A {
        fun foo() {
            println("foo in A")
        }

        fun bar()
    }

    interface B {
        fun foo() {
            println("foo in B")
        }

        fun bar() {
            println("bar in B")
        }
    }

    class C : A {
        override fun bar() {
            println("bar in C")
        }
    }

    class D : A, B {
        // Way 1 :
//        override fun foo() {
//            println("foo in D")
//        }
//
//        override fun bar() {
//            println("bar in D")
//        }

        // Way 2 :
        override fun foo() {
            super<A>.foo()
            super<B>.foo()
        }

        override fun bar() {
//            super.bar()
            super<B>.bar()
        }

    }
    // Example 5 : Resolving overriding conflicts, END
}