package com.hades.example.leankotlin.concepts._4_class_and_object._13_inline_value_classes

//  内联类
// https://book.kotlincn.net/text/inline-classes.html
// https://kotlinlang.org/docs/inline-classes.html
fun main() {
    /**
     * Example 1 : Inline value classes
     * 内联类
     */
    run {
        test1()
    }

    /**
     * Example 2 : Members
     */

    run {
        test2()
    }
    /**
     * Example 3 : Inheritance
     */
    run {
        test3()
    }

    /**
     * Example 4 : Representation
     */

    test4_1()

    /**
     * Example 4 : Representation - Mangling
     */
    test4_2()

    /**
     * Example 4 : Representation - Calling from Java code
     */
    test4_3()

    /**
     * Example 5 : Inline classes vs type aliases
     */
    test5()

    /**
     * Example 6 : Inline classes and delegation
     */
    test6()
}

/**
 * Example 1 : Inline value classes
 * 内联类
 */
// value 和 @JvmInline 来表示标识一个内联类
@JvmInline
value class Password(private val s: String)

//  内联类必须含有唯一的一个属性在主构造器中初始化。在运行时，将使用这个唯一属性来表示内联类的实例。

fun test1() {
    // 内联类的使用场景：wrap a value in a class to create a more domain-specific type. 若被包装的是原生类型，那么同时又不会丧失运行时对原始类型的优化因此不会有性能损失。
    // 在运行时，不存在Password类的真实实例对象，securePassword仅仅包含 string
    val securePassword = Password("Don't try this in production")
    println(securePassword)
    // 内联类的主要特性：类的数据被内联到该类使用的地方。类似于内联函数中的代码被内联到该函数调用的地方。
}

/**
 * Example 2 : Members
 */

// 内联类支持普通类中的一些功能，比如属性、函数、init block、secondary constructors
// Inline class properties cannot have backing fields. They can only have simple computable properties (no lateinit/delegated properties).
@JvmInline
value class Person(private val fullName: String) {
    init {
        require(fullName.isNotEmpty()) {
            "Full name should not be empty"
        }
    }

    constructor(firstName: String, lastName: String) : this("$firstName $lastName") {
        require(lastName.isNotEmpty()) {
            "Last name should not be empty"
        }
    }

    val length: Int
        get() = fullName.length

    fun greet() {
        println("Hi, $fullName")
    }
}

fun test2() {
    val name1 = Person("Kotlin", "Mascot")
    val name2 = Person("Kode")
    name1.greet()
    println(name2.length)
}


/**
 * Example 3 : Inheritance
 * 继承
 */
// 内联类允许去继承接口
interface Printable {
    fun prettyPrint(): String
}

@JvmInline
value class Name(val s: String) : Printable {
    override fun prettyPrint(): String {
        return "Let's $s!"
    }
}
//final class A :Name(){ // 内联类是final类，不能被其他类继承。
//
//}

fun test3() {
    val name = Name("Kotlin")
    println(name.prettyPrint())
}

/**
 * Example 4 : Representation
 * 表示方式
 */
// In generated code, the Kotlin compiler keeps a wrapper for each inline class.
// 内联类的实例在运行时表示为wrappers或者基础类型。类似与Int可以表示为原生类型int或wrapper Integer

// 为了生成性能最优的代码，Kotlin 编译更倾向于使用基础类型而不是包装器。 然而，有时候使用包装器是必要的。一般来说，只要将内联类用作另一种类型， 它们就会被装箱。
interface I

@JvmInline
value class Foo(val i: Int) : I {
}

fun asInline(f: Foo) {

}

fun <T> asGeneric(x: T) {

}

fun asInterface(i: I) {
}

fun asNullable(i: Foo?) {

}

fun <T> id(x: T): T = x


fun test4_1() {
    val f = Foo(42)
    asInline(f)     // 拆箱：用作Foo本身
    asGeneric(f)    // 装箱：用作泛型类型T
    asInterface(f)  // 装箱：用作泛型类型I
    asNullable(f)   // 装箱: 用作不同Foo的可空类型 Foo？

    // 'f' 首先会被装箱（当它作为参数传递给 'id' 函数时）然后又被拆箱（当它从'id'函数中被返回时）
    // 最后， 'c' 中就包含了被拆箱后的内部表达(也就是 '42')， 和 'f' 一样
    val c = id(f)
}
// 因为内联类既可以表示为基础类型有可以表示为包装器，引用相等(===/!==)对于内联类而言毫无意义，因此这也是被禁止的。
// https://book.kotlincn.net/text/equality.html

// Inline classes can also have a generic type parameter as the underlying type. In this case, the compiler maps it to Any? or, generally, to the upper bound of the type parameter.
@JvmInline
value class UserId<T>(val value: T)

fun compute(s: UserId<String>) {} // compiler generates fun compute-<hashcode>(s: Any?)


/**
 * Example 4 : Representation - Mangling
 * 名字修饰
 */

// 由于内联类被编译为其基础类型，因此可能会导致各种模糊的错误，例如意想不到的平台签名冲突：

@JvmInline
value class UInt(val x: Int)

// 在 JVM 平台上被表示为'public final void compute(int x)'
fun compute(x: Int) {}

// 同理，在 JVM 平台上也被表示为'public final void compute(int x)'！
fun compute(x: UInt) {}

// 为了缓解这种问题，一般会通过在函数名后面拼接一些稳定的哈希码来修饰函数名。 因此，fun compute(x: UInt) 将会被表示为 public final void compute-<hashcode>(int x)，以此来解决冲突的问题。
fun test4_2() {

}

/**
 * Example 4 : Representation - Calling from Java code
 */
// You can call functions that accept inline classes from Java code. To do so, you should manually disable mangling: add the @JvmName annotation before the function declaration:

@JvmInline
value class UInt2(val x: Int)

// Java : :InlineValueClassesExampleKt.compute2(10);
fun compute2(x: Int) {}

// InlineValueClassesExampleKt.computeUInt(10);
@JvmName("computeUInt") // 如果不加@JvmName，那么，Java能看到两个compute2，Java不能match到底是调用哪一个。
fun compute2(x: UInt2) {
    // 相当于因为Java看到重复的函数签名，然后用@JvmName给其中一个指定一个别的名字。
}

fun test4_3() {
    compute2(10)
    val value = UInt2(10)
    compute2(value)
}

/**
 * Example 5 : Inline classes vs type aliases
 * 内联类与类型别名
 */
// 内联类 vs 类型别名（https://book.kotlincn.net/text/type-aliases.html）
// 内联类是引入了一个真实的新类型
// 类型别名是为现有的类型取了一个新的替代名称（别名）
typealias NameTypeAlias = String

@JvmInline
value class NameInlineClass(val s: String)

fun acceptString(s: String) {

}

fun acceptNameTypeAlias(n: NameTypeAlias) {

}

fun acceptNameInlineClass(p: NameInlineClass) {}


fun test5() {
    val nameTypeAlias: NameTypeAlias = "abc"
    val nameInlineClass: NameInlineClass = NameInlineClass("abc")
    val string: String = "abc"

    acceptString(nameTypeAlias) // ok
//    acceptString(nameInlineClass) // error
    acceptString(string)    // ok

    acceptNameTypeAlias(string)     // ok
    acceptNameTypeAlias(nameTypeAlias) // ok
//    acceptNameTypeAlias(nameInlineClass) // error

//    acceptNameInlineClass(nameTypeAlias) // error
    acceptNameInlineClass(nameInlineClass) // ok
//    acceptNameInlineClass(string) // error

}

/**
 * Example 6 : Inline classes and delegation
 * TODO: Inline classes and delegation
 */
// Implementation by delegation to inlined value of inlined class is allowed with interfaces:
interface MyInterface {
    fun bar()
    fun foo() = "f00"
}

@JvmInline
value class MyInterfaceWrapper(private val myInterface: MyInterface) : MyInterface by myInterface // TODO: by

fun test6() {
    val my = MyInterfaceWrapper(object : MyInterface {
        override fun bar() {
            println("bar")
        }
    })
    println(my.foo())
}


