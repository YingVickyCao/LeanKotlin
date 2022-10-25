package com.hades.example.leankotlin

// Data  : 常量 val、变量 var

/**
 * Data type
 * 1 For constant and variable ?
 * val means value.
 * This is a read-only value.
 *
 * 2 自动类推断
 *
 * var means variable.
 * This is a mutable value.
 *
 * 2 Kotlin's data type is null safe
 *
 */

fun main() {
    // 1 Variable
    var age: Int = 18
    val num = 10
    // Compile error : Val cannot be reassigned
    // num = 20

    // 2 自动类型推断
    var age2 = 18

    // 3 空安全类型
    // (1) non-null type 不能赋值 null
    var nameOfNon_null: String = "Vicky"      // non-null
    // compile error : Null can not be a value of a non-null type String
//    val nameOfNon_null: String = null

    // (2) nullable type
    var nameOfNullable: String? = null       // nullable (null / not null)

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