package com.hades.example.leankotlin.concepts._1_types._2_data_type._4_type_checks_and_casts

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
    safe_case_using_as()
    safe_case_using_check_null()
}

fun safe_case_using_as() {
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

fun safe_case_using_check_null() {
    // 3 空安全类型
    // (1) non-null type,  不能赋值 null
    var nameOfNon_null: String = "Vicky"
    // compile error : Null can not be a value of a non-null type String
//    val nameOfNon_null: String = null

    // (2) nullable type (null / not null)
    var nameOfNullable: String? = null

    // (3) non-null type  cannot= nullable type, but can use !! to force convert nullable type to non-null type
    /**
     * compile error :Type mismatch.
    Required: String
    Found:    String?
     */
//    nameOfNon_null = nameOfNullable

    // !! 表示我已经检查过right value 非空了，我要强制转换为non-null type
    /**
     * warning : Value of 'name2' is always null
     * error : Exception in thread "main" java.lang.NullPointerException
     */
//    nameOfNon_null = nameOfNullable!!      // wrong, TODO: java @Nonnull -> kotlin , pass function variable value = null

    if (null != nameOfNullable) {           // right
        nameOfNon_null = nameOfNullable!!
    }

    // (4) nullable type can= non-null type
    nameOfNullable = nameOfNon_null
}

// Safe cast,END

// Generics type casts,START
fun generics_type_casts() {
    // TODO:
}
// Generics type casts,end

