/*
public final class MyUtils {
 */
// compile error : Kotlin: Duplicate JVM class name 'com/hades/example/leankotlin/_3_compatibility/MyUtils' generated from: MyUtils, MyUtils
//@file:JvmName("MyUtils")

@file:JvmName("MyUtils")
@file:JvmMultifileClass

package com.hades.example.leankotlin._3_compatibility

fun printCore(score: Int) {
    println(score)
}