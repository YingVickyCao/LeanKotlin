package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/collection-write.html
// Write operations

/**
 * Adding elements
 */
private fun test1() {
    fun test1_example1() {
        // Set / List : add()
        val numbers = mutableListOf(1, 2, 3, 4)
        numbers.add(5)
        println(numbers) // [1, 2, 3, 4, 5]
    }

    fun test1_example2() {
        // List: addAll()
        val numbers = mutableListOf(1, 2, 5, 6)
        numbers.addAll(arrayOf(7, 8))
        println(numbers) // [1, 2, 5, 6, 7, 8]

        numbers.addAll(2, setOf(3, 4)) // set the insert index
        println(numbers) // [1, 2, 3, 4, 5, 6, 7, 8]
    }

    fun test1_example3() {
        //  plus operator
        //  plusAssign (+=)
        val numbers = mutableListOf("one", "two")
        numbers += "three"
        println(numbers) // [one, two, three]
        numbers += listOf("four", "five")
        println(numbers) // [one, two, three, four, five]
    }
//    test1_example1()
//    test1_example2()
    test1_example3()
}

/**
 * Removing elements
 */
private fun test2() {
    fun test2_example1() {
        // remove()
        val numbers = mutableListOf(1, 2, 3, 4, 3)
        numbers.remove(3) // remove 3
        println(numbers) // [1, 2, 4, 3]
        numbers.remove(5) // remove nothing
        println(numbers) // [1, 2, 4, 3]
    }

    fun test2_example2() {
        // removeAll() removes all elements that are present in the argument collection.
        // retainAll() is the opposite of removeAll(): it removes all elements except the ones from the argument collection. W
        val numbers = mutableListOf(1, 2, 3, 4, 3)
        numbers.removeAll { it >= 3 }
        println(numbers) // [1, 2]

        val numbers2 = mutableListOf(1, 2, 3, 4, 3)
        numbers2.retainAll { it >= 3 }
        println(numbers2) // [3, 4, 3]

        // clear() removes all elements from a list and leaves it empty.
        numbers2.clear()
        println(numbers2) // []
    }

    fun test2_example3() {
        // Another way to remove elements from a collection is with the minusAssign (-=) operator – the in-place version of minus.
        val numbers = mutableListOf("one", "two", "three", "three", "four")
        numbers -= "three"
        println(numbers) // [one, two, three, four]
        numbers -= listOf("four", "five")
        println(numbers) // [one, two, three]
    }
//    test2_example1()
//    test2_example2()
    test2_example3()
}

/*
* Updating elements
*/
private fun test3() {
// Lists and maps also provide operations for updating elements.
// They are described in List-specific Operations and Map Specific Operations.
}

fun main() {
//    test1()
    test2()
    test3()
}