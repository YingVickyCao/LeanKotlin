package com.hades.example.leankotlin._3_control_flow._2_loop

fun main() {
    /*run {
        var x = 3;
        while_loops(x)

        var y = 0
        while_loops(y)
    }*/

    run {
        var x = IntArray(1)
        x[0] = 3
        println("START,x[0]=" + x[0])
        while_loops(x)
        println("END,x[0]=" + x[0])
        println()

        /*
         START,y[0]=0
         END,y[0]=0
         */
        var y = IntArray(1)
        y[0] = 0
        println("START,y[0]=" + y[0])
        while_loops(y)
        println("END,y[0]=" + y[0])
    }
}

// param is val. if you need to modify it, pass an object to wrapper it
//fun while_loops(x: Int) {
//    while (x > 0) {
//        x -= 1  // Error : compile error - Val cannot be reassigned
//    }
//}

fun while_loops(array: IntArray) {
    while (array[0] > 0) {
        print("before," + array[0])
        array[0] -= 1
        print(",after," + array[0])
        println()
    }
}