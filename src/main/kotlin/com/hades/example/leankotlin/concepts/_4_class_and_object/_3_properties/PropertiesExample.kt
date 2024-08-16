package com.hades.example.leankotlin.concepts._4_class_and_object._3_properties

import java.lang.AssertionError
import javax.inject.Inject

// https://kotlinlang.org/docs/properties.html


fun main() {

    /**
     * Example 1 : Declaring properties
     */

    val address = Address()
    println(address.name)
    address.name = "Sherlock"
    println(address.state)
    val newAddress = copyAddress(address)
    println(newAddress.name)

    /**
     * Example 2 : Getters and setters
     */
    val rectangle = Rectangle(5, 10, 20)
    println(rectangle.initialized)
    rectangle.initialized = 2
    println(rectangle.initialized)

    println(rectangle.simple)

    println(rectangle.inferredType)
    println(rectangle.area)

    rectangle.stringRepresentation = "abc"
    println(rectangle.stringRepresentation)
    println(rectangle.initialized)

    println(rectangle.setterVisibility)
    println(rectangle.settingAnnotation)

    println(rectangle.counter)
    rectangle.counter = 5
    println(rectangle.counter)
    println(rectangle.isEmpty)

    println(rectangle.table)

    /**
     * Example 3 : Compile-time constants
     */
    println(MyObject.COUNT)
    println(Sample.NUM)

    /**
     * Example 4 : Late-initialized properties and variables
     */

    val myTest = MyTest()
    myTest.print()
    myTest.subject = "hello world"
    println(myTest.subject)
    myTest.print()

    /**
     * Example 5 : Overriding properties
     * see interface - Overriding methods
     * https://kotlinlang.org/docs/inheritance.html#overriding-properties
     */

    /**
     * Example 6 : Delegated properties
     * see Delegated properties
     * https://kotlinlang.org/docs/delegated-properties.html
     *
     */
}

/**
 * Example 1 : Declaring properties
 */
class Address {
    var name: String = "Holmes"
    var state: String? = null
}

fun copyAddress(address: Address): Address {
    val result = Address()
    result.name = address.name
    result.state = address.state
    return result
}

/**
 * Example 2 : Getters and setters
 */

class Rectangle(val simple: Int?, private val width: Int, private val height: Int) { // read-only property : simple has type Int, default getter, must be initialized
    // mutable property : initialized has type int, default getter and setter
    var initialized = 1

    // read-only property : inferredType has type Int, default getter
    val inferredType = 1

    // define custom accessors for a property
    val area: Int
        get() = width * height

    // omit the property type if it can be inferred from the getter
    // val area  get() = width * height

    // define a custom setter, it will be called every time you assign a value to the property, except its initialization.
    var stringRepresentation: String
        get() = this.toString()
        set(value) {// parses the string and assigns values to other properties
            setDataFromString(value)
        }

    private fun setDataFromString(value: String) {
        initialized = value.length
    }

    // If you need to annotate an accessor or change its visibility, but you don't want to change the default implementation, you can define the accessor without defining its body:
    var setterVisibility: String = "efg"
        private set // the setter is private and has the default implementation
    var settingAnnotation: Any? = null
        @Inject set // annotate the setter with Inject

    /**
     * Backing fields
     */
    // In Kotlin, a field is only used as a part of a property to hold its value in memory. Fields cannot be declared directly. However, when a property needs a backing field, Kotlin provides it automatically. This backing field can be referenced in the accessors using the field identifier:
    // The field identifier can only be used in the accessors of the property.
    var counter = 0
        set(value) {
            if (value >= 0) {
                field = value
            }
        }

    // A backing field will be generated for a property if it uses the default implementation of at least one of the accessors, or if a custom accessor references it through the field identifier.
    // For example, there would be no backing field in the following case:
    var size = 5
    val isEmpty: Boolean
        get() = size == 0

    /**
     * Backing properties
     */
    // If you want to do something that does not fit into this implicit backing field scheme, you can always fall back to having a backing property:
    private var _table: Map<String, Int>? = null
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap();
            }
            // if table != null ? return table , or throw AssertionError
            return _table ?: throw AssertionError("Set to null by another thread")
        }
    // On the JVM: Access to private properties with default getters and setters is optimized to avoid function call overhead.
}


/**
 * Example 3 : Compile-time constants
 */
// If the value of a read-only property is known at compile time, mark it as a compile time constant using the const modifier.

// It must be a top-level property, or a member of an object declaration or a companion object.
//It must be initialized with a value of type String or a primitive type
//It cannot be a custom getter

// way 1 : mak a top-level property as const
// top-level property : 把属性的声明不写在 class 里面
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

// top-level function : 把函数的声明不写在 class 里面
// Such properties can also be used in annotations:
@Deprecated(SUBSYSTEM_DEPRECATED)
fun foo() {

}

// way 2 : mak a member of an object declaration
object MyObject {
    const val COUNT = 5
}

// way 3 : mak a member of companion object as const
//
class Sample {
    companion object {
        const val NUM = 1
    }
}

/**
 * Example 4 : Late-initialized properties and variables
 */
class MyTest {
    // 普通 var 要被定义一个non-nullable type。
    // 但是可以用 lateinit 来延迟初始化。
    // 但是无论如何，在使用之前必须初始化，否则会运行时报错。
    lateinit var subject: String

    /**
     * Checking whether a lateinit var is initialized
     */
    // https://kotlinlang.org/docs/reflection.html#property-references
    fun print() {
        if (::subject.isInitialized) {
            println("subject is $subject")
        } else {
            println("subject is not init yet.")
        }
    }
}


/**
 * Example 5 : Overriding properties
 * see interface - Overriding methods
 * https://kotlinlang.org/docs/inheritance.html#overriding-properties
 */


/**
 * Example 6 : Delegated properties
 * see Delegated properties
 * https://kotlinlang.org/docs/delegated-properties.html
 *
 */