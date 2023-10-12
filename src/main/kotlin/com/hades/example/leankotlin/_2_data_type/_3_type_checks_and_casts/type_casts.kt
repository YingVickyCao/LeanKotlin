package com.hades.example.leankotlin._2_data_type._3_type_checks_and_casts

fun main() {
//    smartCasts()
    unsafe_cast()
    safe_cast()
}

// Smart Cast, START
fun smartCasts() {
    var x = "abc";
    var x2 = 100

//    demo1(x)
//    demo1(x2)
//
//    demo2(x)
//    demo2(x2)
//
//    demo3(x)
//    demo3(x2)
//
//    demo4(x)
//    demo4(x2)

    demo5(x)
    demo5(x2)
}

// Smart Cast, START
fun demo1(x: Any) {
    if (x is String) { // [is ]x is auto cast to a String
        println(x.length)
    }
}

fun demo2(x: Any) {
    if (x !is String) {
        return
    }

    println(x.length) //  [!is ] x is auto cast to a String

    // In most cases, you don't need to use explicit cast operators in Kotlin because the compiler tracks the is-checks and explicit casts for immutable values and inserts (safe) casts automatically when necessary:
    val l = 1L + 3 // Long + Int => Long
}

fun demo3(x: Any) {
    // x is auto cast to  String on right-hand-size of ||
    if (x !is String || x.length == 0) return
}


fun demo4(x: Any) {
    // x is auto cast to  String on right-hand-size of &&
    if (x is String && x.length > 0) return
}

fun demo5(x: Any) {
    when (x) {
        is String -> println("string:$x")
        is Int -> print(x + 1)
        is IntArray -> println(x.sum())
    }
}

// Smart Cast, END

// Unsafe cast,START
fun unsafe_cast() {
    // x can be null / not null
    val x: String? = "abc"
    val x2: String = "abc"
    val x3: String? = null
    run {

        // Wrong : Nullable/NonNullable to NonNullable
        val y: String = x as String
        val y2: String = x2 as String
        // val y3: String = x3 as String // Error : null cannot be cast to non-null type kotlin.String
    }

    run {
        // Should : Nullable to Nullable
        // Use the nullable type on the right-hand side of the cast
        val y: String? = x as String?
        val y2: String? = x2 as String?
        val y3: String? = x3 as String?
    }

}
// Unsafe cast,END

// Safe cast,START
fun safe_cast() {
    // x can be null / not null
    val x: String? = "abc"
    val x2: String = "abc"
    val x3: String? = null
    run {
        // return null on failure
        val y: String? = x as? String
        val y2: String? = x2 as? String
        val y3: String? = x3 as? String
    }
}

// Safe cast,END

// Generics type casts,START
fun generics_type_casts() {
    // TODO:
}
// Generics type casts,end

