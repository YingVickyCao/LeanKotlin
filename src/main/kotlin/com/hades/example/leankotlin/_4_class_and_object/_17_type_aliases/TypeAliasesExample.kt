package com.hades.example.leankotlin._4_class_and_object._17_type_aliases

// 类型别名
// https://kotlinlang.org/docs/type-aliases.html
// https://book.kotlincn.net/text/type-aliases.html

// 类型别名为现有类型提供替代名称。
// 如果类型名称太长，你可以另外引入较短的名称，并使用新的名称替代原类型名。

// 开发者手动声明一个类型别名，编译器再自动替换回原类型。那么，类型别名的意义？代码可读性

// 类型别名与原始类型，是等价的
typealias Name = String

// 为范型类型提供一个较短的名称 或 有意义的类型
typealias NumTable<K> = MutableMap<K, MutableList<Int>>

// 为函数类型提供别名
typealias MyHandler = (Int, String, Any) -> Unit
typealias Predicate<T> = (T) -> Boolean

fun foo(p: Predicate<Int>) = p(42)

// 为内部类和嵌套类创建新名称
class A {
    class Inner
}

class B {
    class Inner
}

typealias AInner = A.Inner
typealias BInner = B.Inner

fun main() {
    val name: Name = "java"
    println(name) // Java

//    val mutableMap = mutableMapOf<String, MutableList<Int>>()
//    mutableMap.put("k1", mutableListOf<Int>(1, 23, 4))

    val mutableMap: NumTable<String> = mutableMapOf()
    mutableMap.put("k1", mutableListOf<Int>(1, 23, 4))

    val f: (Int) -> Boolean = { it > 0 }
    println(foo(f))

    val p: Predicate<Int> = { it > 0 }
    println(p(10)) // true
    println(p(-5)) // false
    println(listOf<Int>(-1, 2, 5).filter(p))  // 2, 5

    val aInner: AInner = AInner()
}
