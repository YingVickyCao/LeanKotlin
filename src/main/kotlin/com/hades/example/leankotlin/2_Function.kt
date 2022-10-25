package com.hades.example.leankotlin

/**
 * 函数
 */
fun main() {
    val course = "Math"
    printCourse(course);
}

fun printCourse(course: String): String {
    println("String is $course")
    return course
}