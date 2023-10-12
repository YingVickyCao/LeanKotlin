package com.hades.example.leankotlin._2_data_type._3_type_checks_and_casts

fun main() {
    checkType()
}


// Check type,START
/**
 * is
 * !is
 */
fun checkType() {
    run {
        val obj = "a123"
        if (obj is String) {
            println("string")   // invoked
        }
    }
    run {
        val obj = "a123"
//        if (obj !is String) {
        // or
        if (!(obj is String)) {
            println("not string")
        } else {
            println(obj.length) // 4
        }
    }
}
// Check type,END


// Generics type check,START
fun generics_type_checks() {
    // TODO:
}
// Generics type check,end