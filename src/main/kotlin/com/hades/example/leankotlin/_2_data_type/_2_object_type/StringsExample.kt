package com.hades.example.leankotlin._2_data_type._2_object_type

// String is immutable
fun main() {
//    defineString()
//    loopString()
//    immutable()
//    concat()
    stringLiterals()
    stringTemplate()
}

fun defineString() {
    run {
        var str: String = "abc 123"
        println(str)
    }

    run {
        var str = "abc 123"
        println(str)
    }
}

fun loopString() {
    var str = "a1"
    for (c in str) {
        println(c)
    }
}

fun immutable() {
    var str = "a1";
    println(str.uppercase()) // str.uppercase() : create a new String object
    println(str)
}

// +
// $
fun concat() {
    val s1 = "s1"
    val s2 = "ab"
    val s = s1 + s2
    println(s)
}

fun stringLiterals() {
    // Escaped string: 特殊字符以\开头
    var escapedString = "Hello, \nworld"
    println(escapedString)

    // Raw string

    val rawString = """
    this is a
    an example for raw string
    """

    val rawString2 = """
        |this is a
            |an example for raw string
    """
    println(rawString2)
    println(rawString2.trimMargin()) // trimMargin 默认来移除|开头

    val rawString3 = """
        >this is a
            >an example for raw string
    """
    println(rawString3)
    println(rawString3.trimMargin(">")) // trimMargin 指定移除>开头
}

fun stringTemplate() {
    val s = "hi"
    println("print string : $s")    // print string : hi
    println("$s.length is ${s.length} ")    // hi.length is 2

    //  {$}_9.99
    val price = """
    {$}_9.99
    """
    println(price)

    // $_9.99
    val price2 = """
    ${'$'}_9.99
    """
    println(price2)
}