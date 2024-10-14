package com.hades.example.leankotlin.standard_library._1_collections

import kotlin.math.sign

// https://kotlinlang.org/docs/list-operations.html
// List-specific operations


/**
 * Retrieve elements by index
 */
private fun test1() {
    // Lists -
    // Exception : get(), [index], elementAt(),first(), last(),
    // No exception : getOrNull(), getOrElse()
    val numbers = listOf(1, 2, 3, 4)
    println(numbers.get(0)) // 1
    println(numbers[0]) // 1
//    println(numbers.get(5)) // java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 4
    println(numbers.first()) // 1
    println(numbers.last()) // 4
    println(numbers.getOrNull(5)) // null
    println(numbers.getOrElse(5, { it }))  // 5
    println(numbers.elementAt(0)) // 1
    println(numbers.elementAt(5)) // java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 4
}


/**
 * Retrieve list parts
 */
private fun test2() {
    val numbers = (0..13).toList()
    println(numbers.subList(3, 6)) // [3, 4, 5]

    val numbers2 = (0..13).toMutableList()
    println(numbers2.subList(3, 6)) // [3, 4, 5]
    numbers2.add(0, 100)
    println(numbers2.subList(3, 6)) // [2, 3, 4]
}


/**
 * Find element positions
 */

/**
 * Find element positions - Linear search
 */
private fun test3_1() {
// Linear search
    // indexOf(),lastIndexOf(),indexOfFirst(),indexOfLast()
    val numbers = listOf(1, 2, 3, 4, 5)
    // indexOf: find the first position of an element using the functions indexOf(). If there are no such elements, both functions return -1.
    println(numbers.indexOf(2)) // 1
    println(numbers.indexOf(10)) // -1
    // lastIndexOf: find the last position of an element using the functions lastIndexOf(). If there are no such elements, both functions return -1.
    println(numbers.lastIndexOf(2)) // 1
    println(numbers.lastIndexOf(10)) // -1
    // indexOfFirst() returns the index of the first element matching the predicate or -1 if there are no such elements.
    println(numbers.indexOfFirst { it > 2 }) // 2
    // indexOfLast() returns the index of the last element matching the predicate or -1 if there are no such elements.
    println(numbers.indexOfLast { it % 2 == 1 }) // 4
}

/**
 * Find element positions - Binary search in sorted lists
 */
private fun test3_2() {
    fun test3_2_example1() {

        // Binary search in sorted lists
        // requires the list to be sorted in ascending order according to a certain ordering: natural or another one provided in the function parameter. Otherwise, the result is undefined.
        // To search an element in a sorted list, call the binarySearch() function passing the value as an argument. If such an element exists, the function returns its index; otherwise, it returns (-insertionPoint - 1)  where insertionPoint is the index where this element should be inserted so that the list remains sorted. If there is more than one element with the given value, the search can return any of their indices.
        val numbers = mutableListOf(2, 1, 4, 3)
        println(numbers) // [2, 1, 4, 3]
        numbers.sort()
        println(numbers) // [1, 2, 3, 4]

        println(numbers.binarySearch(2)) // 1
        println(numbers.binarySearch(10)) // -5 : to be inserted position
        // Also specify an index range to search in: in this case, the function searches only between two provided indices.
        println(numbers.binarySearch(2, 0, 2)) // 1
    }

    fun test3_2_example2() {
        // Comparator binary search
        // When list elements aren't Comparable, you should provide a Comparator to use in the binary search. The list must be sorted in ascending order according to this Comparator.
        class Product(val name: String, val price: Double)

        val productList = listOf(Product("WebStorm", 49.0), Product("AppCode", 99.0), Product("DotTrace", 129.0), Product("ReSharper", 149.0))
        val result = productList.binarySearch(Product("AppCode", 99.0), compareBy<Product> { it.price }.thenBy { it.name })
        println(result) // 1

        // Custom comparators are also handy when a list uses an order different from natural one
        val colors = listOf("Blue", "green", "ORANGE", "Red", "yellow")
        val colorResult = colors.binarySearch("RED", String.CASE_INSENSITIVE_ORDER)
        println(colorResult) // 3
    }

    fun test3_2_example3() {
        // Comparison binary search
        // Binary search with comparison function lets you find elements without providing explicit search values. Instead, it takes a comparison function mapping elements to Int values and searches for the element where the function returns zero. The list must be sorted in the ascending order according to the provided function; in other words, the return values of comparison must grow from one list element to the next one.
        data class Product(val name: String, val price: Double)

        val productList = listOf(
            Product("WebStorm", 49.0),
            Product("AppCode", 99.0),
            Product("DotTrace", 129.0),
            Product("ReSharper", 149.0)
        )

        fun priceComparison(product: Product, price: Double) = sign(product.price - price).toInt()

        val result = productList.binarySearch { priceComparison(it, 99.0) }
        println(result) // 1
    }
//    test3_2_example1()
//    test3_2_example2()
    test3_2_example3()
}

/**
 * List write operations
 */

/**
 * List write operations - Add
 */
private fun test4_1() {
    // List - add((), addAll()
    val numbers = mutableListOf<String?>("one", "five", "six")
    numbers.add(1, "two")
    numbers.addAll(2, listOf("three", "four"))
    numbers.add(null)
    numbers.add(null)
    println(numbers) // [one, two, three, four, five, six, null, null]
}

/**
 * List write operations - Update
 */
private fun test4_2() {
    fun test4_2_example1() {
        // List - set() / operation []
        val numbers = mutableListOf("one", "five", "three")
        numbers[1] = "two"
//    numbers.set(1, "two") // equal to operator []
//    numbers[5] = "six" // java.lang.IndexOutOfBoundsException: Index 5 out of bounds for length 3
        println(numbers) // [one, two, three]
    }

    // List - fill() : replace all the elements with the specific value.
    fun test4_2_example2() {
        val numbers = mutableListOf(1, 2, 3, 4)
        numbers.fill(3)
        println(numbers) // [3, 3, 3, 3]
    }
//    test4_2_example1()
    test4_2_example2()
}

/**
 * List write operations - Remove
 */
private fun test4_3() {
    val numbers = mutableListOf(1, 2, 3, 4, 3)
    numbers.removeAt(1)
    println(numbers) // [1, 3, 4, 3]

    numbers.remove(4)
    println(numbers) // [1, 3, 3]

    numbers.remove(3)
    println(numbers) // [1, 3]
}

/**
 * List write operations - Sort
 */
private fun test4_4() {
    val numbers = mutableListOf("one", "two", "three", "four")
    numbers.sort() // sort into ascending
    println(numbers) // [four, one, three, two]

    numbers.sortDescending() // sort into descending
    println(numbers) // [two, three, one, four]

    numbers.sortBy { it.length } // sort into ascending by length
    println(numbers) // [two, one, four, three]

    numbers.sortByDescending { it.last() } // sort into descending by the last letter
    println(numbers) // [four, two, one, three]

    numbers.sortWith(compareBy<String> { it.length }.thenBy { // sort by comparator: first comparing by length, secondly comparing by item
//        println(it)
        it
    })
    println(numbers)  // [one, two, four, three]

    numbers.shuffle()
    println(numbers) // [four, two, one, three]

    numbers.reverse()
    println(numbers) // [three, one, two, four]
}

fun main() {
//    test1()
//    test2()
//    test3_1()
//    test3_2()
//    test4_1()
//    test4_2()
//    test4_3()
    test4_4()
}