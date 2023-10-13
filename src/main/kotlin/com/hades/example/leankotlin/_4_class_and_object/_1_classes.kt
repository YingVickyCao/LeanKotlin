package com.hades.example.leankotlin._4_class_and_object

/**
 * https://kotlinlang.org/docs/classes.html
 */
fun main() {
    // 2 constructor,START
    run {
        var p: Person = Person(10)
        // p.id  // cannot access p.id

        var p2: Person2 = Person2(10)

        var p3: Person3 = Person3(10)

        var p4: Person4 = Person4(10, "Ming")
        println("${p4.id}-${p4.secondName}-${p4.firstName}")

        var p5: Person5 = Person5(10, "Ming", "Li")
    }

    // 2 constructor,END

    // 3 Secondary constructor,START
    run {
        var p4: Person4 = Person4(10, "Ming")
        val pet: Pet = Pet(p4)
        println(p4.id)


        /*
        Dog
        2
        */
        var pet2: Pet2 = Pet2("Dog", 2)
        println(pet2.name)
        println(pet2.age)

        /*
        Init block 1
        Init block 2
        Init block 3
        secondary constructor
         */
        var pet3: Pet3 = Pet3(2)

        var pet4: Pet4 = Pet4();

        //var pet5:Pet5 = Pet5();
    }
    // 3 Secondary constructor,END
}


// 1 We can define a class not having class header and class body
class Empty

// 2 constructor,START

// Class in Kotlin can have a primary constructor as part of the class header.
// If this primary constructor does not have any annotations or visibility modifiers, keyword constructor can be omitted.
class Person constructor(id: Int) { // id is a primary constructor parameter

}

class Person2(id: Int) {

}

// Initialization code can be placed in the initializer blocks "init". executed order is same order as defined.
// primary constructor parameters can be used in initializer blocks
class Person3(id: Int) {
    /*
     Ming
    first name is Ming
    Li
    second name is Li
     */
    val firstName = "Ming".also(::println)  // step 1

    init { // an initializer block
        println("id is $id,first name is $firstName") // step 2
    }

    val secondName = "Li".also(::println)   // step 3

    init {// an initializer block
        println("id is $id,second name is $secondName")   // step 4
    }
}

// Kotlin can declare properties and init them from the primary constructor, and these class properties declaration can also include default value
class Person4(var id: Int, var firstName: String, var secondName: String = "Li") {}

// primary constructor can have annotations or visibility modifiers, in this case, constructor keyword is required
class Person5 public /*@Inject*/ constructor(var id: Int, var firstName: String, var secondName: String) {}

// 2 constructor,END


// 3 Secondary constructor,START
class Pet {
    constructor(p: Person4) { // a secondary constructor
        p.id = 100
    }
}

// If the class has a primary constructor, each secondary constructor delegates the primary constructor using 'this' keyword either directly or indirectly.
class Pet2(var name: String) {
    var age: Int = 0

    constructor(
        name: String,
        age: Int
    ) : this(name) { // secondary constructor directly delegates the primary constructor by "this(name)"
        this.age = age
    }
}

// Codes in init blocks becomes a part of the primary constructor. Even if the class has no primary constructor, the delegation still happens implicitly.
class Pet3 {
    constructor(age: Int) {
        println("secondary constructor")
    }

    init {
        println("Init block 1")
    }

    init {
        println("Init block 2")
    }

    init {
        println("Init block 3")
    }
}

// If a non-abstract class does not declare any constructors(primary or secondary), it will have a generated public primary constructor with no arguments.
class Pet4 { // has a generated public primary constructor

}

// Declare an empty primary constructor with no-default visibility
class Pet5 private constructor() { // explicitly define a private primary constructor

}

// On the JVM, if all of the primary constructor parameters has default values, the compiler will generate an additional parameterless constructor which will use the default values.
class Pet6(var name: String = "Dog", var age: Int = 2) { //   JVM will auto generate : public Pet6()

}
// 3 Secondary constructor,END

// 4 Create an instance of classes,START

// 4 Create an instance of classes,END