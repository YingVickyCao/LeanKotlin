package com.hades.example.leankotlin._1_basic_types

fun main() {
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