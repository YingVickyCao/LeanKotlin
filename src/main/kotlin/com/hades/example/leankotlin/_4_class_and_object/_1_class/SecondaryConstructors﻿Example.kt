package com.hades.example.leankotlin._4_class_and_object._1_class

fun main() {
//    val p: Person2 = Person2("张三")
//    println(p.name)

    val p2: Person2 = Person2("李四", 12)
//    println(p2.name)
//    println(p2.age)

    val cat: Cat = Cat()


    // Compiler Error : Cannot access '<init>': it is private in 'DontCreateMe'
//    val dontCreateMe: DontCreateMe = DontCreateMe()
}


// each secondary constructor needs to delegate to the primary constructor,
class Person2(val name: String) {
    var age: Int = 0

    constructor(name: String, age: Int) : this(name) {
        println("secondary constructor")
        this.age = age
    }

    init {
        println("Init block")
    }
}


// If a non-abstract class does not declare any constructors (primary or secondary), it will have a generated primary constructor with no arguments. The visibility of the constructor will be public.
class Cat {

}


// If you don't want your class to have a public constructor, declare an empty primary constructor with non-default visibility:
class DontCreateMe private constructor() {

}