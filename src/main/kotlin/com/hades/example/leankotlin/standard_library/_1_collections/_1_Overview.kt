package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collections-overview.html
// Collections overview

/**
 * Collection types
 */

private fun test1() {
    val numbers = mutableListOf("one", "two")
    numbers.add("three")
    println(numbers) // [one, two, three]

    // TODO:
    // The read-only collection types are covariant.
    // In turn, mutable collections aren't covariant; otherwise, this would lead to runtime failures.
    open class Shape
    class Rectangle : Shape()
    class Circle : Shape()

    fun print(shape: Shape) {
        print("${shape.javaClass.simpleName} ")
    }
    run {
        val shapes = mutableListOf<Shape>(Rectangle(), Circle(), Shape())
        shapes.add(Circle())
        for (shape in shapes) {
            print(shape)
        }
        println()
    }
    run {
        val shapes = mutableListOf<Rectangle>(Rectangle())
        shapes.add(Rectangle())
        for (shape in shapes) {
            print(shape)
        }
        println()
    }

    run {
        val shapes = listOf<Shape>(Rectangle(), Circle(), Shape())
        for (shape in shapes) {
            print(shape)
        }
        println()
    }

    run {
        val shapes = listOf<Rectangle>(Rectangle())
        for (shape in shapes) {
            print(shape)
        }
        println()
    }
}

/**
 * Collection types -  Collection
 */
private fun test1_1() {
    run {
        // use Collection as a parameter of a function that applies to different collection types.
        fun printAll(strings: Collection<String>) {
            for (s in strings) print("$s ")
            println()
        }

        val strings = listOf("One", "two")
        printAll(strings)

        val stringSet = setOf("One", "two")
        printAll(stringSet)

        // For more specific cases, use the Collection's inheritors: List and Set.
    }

    run {
        fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
            println("shortWords size:${shortWords.size},contents=$shortWords")
            this.filterTo(shortWords) { it.length <= maxLength } // Copy item from words to shortWords if size <= <= maxLength of item from words
            println("shortWords size:${shortWords.size},contents=$shortWords")
            val articles = setOf("a", "A", "an", "An", "the", "The")
            shortWords -= articles // Remove items from shortWords which included in articles
            println("shortWords size:${shortWords.size},contents=$shortWords")
        }

        val words: List<String> = "A long time ago in a galaxy far far away".split(" ")
        println("words size:${words.size},contents=$words")
        val shortWords = mutableListOf<String>()
        words.getShortWordsTo(shortWords, 3)
        println("shortWords size:${words.size},shortWords=$words")
    }
}

/**
 * Collection types -  List
 */
private fun test1_2() {
    fun test1_2_example1() {
        val numbers = listOf("one", "two", "three", "four")
        println("size = ${numbers.size}") // 4
        println("get(2) = ${numbers.get(2)}") // three
        println("[2] = ${numbers[2]}") // three
        println("indexOf = ${numbers.indexOf("two")}") // 1
        println("indexOf = ${numbers.indexOf("five")}") // -1
    }

    fun test1_2_example2() {
        data class Person(var name: String, var age: Int) // auto override equals and hashCode

        val bob = Person("Bob", 31)
        val people = listOf(Person("Adam", 20), bob, bob)
        val people2 = listOf(Person("Adam", 20), Person("Bob", 31), bob)
        println(people == people2) // true
        println(people === people2) // false
        bob.age = 32
        println(people == people2) // false
        println(people === people2) // false
    }

    fun test1_2_example3() {
        class Person(var name: String, var age: Int) // not override equals and hashCode

        val bob = Person("Bob", 31)
        val people = listOf(Person("Adam", 20), bob, bob)
        val people2 = listOf(Person("Adam", 20), Person("Bob", 31), bob)
        println(people == people2) // false
        println(people === people2) // false
        bob.age = 32
        println(people == people2) // false
        println(people === people2) // false
    }

    fun test1_2_example4() {
        class Person(var name: String, var age: Int) { // override equals and hashCode
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Person

                if (name != other.name) return false
                if (age != other.age) return false

                return true
            }

            override fun hashCode(): Int {
                var result = name.hashCode()
                result = 31 * result + age
                return result
            }
        }

        val bob = Person("Bob", 31)
        val people = listOf(Person("Adam", 20), bob, bob)
        val people2 = listOf(Person("Adam", 20), Person("Bob", 31), bob)
        println(people == people2) // true
        println(people === people2) // false
        bob.age = 32
        println(people == people2) // false
        println(people === people2) // false
    }

    // MutableList<T> is a List with list-specific write operations, for example, to add or remove an element at a specific position.
    fun test1_2_example5() {
        val numbers = mutableListOf(1, 2, 3, 4)
        numbers.add(5)
        println(numbers) // [1, 2, 3, 4, 5]

        numbers.remove(1)
        println(numbers) // [2, 3, 4, 5]

        numbers[0] = 0
        println(numbers) // [0, 3, 4, 5]

        numbers.shuffle()
        println(numbers) // [0, 3, 5, 4]
    }

//    test1_2_example1()
//    test1_2_example2()
//    test1_2_example3()
//    test1_2_example4()
    test1_2_example5()
}


private fun test1_3() {

}


private fun test1_4() {

}

fun main() {
    test1_2()
}