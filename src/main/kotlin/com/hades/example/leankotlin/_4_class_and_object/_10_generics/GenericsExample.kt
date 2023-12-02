package com.hades.example.leankotlin._4_class_and_object._10_generics

// https://book.kotlincn.net/text/generics.html
// https://kotlinlang.org/docs/generics.html
// https://www.jianshu.com/p/c5ef8b30d768
fun main() {
    /**
     * Example 1 : Generics: in, out, where
     * 泛型：in、out、where
     */
    run {

        // 泛型字段
        class Box<T>(t: T) {
            val value = t
            fun print() {
                println(value.toString())
            }
        }
        //        val box = Box<Int>(10)
        val box = Box(1);    // 等价
        println(box.value)
        box.print()


        // 泛型接口
        // 略

        // 泛型方法
        class Color<T> {
            fun printColor(t: T) {
                println(t.toString())
            }
        }

        val color = Color<Double>()
        color.printColor(2.5)


        // 泛型方法
        fun <T> fromJson(json: String, tClass: Class<T>): T? {
            return tClass.getDeclaredConstructor().newInstance()
        }
    }

    /**
     * Example 2 : Variance
     */
    run {
        // Java中 通配符类型参数  “? extends E” 表示可以可以接受E或E的一个子类型
        // 带 extends 限定（上界）的通配符类型使得类型是协变的（covariant）

        // Java中 通配符类型参数  “? super E” 表示可以可以接受E或E的一个父类型
        // 带 super 限定（下界）的通配符类型使得类型是逆变性（contravariance）
    }

    /**
     * Example 2 : Variance - Declaration-site variance
     * 声明处型变
     */
    run {
        // https://www.jianshu.com/p/c5ef8b30d768
        /**
         * Out
         * In Kotlin, there is a way to explain this sort of thing to the compiler. This is called declaration-site variance: you can annotate the type parameter T of Source to make sure that it is only returned (produced) from members of Source<T>, and never consumed.
         * out : 类或接口将泛型作为内部方法的返回
         * produce = output = out
         * 类Java的上界通配符“? extends E”
         */
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
        // 对于 out 泛型，子类泛型对象可以赋值给父类泛型对象
        val production: Production<Food> = FoodStore()
        production.produce()    // FoodStore produce food
        val production2: Production<Food> = FastFoodStore()
        production2.produce()   // FastFoodStore produce FastFood
        val production3: Production<Food> = InOutBurger()
        production3.produce()// InOutBurger produce Burger


        /**
         * in : 类或接口将泛型对象作为函数的参数
         * consume = input = in
         * 类似Java的下界通配符 “? super E”。
         */
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

        // 对于 in 泛型，父类泛型对象可以赋值给子类泛型对象
        // 美国的汉堡的消费者既是现代人，更是人类。
        val consumer1: Consumer<Burger> = EveryBody()
        consumer1.consume(Burger()) // EveryBody eat food
        val consumer2: Consumer<Burger> = ModernPeople()
        consumer2.consume(Burger())     // ModernPeople eat FastFood
        val consumer3: Consumer<Burger> = American()
        consumer3.consume(Burger())     // American eat Burger

        val consumer1_1: Consumer<Food> = EveryBody()
//        val consumer2_1 : Consumer<Food> = ModernPeople()  // Error
//        val consumer3_1 : Consumer<Food> = American()      // Error
    }

    /**
     * Example 3 : Type projections
     * 类型投影
     */

    /**
     * Example 3 : Type projections - Use-site variance: type projections
     * 使用处型变：类型投影
     */
    run {
        // 使用类型投影来限定参数，以避免不安全的操作

        // 使用out投影
        // 从一个数组复制到另一个数组
        // from 用out来它是一个受限制（投影）的数组
        // out修饰的参数只能读取，不能写入
        fun copy(from: Array<out Any>, to: Array<Any>) { // 相当于Java中 Array<? extends Any>。因此传入的from只能是Any以及其子类的array
            for (i in from.indices) {
                to[i] = from[i]
            }
        }

        val ints: Array<Int> = arrayOf(1, 2, 3)
        val any = Array<Any>(3) { "" }
        for (item in any) {
            print("$item ")
        }
        println()

        copy(ints, any)
        for (item in any) { // [1,2.3]
            print("$item ")
        }
        println()


        // 使用in投影
        // dest 用in来它是一个受限制（投影）的数组
        fun fill(dest: Array<in String>, value: String) { /// 相当于Java中Array<? super String>,因此传入的dest只能是string以及其父类的array
            dest.set(dest.size - 1, value)
        }

        val strings = Array<String>(4) { "" }
        fill(strings, "ab")
        for (item in strings) {
            print("$item ")
        }
        println()
    }

    /**
     * Example 3 : Type projections - Star-projections
     * TODO：星投影
     */
    run {
        // 星投影用来表示“不知道关于范型实数的任何信息”
        // 类似于Java中的无界操作符，Kotlin中使用 *
        // * 指所有类型，相当于Any？
        // 星号投影只能读取，不能写入
        fun printList(list: MutableList<*>) {
            println(list[0])
        }

        val list1 = mutableListOf<String>()
        list1.add("a")
        list1.add("ab")
        list1.add("de")
        val list2 = mutableListOf<Int>()
        list2.add(12)
        list2.add(56)
        printList(list1)
        printList(list2)
    }

    /**
     * Example 4 : Generic functions
     * 泛型函数
     */
    run {
        //  函数的类型参数，放在函数名称之前
        fun <T> singletonList(item: T): List<T> {
            val list = ArrayList<T>()
            list.add(item)
            return list
        }

        fun <T> T.basicToString(): String { //  扩展函数
            return "$this"
        }

        /// 调用泛型函数：在调用的函数名之后指定类型参数
//        val ints = singletonList<Int>(10)
        val ints = singletonList(10)    // 从上下文可有推断出来的类型参数，可以省略
        println(ints.toString())

        val intValue = 5;
        val stringValue = intValue.basicToString<Int>()
//        val stringValue = intValue.basicToString()    // 从上下文可有推断出来的类型参数，可以省略
        println(stringValue)
    }

    /**
     * Example 5 : Generic constraints
     * TODO:泛型约束
     */
    /**
     * Example 5 : Generic constraints - Upper bounds
     * TODO:上界
     */

    run {
        // 最常见的约束类型是上界。与Java中的extends关键字对应

        // : 之后指定的类型是上界，表示只有 Comparable<T> 的子类型可以替代T
        fun <T : Comparable<T>> sort(list: List<T>) {
            list.sorted()
        }
        sort(listOf<Int>(2, 6, 1)) //  ok 因为Int 是 Comparable<Int> 的子类型
        // sort(listOf<HashMap<Int, String>>()) // error: HashMap<Int, String> 不是 Comparable<T> 的子类

        // 默认的上界（即没有声明）是Any？。在<>中只能指定一个上界。如果同一类型参数需要多个上界，用单独的where 子句：
        fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String> where T : CharSequence, T : Comparable<T> {
            return list.filter { it > threshold }.map { it.toString() }
        } // 表示所传递的类型必须满足where子句的所有条件 ： T必须即是 即实现了CharSequence，又实现了Comparable。

        val strings = arrayListOf<String>("abc", "def", "xyz")
        val string2 = copyWhenGreater(strings, "d")
        println(string2) // [def, xyz]

    }

    /**
     * Example 6 : Definitely non-nullable types
     * TODO:定义非空类型
     */
    run {
        // 如何定义非空类型？ “& Any”

        // The most common use case for declaring definitely non-nullable types is when you want to override a Java method that contains @NotNull as an argument. F
        // When working only with Kotlin, it's unlikely that you will need to declare definitely non-nullable types explicitly because Kotlin's type inference takes care of this for you.
        run {
            class JBoxImpl : JBox {
                // IDEA默认生成的自动补全不正确：JBox中标记参数T为非空，但是IDEA生成的代码中参数为可空 Any?
                override fun <T : Any?> put(t: T & Any) {
                }
            }

            val jbox = JBoxImpl()
            jbox.put("abc")
            // jbox.put(null) // error： 不能传入null，因为有形参有“T & Any”修饰
        }

        run {
            class JBoxImpl : JBox {
                // 调整IDEA自动补全，修改为 Any? 为Any
                override fun <T : Any> put(t: T) {
                }
            }

            val jbox = JBoxImpl()
            jbox.put("abc")
            // jbox.put(null) // error： 不能传入null，因为有形参为非空T
        }

        run {
            class JBoxImpl : JBox {
                // 调整T 为 definitely non-nullable
                override fun <T> put(t: T & Any) {
                }
            }

            val jbox = JBoxImpl()
            jbox.put("abc")
            // jbox.put(null) // error : 仍然不能使用传入null
        }

    }

    /**
     * Example 7 : Type erasure
     * 类型擦除
     */

    run {
        // Kotlin 为泛型声明用法执行的类型安全检测在编译期进行。 运行时泛型类型的实例不保留关于其类型实参的任何信息。 其类型信息称为被擦除。例如，Foo<Bar> 与 Foo<Baz?> 的实例都会被擦除为 Foo<*>
    }

    /**
     * Example 7 : Type erasure - Generics type checks and casts
     * TODO:泛型类型检测与类型转换
     */

    run {

    }

    /**
     * Example 7 : Type erasure - Unchecked casts
     * TODO:非受检类型转换
     */
    run {

    }

    /***
     * Example 8 : Underscore operator for type arguments
     */
    run {
        // The underscore operator _ can be used for type arguments. Use it to automatically infer a type of the argument when other types are explicitly specified:
        class SomeImplementation : SomeClass<String>() {
            override fun execute(): String {
                return "test"
            }
        }

        class OtherImplementation : SomeClass<Int>() {
            override fun execute(): Int {
                return 42
            }
        }

        // T is inferred as String
        val s = Runner.run<SomeImplementation, _>()
        println(s)

        // T is inferred as Int
        val n = Runner.run<OtherImplementation, _>()
        println(n)
    }
}

/**
 * Example 1 : Generics: in, out, where
 */
// Classes in Kotlin can have type parameters, just like in Java:

// 泛型接口
interface Drinks<T> {
    fun price(t: T)
}

/**
 * Example 2 : Variance - Declaration-site variance
 *
 */
interface Production<out T> {
    fun produce(): T
}

interface Consumer<in T> {
    fun consume(item: T)
}

/**
 * Invariant: 将泛型作为函数参数，又将泛型作为函数的输出，那么既不用 in 或 out。
 */
interface ProductionConsumer<T> {
    fun produce(): T
    fun consume(item: T)
}

/***
 * Example 8 : Underscore operator for type arguments
 */
abstract class SomeClass<T> {
    abstract fun execute(): T
}

internal object Runner {
    inline fun <reified S : SomeClass<T>, T> run(): T {
        return S::class.java.getDeclaredConstructor().newInstance().execute()
    }
}