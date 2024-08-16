package com.hades.example.leankotlin._2_control_flow._3_jump

fun main() {
//    kotlin_unlabeled_break()
    kotlin_lableded_break()
}

// unlabeked break : terminates the nearest enclosing loop.
fun kotlin_unlabeled_break() {
    run {
        // example : kotlin unlabeled break in for loop
        val array = IntArray(5) { it ->
            (it + 1) * 10
        }
        for (item in array) {
            if (item == 40) {
                break
            }
            println(item)
        }
        println()
    }


    run {
        // example : kotlin unlabeled break in while loop
        val array = IntArray(5) { it ->
            (it + 1) * 10
        }
        var i = 0
        while (true) {
            if (array[i] == 40) {
                break
            }
            println(array[i])
            i++
        }
        println()

    }

    run {
        // example : kotlin unlabeled break in do while loop
        val array = IntArray(5) { it ->
            (it + 1) * 10
        }
        var i = 0;
        do {
            if (array[i] == 40) {
                break
            }
            println(array[i])
            i++
        } while (true)
    }
}

// labeled break : terminates the desired loop (can be an outer loop)
fun kotlin_lableded_break() {
    run {
        // example : kotlin unlabeled break in for loop
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
                    break
                }
            }
            println()
        }
        println()
    }

    run {
        // example : kotlin labeled break in for loop
        /*
        i=1,j=1  i=1,j=2
        i=2,j=1
         */
        outer@ for (i in 1..4) {
            inter@ for (j in 1..2) {
                print("i=$i,j=$j  ")
                if (i == 2) {
                    break@outer
                }
            }
            println()
        }
    }
}