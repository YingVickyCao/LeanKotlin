package com.hades.example.leankotlin._4_class_and_object._14_object_expressions_and_declarations

import java.lang.reflect.Constructor

// object expressions and object declarations.
// 对象表达式与对象声明 : 需要创建一个对某个类做了轻微改动的类的对象，而不用为之显式声明新的子类
// https://book.kotlincn.net/text/object-declarations.html
// https://kotlinlang.org/docs/object-declarations.html


fun main() {
    test2()
    test2_1()
    test2_2()
    test2_3()
}

/**
 * Example 2 - Object declarations
 * 对象声明
 */
// 单例模式

// 对象DataProviderManager是一个单例。
// 对象声明的初始化过程是线程安全的并且在首次访问时进行。
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        allDataProviders.add(provider)
    }

    val allDataProviders: ArrayList<DataProvider> = arrayListOf()
}

class DataProvider {

}

fun test2() {
    // 如需引用该对象，直接使用其名称即可
    DataProviderManager.registerDataProvider(DataProvider())
}


// 对象可以有超类型
object DefaultListener : MouseAdapter() {
    override fun mouseClicked() {

    }
}
// 对象声明不能在局部作用域（即不能直接嵌套在函数内部），但是它们可以嵌套到其他对象声明或非内部类中。

/**
 * Example 2 - Object declarations - Data objects
 */
// 打印 a plain object declaration，得到是一个string 和 它的hash
object MyObject

// a data object declares a singleton
// 打印 a data object declaration，得到是一个string
// compiler自动生成方法：toString()、equals()/hashCode() pair，其后者不能自定义
data object MyDataObject {
    var x: Int = 3
}


// 比较时用==不用===
//  in the edge case where another object of the same type is generated at runtime (for example, by using platform reflection with java.lang.reflect or a JVM serialization library that uses this API under the hood),
//  this ensures that the objects are treated as being equal.
data object MySingleton

fun createInstanceViaReflection(): MySingleton {
    // Kotlin reflection does not permit the instantiation of data objects.
    // This creates a new MySingleton instance "by force" (i.e. Java platform reflection)
    // Don't do this yourself!
    return (MySingleton.javaClass.declaredConstructors[0].apply {
        isAccessible = true
    } as Constructor<MySingleton>).newInstance()
}


// Differences between data objects and data classes
// （1）data object no copy function
//  (2)no componentN() function

// Using data objects with sealed hierarchies
// data object declarations are a particularly useful for sealed hierarchies, like sealed classes or sealed interfaces, since they allow you to maintain symmetry with any data classes you may have defined alongside the object:
sealed interface ReadResult
data class Number(val number: Int) : ReadResult
data class Text(val text: String) : ReadResult
data object EndOfFile : ReadResult

fun printReadResult(result: ReadResult) {
    when (result) {
        is Number -> println("Num ${result.number}")
        is Text -> println("Text ${result.text}")
        is EndOfFile -> println("EOF")
    }
}

fun test2_1() {
    println(MyObject) // MyObject@7a79be86

    println(MyDataObject) // MyObject
    println(MyDataObject.toString()) // MyObject
    println(MyDataObject.hashCode()) // -1999451726
    MyDataObject.x = 100
    println(MyDataObject.hashCode()) // -1999451726

    // 比较时用==不用===
    val evilTwin = createInstanceViaReflection()
    println(evilTwin)   // MySingleton
    println(MySingleton)    // MySingleton
    println(MySingleton == evilTwin) // true
    println(MySingleton === evilTwin) // false
    // The generated hashCode() function has behavior that is consistent with the equals() function, so that all runtime instances of a data object have the same hash code
    println(MySingleton.hashCode()) // -626139966
    println(evilTwin.hashCode())    // -626139966
    println(evilTwin.equals(MySingleton))    // true

    // Using data objects with sealed hierarchies
    printReadResult(EndOfFile)
}


/**
 * Example 2 - Object declarations - Companion objects
 * 伴生对象
 */

// 类内部的对象声明可以用 companion 关键字标记：
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }

    fun print() {
        println("MyClass")
    }
}

// companion object 相当于  factory method
/*
 public final class MyClass {
   @NotNull
   public static final Factory Factory = new Factory((DefaultConstructorMarker)null);

   public static final class Factory {
      @NotNull
      public final MyClass create() {
         return new MyClass();
      }

      private Factory() {
      }
      public Factory(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
 */

//  name of companion object 可以被省略,那么name 是Companion
class MyClass2 {
    companion object {
        fun create(): MyClass2 = MyClass2()
    }

    fun print() {
        println("MyClass2")
    }
}
// Class members can access the private members of the corresponding companion object.
// 其自身所用的类的名称（不是另一个名称的限定符）可用作对该类的伴生对象 （无论是否具名）的引用：

// 伴生对象还可以实现接口
interface Factory<T> {
    fun create(): T
}

class MyClass3 {
    companion object : Factory<MyClass3> {
        override fun create(): MyClass3 {
            return MyClass3()
        }
    }
}

fun test2_2() {
//    val myClass = MyClass.Factory.create(); //  名称Factory 可以省略
    val myClass = MyClass.create();
    myClass.print()

    val myClass2 = MyClass2.Companion.create() // 名称Companion可以省略
    myClass2.print()

    // 其自身所用的类的名称（不是另一个名称的限定符）可用作对该类的伴生对象 （无论是否具名）的引用：
    val x = MyClass     //  x代指伴生对象Factory的引用
    x.create()
    val y = MyClass2   // y代指伴生对象Companion的引用
    y.create()

    // 即使伴生对象的成员看起来像其他语言的静态成员，在运行时他们仍然是真实对象的实例成员
    // 伴生对象还可以实现接口
    val f: Factory<MyClass3> = MyClass3
    // 使用 @JvmStatic 注解，你可以将伴生对象的成员生成为真正的静态方法和字段。
}

/**
 * Example 2 - Object declarations - Semantic difference between object expressions and declarations
 * 对象表达式和对象声明之间的语义差异
 */
fun test2_3() {
    // 对象表达式 vs 对象声明
    // (1) 对象表达式是在使用他们的地方立即执行（及初始化）的。
    // (2) 对象声明是在第一次被访问到时延迟初始化的。
    // (3) 伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配 。
}