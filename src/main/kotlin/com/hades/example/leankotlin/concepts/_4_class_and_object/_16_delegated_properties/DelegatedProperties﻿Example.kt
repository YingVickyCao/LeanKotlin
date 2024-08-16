package com.hades.example.leankotlin.concepts._4_class_and_object._16_delegated_properties

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// 属性委托 (Delegated properties)
// https://kotlinlang.org/docs/delegated-properties.html
// https://book.kotlincn.net/text/delegated-properties.html


fun main() {
    test1()
    test2_1()
    test2_2()
    test3()
    test4()
    test5()
    test6()
    test7()
    test8()
    test9()
    test10()
}

/**
 * Example 1 : Delegated properties
 * TODO：委托属性 => 意义在哪？
 */

class Delegate {
    // 第一个参数是读出p的对象 Example，第二个参数是保存对p的描述
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    // 第一个参数是读出p的对象 Example，第二个参数是保存对p的描述, 第三个参数是保存given value
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef")
    }
}

class Example {
    // val/var <属性名>: <类型> by <表达式>。在 by 后面的表达式是该 委托
    // 因为的属性对应的get（与set）被委托给它的getValue和setValue方法。属性的委托不必实现接口，但是需要提供一个 getValue() 函数（对于 var 属性还有 setValue()）
    var p: String by Delegate()
}

fun test1() {
    val example = Example()
    // 因为属性p被委托给Delegate，当read p时，将调用Delegate的getValue方法
    println(example.p) // Example@6574b225, thank you for delegating 'p' to me!

    //  p 赋值时，将调用 setValue() 函数。
    example.p = "New"  // New has been assigned to 'p' in Example@7921b0a2
}


/**
 * Example 2 : Standard delegates
 * 标准委托
 */

// Kotlin 标准库为几种有用的委托提供了工厂方法。


/**
 * Example 2 : Standard delegates - Lazy properties
 * 延迟属性
 */

// lazy()：接受一个lambda并返回一个Lazy<T>实例的函数。返回的实例可以作为实现延迟属性的委托。
// 第一次调用get会执行传递给lazy()的lambda表达式并记录结果。后面调用get()只是返回记录的结果。
// TODO:默认情况下，对于 lazy 属性的求值是同步锁的（synchronized）：该值只在一个线程中计算，但所有线程都会看到相同的值。
//  LazyThreadSafetyMode.SYNCHRONIZED
//  LazyThreadSafetyMode.PUBLICATION  初始化委托的同步锁不是必需的，这样可以让多个线程同时执行
//  LazyThreadSafetyMode.NON : 非线程安全
val lazyStringValue: String by lazy {
    println("computed")
    "Hello"
}

fun test2_1() {
    println(lazyStringValue) // computed Hello
    println(lazyStringValue) //  Hello
    println(lazyStringValue) //  Hello
}

/**
 * Example 2 : Standard delegates - Observable properties
 */
// 可观察属性
// 赋值时，有三个参数： 被赋值的属性、旧值、新值
// 如果想截获赋值并否决它们，那么使用 vetoable() 取代 observable()。 在属性被赋新值之前会调用传递给 vetoable 的处理程序。
class User {
    var name: String by Delegates.observable("no name") { property, oldValue, newValue ->
        println("$oldValue,$newValue")
    }
}

class Person {
    // Delegates.observable
    var age: Int by Delegates.vetoable(20) { property, oldValue, newValue ->
        when {
            oldValue <= newValue -> true // 只有当新值 >=  旧值才可以成功赋值
            else -> false
        }
    }
}

fun test2_2() {
    val user = User()
    user.name = "first name"
    user.name = "second name"

    // Delegates.vetoable
    val person = Person()
    person.age = 15
    println(person.age)

    person.age = 24
    println(person.age)
}

/**
 * Example 3 : Delegating to another property
 */
// 委托给另一个属性

// 一个属性可以把它的 getter 与 setter 委托给另一个属性。
// 该委托属性可以为：
// (1)顶层属性
// (2)同一个类的成员或扩展属性
// (3)另一个类的成员或扩展属性

// 将一个属性委托给另一个属性，应在委托名称中使用 :: 限定符

var topLevelInt: Int = 3

class ClassWithDelegate(val anotherClassInt: Int)
class MyClass(var memberInt: Int, val anotherClassInstance: ClassWithDelegate) {
    var delegatedToTopMember: Int by this::memberInt
    var delegatedToTopLevel: Int by ::topLevelInt
    val delegatedToAnotherClass: Int by anotherClassInstance::anotherClassInt
}

// 扩展
var MyClass.extDelegated: Int by ::topLevelInt

// 以一种向后兼容的方式重命名一个属性的时候：引入一个新的属性、 使用 @Deprecated 注解来注解旧的属性、并委托其实现。
class MyClass2 {
    var newName: Int = 0

    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
    var oldName: Int by this::newName
}

fun test3() {
    val myClass = MyClass(10, ClassWithDelegate(5))
    println(myClass.delegatedToTopMember) // 10
    myClass.delegatedToTopMember = 20
    println(myClass.delegatedToTopMember) // 20

    println(topLevelInt)    // 3
    println(myClass.delegatedToTopLevel) // 3
    myClass.delegatedToTopLevel = 30
    println(topLevelInt)    // 30
    println(myClass.delegatedToTopLevel) // 30

    println(myClass.delegatedToAnotherClass) // 5

    println(myClass.extDelegated) // 30
    myClass.extDelegated = 10
    println(myClass.extDelegated) // 10
    println(topLevelInt)    // 10

    //  @Deprecated
    val myClass2 = MyClass2()
    myClass2.oldName = 42
    println(myClass2.oldName) // 42
    println(myClass2.newName) // 42
}

/**
 * Example 4 : Storing properties in a map
 * 将属性储存在映射中
 */
// 一个常见的用例是在一个映射（map）里存储属性的值。 这经常出现在像解析 JSON 或者执行其他“动态”任务的应用中。 在这种情况下，你可以使用映射实例自身作为委托来实现委托属性。

class User2(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

// 这也适用于 var 属性，把只读的 Map 换成 MutableMap
class User3(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int by map
}

fun test4() {
    // 在构造函数中接受一个映射参数
    val user = User2(mapOf("name" to "Doe", "age" to 25))
    println(user.name) // Doe
    println(user.age) // 25
}

/**
 * Example 5 : Local delegated properties
 * TODO:局部委托属性
 */
// 将局部变量声明为委托属性。 例如，使一个局部变量惰性初始化：
class Foo {
    fun isValid(): Boolean {
        return true
    }

    fun doSomething() {

    }
}

fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    val someCondition = false
    // memoizedFoo 变量只会在第一次访问时计算。 如果 someCondition 失败，那么该变量根本不会计算。
    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}

fun test5() {

}

/**
 * Example 6 : Property delegate requirements
 * TODO: 属性委托要求 ； 方法的变量委托其他委托类。
 */

// 对于一个只读属性（即 val 声明的），委托必须提供一个操作符函数 getValue()，该函数具有以下参数：
// (1) thisRef 必须与属性所有者类型（对于扩展属性必须是被扩展的类型）相同或者是其超类型。
// (2) property 必须是类型 KProperty<*> 或其超类型。
// getValue() 必须返回与属性相同的类型（或其子类型）。

class Resource
class Owner {
    val resource: Resource by ResourceDelegate()
}

class ResourceDelegate {
    operator fun getValue(thisRef: Owner, property: KProperty<*>): Resource {
        println("ResourceDelegate, init Resource")
        return Resource()
    }
}

// 对于一个可变属性（即 var 声明的），委托必须额外提供一个操作符函数 setValue()， 该函数具有以下参数：
// (1)thisRef 必须与属性所有者类型（对于扩展属性必须是被扩展的类型）相同或者是其超类型。
// (2)property 必须是类型 KProperty<*> 或其超类型。
// (3)value 必须与属性类型相同（或者是其超类型）。

class Resource2
class Owner2 {
    var resource: Resource2 by ResourceDelegate2()
}

class ResourceDelegate2(private var resource: Resource2 = Resource2()) {
    operator fun getValue(thisRef: Owner2, property: KProperty<*>): Resource2 {
        println("ResourceDelegate2, get Resource")
        return resource
    }

    operator fun setValue(thisRef: Owner2, property: KProperty<*>, value: Any?) {
        println("ResourceDelegate2, set Resource")
        if (value is Resource2) {
            resource = value
        }
    }
}

//  create delegates as anonymous objects without creating new classes, by using the interfaces ReadOnlyProperty and ReadWriteProperty from the Kotlin standard library. They provide the required methods: getValue() is declared in ReadOnlyProperty; ReadWriteProperty extends it and adds setValue().
//  This means you can pass a ReadWriteProperty whenever a ReadOnlyProperty is expected.
fun resourceDelegate(resource: Resource2 = Resource2()): ReadWriteProperty<Any?, Resource2> =
    object : ReadWriteProperty<Any?, Resource2> {
        var curValue = resource
        override fun getValue(thisRef: Any?, property: KProperty<*>): Resource2 {
            return curValue
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Resource2) {
            curValue = value
        }
    }

fun test6() {
    // val 属性的委托
    val owner = Owner()
    owner.resource  // 每次访问 owner.resource时，ResourceDelegate都会 new Resource
    owner.resource  // 每次访问 owner.resource时，ResourceDelegate都会 new Resource

    // var 属性的委托
    val owner2 = Owner2()
    owner2.resource

    // create delegates as anonymous objects without creating new classes
    val readOnlyResource: Resource2 by resourceDelegate() // ReadWriteProperty as val
    var readWriteResource: Resource2 by resourceDelegate()
}

/**
 * Example7 : Translation rules for delegated properties
 * TODO:Translation rules for delegated properties
 */
fun test7() {

}

/**
 * Example7 : Translation rules for delegated properties - Optimized cases for delegated properties
 * TODO:Optimized cases for delegated properties
 */
fun test8() {

}

/**
 * Example7 : Translation rules for delegated properties - Translation rules when delegating to another property
 * TODO:Translation rules when delegating to another property
 */
fun test9() {

}

/**
 * Example 8 : Providing a delegate
 * TODO:Providing a delegate
 */
fun test10() {

}