package com.hades.example.leankotlin._4_class_and_object._9_sealed_classes_and_interfaces

import java.io.File
import javax.sql.DataSource

// TODO： https://kotlinlang.org/docs/sealed-classes.html#
fun main() {

}

/**
 * Example 1 : Sealed classes and interfaces
 */
// Sealed classes 和 and interfaces 不能被extend
// All direct subclasses of a sealed class are known at compile time. No other subclasses may appear outside the module and package within which the sealed class is defined.
// The same works for sealed interfaces and their implementations: once a module with a sealed interface is compiled, no new implementations can appear.
sealed interface Error
sealed class IOError() : Error {}
class FileReadError(val file: File) : IOError()
class DatabaseError(val source: DataSource) : IOError()
object RuntimeError : Error
class CustomError() : Error

// A sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members.
// Constructors of sealed classes can have one of two visibilities: protected (by default) or private:
sealed class IOError2 {
    constructor() {
        println("IOError2 constructor")
    }

    private constructor(description: String) : this() { // private is ok

    }

//    public constructor(code: Int) : this() { // Error: public and internal are not allowed. error message : "Constructor must be private or protected in sealed class"
//
//    }
}

/**
 * Example 2 : Location of direct subclasses
 *
 */
/**
Inheritance in multiplatform projects
 *
 *
 */


/**
 * Example 3 : Sealed classes and when expression
 */
fun log(e: Error) = when (e) {
    is FileReadError -> {
        println("Error while reading file ${e.file}")
    }

    is CustomError -> {
        println("Custom error")
    }

    is DatabaseError -> println("Error while reading from database  ${e.source}")
    RuntimeError -> println("Runtime error")
}