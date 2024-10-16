package com.hades.example.leankotlin.standard_library._4_scope_functions

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f
import kotlin.random.Random

// https://kotlinlang.org/docs/scope-functions.html
// Scope functions

private data class Person(val name: String, var age: Int = 0, var city: String = "") {
    fun moveTo(city: String) {
        this.city = city
    }

    fun incrementAge() {
        this.age++
    }
}

/**
 * Scope functions
 */
private fun test1() {

    // Use a scope function
    Person("Alice", 20, "Amsterdam").let {
        println(it) // Person(name=Alice, age=20, citi=Amsterdam)
        it.moveTo("Landon")
        it.incrementAge()
        println(it) // Person(name=Alice, age=21, citi=Landon)
    }

    // If you write the same without let, you'll have to introduce a new variable and repeat its name whenever you use it.
    val alice = Person("Alice", 20, "Amsterdam")
    println(alice) // Person(name=Alice, age=20, citi=Amsterdam)
    alice.moveTo("Landon")
    alice.incrementAge()
    println(alice) // Person(name=Alice, age=21, citi=Landon)
    // Scope functions don't introduce any new technical capabilities, but they can make your code more concise and readable.
}

/**
 * Function selection
 */

// Although scope functions can make your code more concise, avoid overusing them: it can make your code hard to read and lead to errors. We also recommend that you avoid nesting scope functions and be careful when chaining them because it's easy to get confused about the current context object and value of this or it.

/**
 * Distinctions
 */

private fun test3() {
//    test3_1()
    test3_2()
}

/**
 * Distinctions - Context object: this or it
 */
private fun test3_1() {
    fun test3_1_example1() {
        // Each scope function uses one of two ways to reference the context object: as a lambda receiver (this) or as a lambda argument (it).
        val str = "Hello"
        // this
        str.run {
            println("The string's length: $length") // The string's length: 5
            // does the same
            println("The string's length: ${this.length}") // The string's length: 5
        }
        // it
        str.let {
            println("The string's length: ${it.length}") // The string's length: 5
        }
    }

    fun test3_1_example2() {
        // this
        // run, with, and apply reference the context object as a lambda receiver - by keyword this.
        // On the other hand, if this is omitted, it can be hard to distinguish between the receiver members and external objects or functions. So having the context object as a receiver (this) is recommended for lambdas that mainly operate on the object's members by calling its functions or assigning values to properties.
        val adam = Person("Adam").apply {
            age = 20 // same as this.age = 20
            city = "London"
        }
        println(adam) // Person(name=Adam, age=20, city=London)
    }

    fun test3_1_example3() {
        // it
        // In turn, let and also reference the context object as a lambda argument. If the argument name is not specified, the object is accessed by the implicit default name it. it is shorter than this and expressions with it are usually easier to read.
        fun getRandomInt(): Int {
//            return Random.nextInt(100).also {
//                println("getRandomInt() generated value $it") // getRandomInt() generated value 73
//            }

            return Random.nextInt(100).also { value ->
                println("getRandomInt() generated value $value") // getRandomInt() generated value 73
            }
        }

        val i = getRandomInt()
        println(i) // 73
    }
//    test3_1_example1()
//    test3_1_example2()
    test3_1_example3()
}

/**
 * Distinctions - Return value
 */
private fun test3_2() {
    // Scope functions differ by the result they return:
    //apply and also return the context object.
    //let, run, and with return the lambda result.

    fun test3_2_example1() {// Context object
        // The return value of apply and also is the context object itself. Hence, they can be included into call chains as side steps: you can continue chaining function calls on the same object, one after another.
        val numberList = mutableListOf<Double>()
        numberList.also { println("Populating the list") }.apply {
            add(2.71)
            add(3.14)
            add(1.0)
        }.also { println("Sorting the list") }.sort()
        println(numberList)
        // print:
        // Populating the list
        //Sorting the list
        //[1.0, 2.71, 3.14]


        // They also can be used in return statements of functions returning the context object.
        fun getRandomInt(): Int {
            return Random.nextInt(100).also {
                println("getRandomInt() generated value $it") // getRandomInt() generated value 73
            }
        }

        val i = getRandomInt()
        println(i)
    }

    fun test3_2_example2() { // Lambda result
        // let, run, and with return the lambda result. So you can use them when assigning the result to a variable, chaining operations on the result, and so on.
        val numbers = mutableListOf("one", "two", "three")
        val countEndsWithE = numbers.run {
            add("four")
            add("five")
            count { it.endsWith("e") }
        }
        println("There are $countEndsWithE elements that end with e")
        // print:
        // There are 3 elements that end with e

        // Additionally, you can ignore the return value and use a scope function to create a temporary scope for local variables.

        with(numbers) {
            val firstItem = first()
            val lastItem = last()
            println("First item:$firstItem,last item:$lastItem")
        }
        // print:
        // First item:one,last item:five
    }
//    test3_2_example1()
    test3_2_example2()
}

/**
 * Functions
 */

private fun test4() {
    test4_1()
    test4_2()
    test4_3()
    test4_4()
    test4_5()
}

/**
 * Functions - let
 */
private fun test4_1() {
    // let
    // ·The context object is available as an argument (it).
    // ·The return value is the lambda result.

    fun test4_1_example1() {
        // let can be used to invoke one or more functions on results of call chains.

        val numbers = mutableListOf("one", "two", "three", "four", "five")
        val resultList = numbers.map { it.length }.filter { it > 3 }
        println(resultList) // [5, 4, 4]
    }

    fun test4_1_example2() {
        // With let, you can rewrite the above example so that you're not assigning the result of the list operations to a variable:
        val numbers = mutableListOf("one", "two", "three", "four", "five")
        numbers.map { it.length }.filter { it > 3 }.let {
            println(it) // [5, 4, 4]
        }
    }

    fun test4_1_example3() {
        // If the code block passed to let contains a single function with it as an argument, you can use the method reference (::) instead of the lambda argument:
        val numbers = mutableListOf("one", "two", "three", "four", "five")
        numbers.map { it.length }.filter { it > 3 }.let(::println)
        // [5, 4, 4]
    }

    fun test4_1_example4() {
        // let is often used to execute a code block containing non-null values. To perform actions on a non-null object, use the safe call operator ?. on it and call let with the actions in its lambda.
        fun processNonNullString(str: String) {
            // param is not null
        }

        val str: String? = "Hello"
//        processNonNullString(str) // compilation error: str can be null
        val length = str?.let {
            println("let() called on $it")
            processNonNullString(it) // OK: it is not null inside ?.let{}
            it.length
        }
        println(length) // 5
    }
    test4_1_example1()
    test4_1_example2()
    test4_1_example3()
    test4_1_example4()
}

/**
 * Functions - with
 */
private fun test4_2() {
    // with
    // .The context object is available as a receiver (this).
    // .The return value is the lambda result.
    fun test4_2_example1() {
        // As with is not an extension function: the context object is passed as an argument, but inside the lambda, it's available as a receiver (this).
        //We recommend using with for calling functions on the context object when you don't need to use the returned result. In code, with can be read as " with this object, do the following. "
        val numbers = mutableListOf("One", "two", "three")
        with(numbers) {
            println("with is called with argument $this") // with is called with argument [One, two, three]
            println("It contains $size elements") // It contains 3 elements
        }
    }

    fun test4_2_example2() {
        // You can also use with to introduce a helper object whose properties or functions are used for calculating a value.
        val numbers = mutableListOf("One", "two", "three")
        val firstAndLast = with(numbers) {
            "The first element is ${first()}," + "the last element is ${last()}." // The first element is One,the last element is three.
        }
        println(firstAndLast)
    }
    test4_2_example1()
    test4_2_example2()
}

/**
 * Functions - run
 */
private fun test4_3() {
    // run
    // .The context object is available as a receiver (this).
    // .The return value is the lambda result.

    fun test4_3_example1() {
        // run does the same as with but it is implemented as an extension function. So like let, you can call it on the context object using dot notation.
        //run is useful when your lambda both initializes objects and computes the return value.

        data class MultipleService(var url: String, var port: Int) {
            fun query(content: String): String {
                return content
            }

            fun prepareRequest(): String {
                return "prepare request $url"
            }
        }

        val service = MultipleService("https://example.kotlinlang.org", 80)
        val result = service.run {
            port = 8080
            query(prepareRequest() + " to port $port")
        }
        println(result) // log: prepare request https://example.kotlinlang.org to port 8080

        // the same code written with let() function.
        val service2 = MultipleService("https://example.kotlinlang.org", 80)
        val result2 = service.let {
            it.port = 8080
            it.query(it.prepareRequest() + " to port ${it.port}")
        }
        println(result2) // log: prepare request https://example.kotlinlang.org to port 8080
    }

    fun test4_3_example2() {
        // You can also invoke run as a non-extension function. The non-extension variant of run has no context object, but it still returns the lambda result. Non-extension run lets you execute a block of several statements where an expression is required. In code, non-extension run can be read as " run the code block and compute the result. "
        val hexNumberRegex = run {
            val digits = "0-9"
            val hexDigits = "A-Fa-f"
            val sign = "+-"
            Regex("[$sign]?[$digits$hexDigits]+")
        }
        // +123
        // -FFFF
        // 88
        for (match in hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) {
            println(match.value)
        }
    }
    test4_3_example1()
    test4_3_example2()
}

/**
 * Functions - apply
 */
private fun test4_4() {
    // apply
    // .The context object is available as a receiver (this).
    // .The return value is the object itself.
    // As apply returns the context object itself, we recommend that you use it for code blocks that don't return a value and that mainly operate on the members of the receiver object. The most common use case for apply is for object configuration. Such calls can be read as " apply the following assignments to the object. "
    val adam = Person("Adam").apply {
        age = 32
        city = "London"
    }
    println(adam) // Person(name=Adam, age=32, city=London)
}

/**
 * Functions - also
 */
private fun test4_5() {
    // also
    // . The context object is available as an argument (it).
    // . The return value is the object itself.

    // also is useful for performing some actions that take the context object as an argument. Use also for actions that need a reference to the object rather than its properties and functions, or when you don't want to shadow the this reference from an outer scope.
    // When you see also in code, you can read it as " and also do the following with the object. "

    val numbers = mutableListOf("One", "two", "three")
    numbers.also { println("The list elements before adding new one: $it") } // The list elements before adding new one: [One, two, three]
        .add("four")
    println(numbers) // [One, two, three, four]
}

/**
 * takeIf and takeUnless
 */
private fun test5() {
    // In addition to scope functions, the standard library contains the functions takeIf and takeUnless. These functions let you embed checks of an object's state in call chains.
    fun test5_example1() {
        // When called on an object along with a predicate, takeIf returns this object if it satisfies the given predicate. Otherwise, it returns null. So, takeIf is a filtering function for a single object.
        //takeUnless has the opposite logic of takeIf. When called on an object along with a predicate, takeUnless returns null if it satisfies the given predicate. Otherwise, it returns the object.

        val number = Random.nextInt(100)
        println(number)
        val eventOrNull = number.takeIf { it % 2 == 0 }
        val oddOrNull = number.takeUnless { it % 2 == 0 }
        println("event: $eventOrNull, odd: $oddOrNull")
        //print:
        // 11
        // event: null, odd: 11

        // 48
        // event: 48, odd: null
    }

    fun test5_example2() {
        // When chaining other functions after takeIf and takeUnless, don't forget to perform a null check or use a safe call (?.) because their return value is nullable.

        val str = "Hello"
//        val caps = str.takeIf { it.isNotEmpty() }.uppercase() // compilation error
        val caps = str.takeIf { it.isNotEmpty() }?.uppercase()
        println(caps) // HELLO
    }

    fun test5_example3() {
        // takeIf and takeUnless are especially useful in combination with scope functions.  For example, you can chain takeIf and takeUnless with let to run a code block on objects that match the given predicate. To do this, call takeIf on the object and then call let with a safe call (?). For objects that don't match the predicate, takeIf returns null and let isn't invoked.

        fun displaySubstringPosition(input: String, sub: String) {
            input.indexOf(sub).takeIf { it >= 0 }?.let {
                println("The substring $sub is found in $input.")
                println("Its start position is $it")
            }
        }
        displaySubstringPosition("010000011", "11")
        displaySubstringPosition("010000011", "12")

        // print:
        // The substring 11 is found in 010000011.
        // Its start position is 7
    }

    fun test5_example4() {
        // For comparison, below is an example of how the same function can be written without using takeIf or scope functions:
        fun displaySubstringPosition(input: String, sub: String) {
            val index = input.indexOf(sub)
            if (index >= 0) {
                println("The substring $sub is found in $input.")
                println("Its start position is $index")
            }
        }
        displaySubstringPosition("010000011", "11")
        displaySubstringPosition("010000011", "12")
        // print:
        // The substring 11 is found in 010000011.
        // Its start position is 7
    }
    test5_example1()
    test5_example2()
    test5_example3()
    test5_example4()
}

fun main() {
//    test1()
//    test3()
//    test4()
    test5()
}