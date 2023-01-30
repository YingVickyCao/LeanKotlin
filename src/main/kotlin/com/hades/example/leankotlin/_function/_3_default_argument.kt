package com.hades.example.leankotlin

/**
 * 函数 - Default param value
 */
fun main() {
//    printString("Jerry")
//    printString("Jerry", "56")
//
//    var b = ByteArray(5);
//    read(b, 1)

    var aObject = A();
//    aObject.foo() // 10
//    aObject.foo(100) // 100

    var bObject = B();
    bObject.foo() // 10
    bObject.foo2()
}

fun printString(message: String, prefix: String = "123") {
    println("$message,$prefix")
}

/**
 * Default param : off, len
 */
fun read(b: ByteArray, off: Int = 0, len: Int = b.size) {
    println(b.get(off + 1))
}


open class A {
    open fun foo(i: Int = 10) {
        println(i)
    }

    fun foo2(i: Int = 10) {
        println(i)
    }
}

class B : A() {
    override fun foo(i: Int) {
        println(i)
    }
}