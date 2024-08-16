package com.hades.example.leankotlin.concepts._4_class_and_object._8_data_classes

// https://kotlinlang.org/docs/data-classes.html
fun main() {

    /**
     * Example 1 :Data classes 自带func
     */
    run {
        val user1 = User("my name", 10)
        val user2 = User("my name", 10)
        val user3 = User("hi name", 10)

        // The compiler automatically derives the following members from all properties declared in the primary constructor:

        // .equals()/.hashCode() pair
        println(user1.equals(user2))    // true     // .equals() 判断data 是否相同
        println(user3.equals(user2))    // false
        println(user1 == user2)          // true     // data class 的equals() 与 == 功能等价
        println(user3 == user2)         // false
        println(user1.hashCode())   // 2022246251
        println(user2.hashCode())   // 2022246251
        println(user3.hashCode())   // 582972160

        // .toString() of the form "User(name=John, age=42)"
        println(user1.toString())   // User(name=my name, age=10)
        println(user2.toString())   // User(name=my name, age=10)
        println(user3.toString())   // User(name=hi name, age=10)

        // .componentN() functions corresponding to the properties in their order of declaration.
        println(user1.component1()) // my name
        println(user1.component2()) // 10

        // .copy() function (see below).

        // To ensure consistency and meaningful behavior of the generated code, data classes have to fulfill the following requirements:
        //The primary constructor needs to have at least one parameter.
        //All primary constructor parameters need to be marked as val or var.
        //Data classes cannot be abstract, open, sealed, or inner.

        // If there are explicit implementations of .equals(), .hashCode(), or .toString() in the data class body or final implementations in a superclass, then these functions are not generated, and the existing implementations are used.
        //If a supertype has .componentN() functions that are open and return compatible types, the corresponding functions are generated for the data class and override those of the supertype. If the functions of the supertype cannot be overridden due to incompatible signatures or due to their being final, an error is reported.
        //Providing explicit implementations for the .componentN() and .copy() functions is not allowed.
        val user4 = User2("my name", 10)
        println(user4.hashCode())   // 100

        // TODO:Data classes may extend other classes (see Sealed classes for examples).
    }

    /**
     * Example 2 : Properties declared in the class body
     */
    run {
        val person1 = Person("hi")
        person1.age = 5
        val person2 = Person("hi")
        person2.age = 10
        println(person1.equals(person2))    // true
        println(person1 == person2) // true
        println(person1.hashCode()) // 3329
        println(person2.hashCode()) // 3329
        println(person1.toString()) // Person(name=hi)
        println(person2.toString()) // Person(name=hi)
        println(person1.component1())   // hi
        println(person2.component1())   // hi
    }

    /**
     * Example 3 : Copying
     */
    // Use the .copy() function to copy an object, allowing you to alter some of its properties while keeping the rest unchanged. T
    run {
        val user = User("my name", 10)
        val user2 = user.copy()
        println(user2.toString())   // User(name=my name, age=10)
        val user3 = user.copy("custom name")    // copy时允许做一些更改. 因为默认实现为 fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
        println(user3.toString())   // User(name=custom name, age=10)
    }


    /**
     * Example 4 : Data classes and destructuring declarations
     */
    // Component functions generated for data classes make it possible to use them in destructuring declarations:
    run {
        val jane = User("jane", 5)
        val (name, age) = jane
        println("$name, $age years of age") // jane, 5 years of age
    }

    /**
     * Example 5 : Standard data classes
     */
    // The standard library provides the Pair and Triple classes.
    run {
        val pair = Pair(1, 2)
        val pair2 = Pair("Jane", 2)
        println(pair.first)     // 1
        println(pair.second)    // 2

        println(pair2.first)    // Jane
        println(pair2.second)   // 2

        val triple = Triple("Jane", 15, 2023)
        println(triple.first)   // Jane
        println(triple.second)  // 15
        println(triple.third)   // 2023
    }
}

/**
 * Example 1 :Data classes 自带func
 */
internal data class User(val name: String, val age: Int)

internal data class User2(val name: String, val age: Int) {
    override fun hashCode(): Int {
        return 100
    }
}

// On the JVM, if the generated class needs to have a parameterless constructor, default values for the properties have to be specified (see Constructors).
internal data class User3(val name: String = "", val age: Int = 0)

/**
 * Example 2 : Properties declared in the class body
 */
// The compiler only uses the properties defined inside the primary constructor for the automatically generated functions. To exclude a property from the generated implementations, declare it inside the class body:
internal data class Person(val name: String) {
    var age: Int = 0
}

/**
 * Example 3 : Copying
 */
// Use the .copy() function to copy an object, allowing you to alter some of its properties while keeping the rest unchanged. The implementation of this function for the User class above would be as follows:


/**
 * Example 4 : Data classes and destructuring declarations
 */

/**
 * Example 5 : Standard data classes
 */


