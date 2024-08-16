package com.hades.example.leankotlin.concepts._4_class_and_object._1_class

/**
 * primary constructor
 * property initializers
 * initializer blocks
 */
fun main() {
    val person: Person = Person("斯")
    println(person.firstName)
}

// If the primary constructor does not have any annotations or visibility modifiers, the constructor keyword can be omitted:
// primary constructor 中可以设置默认值。 e,g., isChinese
class Person constructor(var firstName: String, val isChinese: Boolean = false) {
    // 同
    // public class Person (var firstName: String) {

    var secondaryName: String = "李"

    init {
        // Primary constructor parameters can be used in the initializer blocks
        println("init block[1] : firstName is $firstName, secondaryName is $secondaryName")    // init block : firstName is 斯, secondaryName is 李
    }

    var hobby: String = "Jump";

    init {
        println("init block[2] : firstName is $firstName, secondaryName is $secondaryName, hobby is $hobby")   // init block : firstName is 斯, secondaryName is 李, hobby is Jump
    }

    // Primary constructor parameters can be used in property initializers declared in the class body
    var customerKey: String = firstName + " a b c"
}
