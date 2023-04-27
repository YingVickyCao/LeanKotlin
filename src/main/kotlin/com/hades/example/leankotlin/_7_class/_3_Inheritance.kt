package com.hades.example.leankotlin._7_class

fun main() {
    run {
        var e: Example = Example()
        var e2: Example2 = Example2()
        var e3: Base = Derived(10)
        var e4: Base = Derived2(10, "Li Ming")
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