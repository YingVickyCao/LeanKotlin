package com.hades.example.leankotlin._8_class

// https://kotlinlang.org/docs/data-classes.html
fun main() {
    val example = DataClassExample()
    example.test()
}

private class DataClassExample {
    fun test() {
        // Example 1 : Data classes
        run {
//            val user = User("Black Dog", 1)
//            val user2 = User("Black Dog", 2)
//            val user3 = User("Black Dog", 1)
//            println(user.age)
//            println(user.name)
//
//            // Example 1 : Data classes
//            // The compiler automatically derives the following members from all properties declared in the primary constructor
//            // equals()
//            println(user.equals(user3))                             // true
//            println(user == user3)                                  // true
//            println(user === user3)                                 // falseåååååååååååååå
//            println(user.equals(user2))                             // false
//
//            // hashCode()
//            println(user.hashCode())                                // 295893510
//            println(user3.hashCode())                               // 295893510
//            println(user2.hashCode())                               // 295893511
//            println("Black Dog".hashCode())                         // -960286373
//
//            // toString()
//            println(user.toString())                                // User(name=Black Dog, age=1)
//
//            // componentN()
//            println(user.component1())                              // Black Dog
//            println(user.component2())                              // 1
//
//            // copy() => fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
//            val newUser = user.copy(age = 2)
//            println(newUser.toString())                             // User(name=Black Dog, age=2)
//            val newUser3 = user.copy()
//            println(newUser3.toString())                            // User(name=Black Dog, age=1)
        }


        // Example 2 : Properties declared in the class body
        run {
//            val person1 = Person("Black dog")
//            val person2 = Person("Black dog")
//            person1.age = 10
//            person2.age = 20
//
//
//            println(person1.name)
//            println(person1.age)
//
//            // toString
//            println(person1.toString())             // Person(name=Black dog)
//
//            // equals()
//            println(person1.equals(person2))        // true
//
//            // hashCode()
//            println(person1.hashCode())             // -960255621
//            println(person2.hashCode())             // -960255621
//
//            // toString()
//            val newPerson = person1.copy()
//            println(newPerson.toString())           // Person(name=Black dog)
//            val newPerson2 = person1.copy("White Cat")
//            println(newPerson2.toString())          // Person(name=White Cat)
//
//            // componentN()
//            println(person1.component1())           //  Black dog
        }

        // Example 3 : Copying

        run {
            test_Copying()
        }

        // Example 4 : Data classes and destructuring declarations
        run {
            test_DestructuringDeclarations()
        }

        // Example 5 : Standard data classes
        run {
            test_StandardDataClass()
        }
    }

    // Example 1 : Data classes, START

    // The data class which can generate code, must:
    // The primary constructor needs to have at least one parameter.
    // All primary constructor parameters need to be marked as val or var.
    // Data classes cannot be abstract, open, sealed, or inner.
    // TODO:sealed
//    data class User(val name: String, val age: Int) {
    data class User(val name: String = "", val age: Int = 0) {  // default value for property can be specified in a parameterless constructor
    }
    // Example 1 : Data classes, END


    // Example 2 : Properties declared in the class body, START

    // Only the property name will be used inside the toString(), equals(), hashCode(), and copy() implementations, and there will only be one component function component1().
    // While two Person objects can have different ages, they will be treated as equal.
    data class Person(val name: String) {
        var age: Int = 0
    }
    // Example 2 : Properties declared in the class body, END

    // Example 3 : Copying, START
    fun test_Copying() {
        val dog = User(name = "Dog", age = 1)
        // copy() => fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
        val newDog = dog.copy(age = 3)

        println(dog.toString())         // User(name=Dog, age=1)
        println(newDog.toString())      // User(name=Dog, age=3)

        println(dog.equals(newDog))     // false
        println(dog === newDog)         // false
    }
    // Example 3 : Copying, END

    // Example 4 : Data classes and destructuring declarations, START
    // 解构声明
    fun test_DestructuringDeclarations() {
        val dog = User("Black Dog", 1)
        val (name, age) = dog
        println("$name,$age")
    }
    // Example 4 : Data classes and destructuring declarations, END

    // Example 5 : Standard data classes, START
    fun test_StandardDataClass() {
        // The standard library provides the Pair and Triple classes.
        // data class Pair<out A, out B> : 范型中 <out T> ， 等于 Java中 <? extends T>
//        val pair: Pair<String, String> = Pair("k", "v")
        val pair = Pair("k", "v")
        println(pair.first)
        println(pair.second)

        val triple = Triple(1, 2, 3)
        println(triple.first)
        println(triple.second)
        println(triple.third)

        // named data classes are a better design choice  because more readable.
    }

    // Example 5 : Standard data classes, END
}