package com.hades.example.leankotlin._2_type_checks_and_casts

fun main() {
//    checkType()
//    smartCasts()
    unsafe_cast()
    safe_cast()
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

// Generics type checks and casts,START
fun generics_type_checks_and_casts() {
    // TODO:  
}
// Generics type checks and casts,end
