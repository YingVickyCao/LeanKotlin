package com.hades.example.leankotlin._2_control_flow._3_jump

fun main() {
    kotlin_unlabeled_continue()
    kotlin_labeled_continue()
}

/**
 * unlabeked continue : skips the current iteration of the nearest enclosing loop.
 */
fun kotlin_unlabeled_continue() {
    // example : kotlin unlabeled continue in for loop
    run {
        /*
        i=1,j=1  i=1,j=2
        i=2,j=1
        i=3,j=1  i=3,j=2
        i=4,j=1  i=4,j=2
         */
        for (i in 1..4) {
            for (j in 1..2) {
                print("i=$i,j=$j  ")
                if (i == 2) {
                    continue
                }
            }
            println()
        }
        println()
    }
}

/**
 * labeled continue :skips the iteration if the desired loop (can be an outer loop)
 */
fun kotlin_labeled_continue() {
    /*
    i=1,j=1  i=1,j=2
    i=2,j=1
    i=3,j=1  i=3,j=2
    i=4,j=1  i=4,j=2
     */
    outer@ for (i in 1..4) {
        inter@ for (j in 1..2) {
            print("i=$i,j=$j  ")
            if (i == 2) {
                println()
                continue@outer
            }
        }
        println()
    }
    println()
}