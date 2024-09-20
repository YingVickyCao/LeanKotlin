package com.hades.example.leankotlin.standard_library._1_collections

// https://kotlinlang.org/docs/ranges.html
// Ranges and progressions


/**
 * Ranges and progressions
 */

private fun test1() {
    // Closed-ended range
    println(4 in 1..4)  // true
    println(5 in 1..4)  // false

    // Open-ended range
    println(4 in 1..<4) // false

    // Ranges for loop
    for (i in 1..4) {
        print("$i ") // 1 2 3 4
    }
    println()

    // Use downto to iterate numbers in reverse order
    for (i in 4 downTo 1) {
        print("$i ") // 4 3 2 1
    }
    println()

    // iterator over functions with an arbitrary (not necessarily)
    for (i in 0..8 step 2) {
        print("$i ") // 0 2 4 6 8
    }
    println()

    for (i in 0..<8 step 2) {
        print("$i ") // 0 2 4 6
    }
    println()

    for (i in 8 downTo 0 step 2) {
        print("$i ") // 8 6 4 2 0
    }
    println()
}

/**
 * Progression
 */
private fun test2() {
    for (i in 1..10) { // step 1
        print("$i  ") // 1  2  3  4  5  6  7  8  9  10
    }
    println()

    for (i in 1..10 step 2) { // step 1
        print("$i  ") // 1  3  5  7  9
    }
    println()

    val filtered = (1..10).filter { it % 2 == 0 }
    println(filtered) // [2, 4, 6, 8, 10]
}

fun main() {
//    test1()
    test2()
}