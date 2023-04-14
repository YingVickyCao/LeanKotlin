package com.hades.example.leankotlin._3_control_flow

fun main() {
    for_loops()
}

fun for_loops() {

    // foreach
    val ints = IntArray(3) {
        it * 2
    }
    for (item in ints) print("  $item")
    for (item in ints) { // body of for is a block
        print("  $item")
    }
    for (item: Int in ints) { // body of for is a block
        print("  $item")
    }

    // for ihas a member for
}