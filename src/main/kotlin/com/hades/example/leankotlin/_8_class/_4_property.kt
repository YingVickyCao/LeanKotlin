package com.hades.example.leankotlin._8_class

import java.lang.AssertionError
import javax.inject.Inject

/**
 * https://kotlinlang.org/docs/properties.html
 */
fun main() {
    PropertyExample().test()
}

class PropertyExample {
    fun test() {

        // Example 1 : Declaring properties
        run {
            val address = Address();
            val result = copyAddress(address)
            println(result.name)    // Helmes
        }

        // Example 2 : Getters and setters
        run {
            val rectangle = Rectangle(10, 5, 6);
            println(rectangle.count)  // 1
            rectangle.count = 10;
            println(rectangle.count)  // 10

            println(rectangle.type)   // 1
//            stu.type = 14;  // Val cannot be reassigned

            println(rectangle.simple)  // 10

            println(rectangle.area)    // 30

            rectangle.representation = "Sunny"
            println(rectangle.representation)

//            rectangle.setterVisibility = "new visibility" // Error: Cannot assign to 'setterVisibility': the setter is private in 'Rectangle'
            println(rectangle.setterVisibility)

            rectangle.setterWithAnnotation = "Annotation"
            println(rectangle.setterWithAnnotation)

            // Backing fields
            rectangle.counter = 5
            println(rectangle.counter)  // 5
            rectangle.counter = -1
            println(rectangle.counter)  // 0

            // Backing properties
            println(rectangle.table)
        }

        // Example 4 : Late-initialized properties and variables
        run {
            val test = MyTest()
            test.test()
        }

        // Example 5 : Overriding properties
        run {
            val shape = Shape2()
            println(shape.count)    // 0

            val shape2 = Rect2()
            println(shape2.count)   // 4

            val shape3 = Rect3()
            println(shape3.count)   // 5

            val shape4 = Polygon3();
            println(shape4.count)   // 10
        }
    }

    // Example 1 : Declaring properties,START
    class Address {
        var name: String = "Helmes"
        val id = 1
    }

    private fun copyAddress(address: Address): Address {
        val result = Address();
        result.name = address.name
        return result;
    }
    // Example 1 : Declaring properties,END

    // Example 2 : Getters and setters,START
    // val property is read-only, it's declaration differs with mutable(var):(1) val (2) not allow a setter
    class Rectangle(val simple: Int?, val width: Int, val height: Int) {   // (1) simple   : default getter, nullable, and must be initialized in constructor
        var count: Int = 1          // count    : (2) default setter and getter
        val type = 1                // type     : (3) default getter

        // (4) Define a custom getter
//        val area: Int       // property type is optional, because it can be inferred from the getter's return type
//            get() = this.width * this.height
        val area get() = this.width * this.height

        // (5) Define a custom setter and getter
        var representation: String
            get() = this.javaClass.simpleName
            set(value) {
                value + "_" + System.currentTimeMillis()
            }

        // (6) Annotate an accessor
        var setterVisibility: String = "visibility"
            private set     // setter is private and has default implementation
        var setterWithAnnotation: Any? = null
            @Inject set     // annotate the setter with Inject

        // (7) Backing fields
        // In kotlin, a field is only used as a part of a property to hold it's  value in memory.Fields can not be declared directly. Kotlin can provide an implicit backing field for a property.
        var counter = 0
            set(value) {
                field = if (value >= 0) {   // field identifier can only be used in accessors of the property
                    value
                } else {
                    0
                }
            }

        // (8) Backing properties
        // Instead implicit backing field scheme, you can fall back to having a backing property.
        private var _table: String? = null
        public val table: String
            get() {
                if (null == _table) {
                    _table = "Default"
                }
                return _table ?: throw AssertionError("table value is invalid")
            }
    }
    // Example 2 : Getters and setters,END

    // Example 3 : Compile-time constants, START
    class compileTimeConstants {
        companion object {
            // Use const to mark it as a compile time constant
            // Ref : https://kotlinlang.org/docs/properties.html#compile-time-constants
            // a top-level property or a member of an object declaration or a companion object
            // (1) It must be a top-level property, or a member of an object declaration or a companion object.
            // (2) It must be initialized with a value of type String or a primitive type
            // (3) It cannot be a custom getter
            const val SUB_SYSTEM: String = "the value of sub system is deprecated"
        }

        @Deprecated(SUB_SYSTEM)
        fun foo() {

        }
    }
    // Example 3 : Compile-time constants, END

    // Example 4 : Late-initialized properties and variables, START
    // https://kotlinlang.org/docs/properties.html#late-initialized-properties-and-variables
    class TestSubject {
        fun desc() {
            println("A TestSubject class")
        }
    }

    public class MyTest {
        // Properties declared as a non-null type must be initialized in the constructor.
        // Use lateinit to avoid null checking
        // (1）var properties declared inside the body of class (not in the primary constructor and only when the property does not have custom getter and setter)
        // (2) For top-leve properties and local variables.
        // (3) The type of property or variable must be non-null, and it must not be a primitive type.¬
        lateinit var subject: TestSubject

        fun test() {
//            subject.desc() // ERROR : lateinit property subject has not been initialized
            if (this::subject.isInitialized) { // check whether a lateinit var is initialized
                subject.desc()
            } else {
                println("TestSubject is not isInitialized")
            }
        }
    }
    // Example 4 : Late-initialized properties and variables, END

    // Example 5 : Overriding properties
    // Overriding the properties of class, START
    open class Shape2 {
        open val count: Int = 0;
    }

    class Rect2() : Shape2() {
        override val count: Int = 4
    }
    // Overriding the properties of class, END

    // Overriding the properties of interface, START
    interface Shape3 {
        val count: Int;
    }

    class Rect3(override val count: Int = 5) : Shape3

    class Polygon3 : Shape3 {
        override val count: Int = 10
    }
    // Overriding the properties of interface, END
}
