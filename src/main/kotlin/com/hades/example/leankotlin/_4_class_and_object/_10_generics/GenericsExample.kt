package com.hades.example.leankotlin._4_class_and_object._10_generics

// https://www.jianshu.com/p/c5ef8b30d768
fun main() {
    /**
     * Example 1 : Generics: in, out, where
     */
    run {
        // 泛型接口
        // 略

        // 泛型方法
        val color = Color<Double>()
        color.printColor(2.5)

        // 泛型字段
        //        val box = Box<Int>(10)
        val box = Box(1);    // 等价
        println(box.value)
        box.print()

        // 泛型方法

    }

    /**
     * Example 2 : Variance
     */
    run {
        // 对于 out 泛型，子类泛型对象可以赋值给父类泛型对象
        val production: Production<Food> = FoodStore()
        production.produce()    // FoodStore produce food
        val production2: Production<Food> = FastFoodStore()
        production2.produce()   // FastFoodStore produce FastFood
        val production3: Production<Food> = InOutBurger()
        production3.produce()// InOutBurger produce Burger

        // 对于 in 泛型，父类泛型对象可以赋值给子类泛型对象
        // 美国的汉堡的消费者既是现代人，更是人类。
        val consumer1: Consumer<Burger> = EveryBody()
        consumer1.consume(Burger()) // EveryBody eat food
        val consumer2: Consumer<Burger> = ModernPeople()
        consumer2.consume(Burger())     // ModernPeople eat FastFood
        val consumer3: Consumer<Burger> = American()
        consumer3.consume(Burger())     // American eat Burger

        val consumer1_1 : Consumer<Food> = EveryBody()
//        val consumer2_1 : Consumer<Food> = ModernPeople()  // Error
//        val consumer3_1 : Consumer<Food> = American()      // Error
    }

    /**
     * Example 3 : Type projections
     */

    /**
     * Example 4 : Generics: in, out, where
     */

    /**
     * Example 5 : Generic functions
     *
     */

    /**
     * Example 6 : Generic constraints
     */

    /**
     * Example 7 : Definitely non-nullable types
     */

    /**
     * Example 8 : Type erasure
     */

    /***
     * Example 9 : Underscore operator for type arguments
     */
}

/**
 * Example 1 : Generics: in, out, where
 */
// Classes in Kotlin can have type parameters, just like in Java:

// 泛型接口
interface Drinks<T> {
    fun price(t: T)
}

// 泛型方法
class Color<T> {
    fun printColor(t: T) {
        println(t.toString())
    }
}

// 泛型字段
class Box<T>(t: T) {
    val value = t
    fun print() {
        println(value.toString())
    }
}

// 泛型方法
fun <T> fromJson(json: String, tClass: Class<T>): T? {
    return tClass.getDeclaredConstructor().newInstance()
}


/**
 * Example 2 : Variance
 * https://www.jianshu.com/p/c5ef8b30d768
 */

// In Kotlin, there is a way to explain this sort of thing to the compiler. This is called declaration-site variance: you can annotate the type parameter T of Source to make sure that it is only returned (produced) from members of Source<T>, and never consumed.

/**
 * out : 类或接口将泛型作为内部方法的返回
 * produce = output = out
 * 看成Java的上界通配符, void sumOfList(List<? extends Number> list)。 子类泛型对象可以赋值给父类泛型对象
 */
interface Production<out T> {
    fun produce(): T
}

open class Food
open class FastFood : Food()
open class Burger : FastFood()
class FoodStore : Production<Food> {
    override fun produce(): Food {
        println("FoodStore produce food")
        return Food()
    }
}

class FastFoodStore : Production<FastFood> {
    override fun produce(): FastFood {
        println("FastFoodStore produce FastFood")
        return FastFood()
    }
}

class InOutBurger : Production<Burger> {
    override fun produce(): Burger {
        println("InOutBurger produce Burger")
        return Burger()
    }
}

/**
 * in : 类或接口将泛型对象作为函数的参数
 * consume = input = in
 * 看成Java的下界通配符。父类泛型对象可以赋值给子类泛型对象
 */
interface Consumer<in T> {
    fun consume(item: T)
}

class EveryBody : Consumer<Food> {
    override fun consume(item: Food) {
        println("EveryBody eat food")
    }
}

class ModernPeople : Consumer<FastFood> {
    override fun consume(item: FastFood) {
        println("ModernPeople eat FastFood")
    }
}

class American : Consumer<Burger> {
    override fun consume(item: Burger) {
        println("American eat Burger")
    }
}

/**
 * Invariant
 */
// Invariant: 将泛型作为函数参数，又将泛型作为函数的输出，那么既不用 in 或 out。
interface ProductionConsumer<T> {
    fun produce(): T
    fun consume(item: T)
}

/**
 * Example 3 : Type projections
 */

/**
 * Example 4 : Generics: in, out, where
 */

/**
 * Example 5 : Generic functions
 *
 */

/**
 * Example 6 : Generic constraints
 */

/**
 * Example 7 : Definitely non-nullable types
 */

/**
 * Example 8 : Type erasure
 */

/***
 * Example 9 : Underscore operator for type arguments
 */