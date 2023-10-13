package com.hades.example.leankotlin._3_control_flow._2_loop

fun main() {
    run {
        var x = IntArray(1)
        x[0] = 3
        println("START,x[0]=" + x[0])
        do_while_loops(x)
        println("END,x[0]=" + x[0])
        println()

        /*
            START,y[0]=0
            before,0,after,-1
            END,y[0]=-1
         */
        var y = IntArray(1)
        y[0] = 0
        println("START,y[0]=" + y[0])
        do_while_loops(y)
        println("END,y[0]=" + y[0])
    }
}

fun do_while_loops(array: IntArray) {
    do {
        print("before," + array[0])
        array[0] -= 1
        print(",after," + array[0])
        println()
    } while (array[0] > 0)
}