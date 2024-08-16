package com.hades.example.leankotlin.concepts._1_types._2_data_type._2_object_type

fun main() {
    array()
//    primitiveTypeArray()
}

fun array() {
    run {
        val x = arrayOf(1, 2, 3) // [1, 2, 3]
        println(x.contentToString())

        // set
        x.set(0, 100)
        // get
        println(x.get(0)) // index 0, value = 100
        // size
        println(x.size) // 3

        val x2 = arrayOfNulls<Int>(3);  // [null, null, null]
        println(x2.contentToString())
    }

    run {
        /**
        遍历list，每次遍历，返回对应index的value
        两种方式等价
         */
        val x = Array(3) { i ->
            i + 10
        } // [10, 11, 12]
        println(x.contentToString())

        val x2 = Array(3) {
            it + 10
        } // [10, 11, 12]
        println(x.contentToString())
    }

}

fun primitiveTypeArray() {
    run {
        val x: IntArray = intArrayOf(-1, 2, 3) // [-1, 2, 3]
        println(x.contentToString())

        x[0] = x[1] + x[2]
        println(x.contentToString()) // [5, 2, 3]
    }

    run {
        val x = IntArray(3); // [0, 0, 0]
        println(x.contentToString())
    }

    run {
        val x = IntArray(3) { 100 } // [100, 100, 100]
        println(x.contentToString())
    }

    run {
        // 遍历赋值
        val x = IntArray(3) { // [0, 1, 2]
            it * 1
        }
        print(x.contentToString())
    }
}