package com.hades.example.leankotlin._3_control_flow._2_loop

fun main() {
    for_loops()
}

fun for_loops() {

    // foreach
    val ints = IntArray(3) {
        it * 2
    }

    for (item in ints) print("  $item")
    println()

    for (item in ints) { // body of for is a block
        print("  $item")
    }
    println()


    for (item: Int in ints) { // body of for is a block
        print("  $item")
    }
    println()

    // To iterate over a range of numbers, use a range expression.
    for (i in 1..3) {
        print("  " + i)
    }
    println()

    for (i in 6 downTo 0 step 2) {
        print("  " + i)
    }
    println()

    // iterate through an array or a list with an index
    var array = IntArray(3) { it ->
        it * 10
    }
    for (i in array.indices) { // Way 1 : array.indices
        print("[$i]=" + array[i] + "  ")
    }
    println()
    for ((index, value) in array.withIndex()) {
        println("at $index is $value")
    }
    println()
}