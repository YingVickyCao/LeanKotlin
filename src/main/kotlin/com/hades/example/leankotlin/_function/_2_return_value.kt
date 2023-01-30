package com.hades.example.leankotlin

/**
 * 函数
 */
fun main() {
    printInt(5)
    printInt2(5)

    val course = "Math"
    print(printCourse(course)) // Course is Math
}

// This method does not have return
fun printInt(id: Int) {
    println(id)
}

// This method does not have return
fun printInt2(id: Int): Unit {
    println(id)
}

// his method returns bool value
fun printCourse(course: String): Boolean {
    //
    println("Course is $course")
    return true
}