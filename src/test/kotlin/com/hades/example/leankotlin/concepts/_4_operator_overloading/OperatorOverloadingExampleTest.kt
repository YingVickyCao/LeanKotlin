package com.hades.example.leankotlin.concepts._4_operator_overloading
import kotlin.test.*

// https://kotlinlang.org/docs/operator-overloading.html#augmented-assignments
// https://www.yiibai.com/kotlin/kotlin-operator.html
// https://book.kotlincn.net/text/operator-overloading.html
// https://juejin.cn/post/7087129138070290463
// 操作符
// 操作符重载
class OperatorOverloadingExampleTest {
    /**
     * Unary operations - Unary prefix operators
     * 一元操作 - 一元前缀操作符
     */
    @Test
    fun test_UnaryPrefixOperator() {
        run {
            val a = 10
            assertEquals(10, +a)
            assertEquals(10, a.unaryPlus())
            assertEquals(-10, -a)
            assertEquals(-10, a.unaryMinus())
        }

        run {
            val a = -10
            assertEquals(-10, +a)
            assertEquals(-10, a.unaryPlus())
            assertEquals(10, -a)
            assertEquals(10, a.unaryMinus())
        }

        run {
            val flag = true
            assertEquals(false, !flag)
            assertEquals(false, flag.not())
        }
    }

    /**
     * 一元前缀操作符重载
     */
    @Test
    fun test_UnaryPrefixOperator_Operator_Overloading() {
        data class Point(var x: Int, var y: Int)

        operator fun Point.unaryMinus(): Point {
            return Point(-x, -y)
        }

        operator fun Point.unaryPlus(): Point {
            return Point(+x, +y)
        }

        run {
            val point = Point(-10, -20)
            val result = +point
            assertEquals(-10, result.x)
            assertEquals(-20, result.y)
        }

        run {
            val point = Point(10, 20)
            val result = -point
            assertEquals(-10, result.x)
            assertEquals(-20, result.y)
        }
    }

    /**
     * Unary operations - Increments and decrements
     * 一元操作 - 递增与递减
     */
    @Test
    fun test_Increments_and_decrements() {
        run {
            var a = 5
            val result = ++a
            assertEquals(6, result)
            assertEquals(6, a)
        }

        run {
            var a = 5
            val result = a++
            assertEquals(5, result)
            assertEquals(6, a)
        }

        run {
            var a = 5
            val result = --a
            assertEquals(4, result)
            assertEquals(4, a)
        }

        run {
            var a = 5
            val result = a--
            assertEquals(5, result)
            assertEquals(4, a)
        }
    }

    @Test
    fun test_Increments_and_decrements_Overloading() {
        // They shouldn't mutate the object on which the inc or dec was invoked.
        data class Point(var x: Int)

        //a++
        operator fun Point.inc(): Point {
            return Point(x.inc())
        }

        // a--
        operator fun Point.dec(): Point {
            return Point(x.dec())
        }

        run {
            var a = Point(5)
            val result = ++a
            assertEquals(6, result.x)
            assertEquals(6, a.x)
        }

        run {
            var a = Point(5)
            val result = --a
            assertEquals(4, result.x)
            assertEquals(4, a.x)
        }

        run {
            var a = Point(5)
            val result = a--
            assertEquals(5, result.x)
            assertEquals(4, a.x)
        }
    }


    /**
     * Binary operations - Arithmetic operators
     * 二元操作 - 算术运算符
     */
    @Test
    fun test_ArithmeticOperators() {
        val a = 10
        val b = 5
        val c = 7
        val d = 2
        assertEquals(15, a + b)
        assertEquals(15, a.plus(b))

        assertEquals(5, a - b)
        assertEquals(5, a.minus(b))

        assertEquals(50, a * b)
        assertEquals(50, a.times(b))

        assertEquals(2, a / b)
        assertEquals(2, a.div(b))

        assertEquals(0, a % b)
        assertEquals(0, a.rem(b))

        assertEquals(1, c % d)
        assertEquals(1, c.rem(d))
    }


    @Test
    fun test_ArithmeticOperators_Overloading() {
        data class Point(var x: Int) {
            operator fun plus(p: Point): Point {
                return Point(x + p.x)
            }

            operator fun minus(p: Point): Point {
                return Point(x - p.x)
            }

            operator fun times(p: Point): Point {
                return Point(x * p.x)
            }

            operator fun div(p: Point): Point {
                return Point(x / p.x)
            }

            operator fun rem(p: Point): Point {
                return Point(x % p.x)
            }
        }

        val a = Point(10)
        val b = Point(5)
        val c = Point(7)
        val d = Point(2)
        assertEquals(15, (a + b).x)
        assertEquals(15, (a.plus(b)).x)

        assertEquals(5, (a - b).x)
        assertEquals(5, (a.minus(b)).x)

        assertEquals(50, (a * b).x)
        assertEquals(50, (a.times(b)).x)

        assertEquals(2, (a / b).x)
        assertEquals(2, (a.div(b)).x)

        assertEquals(0, (a % b).x)
        assertEquals(0, (a.rem(b)).x)

        assertEquals(1, (c % d).x)
        assertEquals(1, (c.rem(d)).x)
    }

    /**
     * Binary operations - in operator x
     * 二元操作 - in 操作符
     */
    @Test
    fun test_in_operator() {
        val data: Array<Int> = arrayOf(1, 10)
        assertTrue(1 in data)
        assertFalse(2 in data)
        assertTrue(2 !in data)
    }

    @Test
    fun test_in_operator_Overloading() {
        data class Points(val ints: Array<Int>) {
            operator fun contains(x: Int): Boolean {
                return ints.contains(x)
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Points

                return ints.contentEquals(other.ints)
            }

            override fun hashCode(): Int {
                return ints.contentHashCode()
            }
        }

        val data = Points(arrayOf(1, 10))
        assertTrue(1 in data)
        assertFalse(2 in data)
        assertTrue(2 !in data)
    }

    /**
     * Binary operations - Indexed access operator
     * 二元操作 - 索引访问操作符
     */
    @Test
    fun test_Indexed_access_operator() {
        val a = arrayOf(1, 2, 3)
        assertEquals(2, a[1])
        a[1] = 10
        assertEquals(10, a[1])
    }

    @Test
    fun test_Indexed_access_operator_Overloading() {
        data class Points(val ints: Array<Int>) {
            operator fun get(index: Int): Int {
                return ints[index]
            }

            operator fun set(index: Int, value: Int) {
                ints[index] = value
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Points

                return ints.contentEquals(other.ints)
            }

            override fun hashCode(): Int {
                return ints.contentHashCode()
            }
        }

        val a = Points(arrayOf(1, 2, 3))
        assertEquals(2, a[1])
        a[1] = 10
        assertEquals(10, a[1])
    }

    /**
     * Binary operations - invoke operator
     * 二元操作 - invoke 操作符
     */
    @Test
    fun test_invoke_operator() {
        fun sum(n1: Int, n2: Int, callback: (Int, Int) -> Int): Int {
            return callback.invoke(n1, n2)
        }

        val result = sum(1, 3, callback = { n1, n2 ->
            n1 + n2
        })
        assertEquals(4, result)
    }

    @Test
    fun test_invoke_operator_Overloading() {
        // https://www.jianshu.com/p/947cda6e5e06
        data class Stu(val name: String, val age: Int) {
            operator fun invoke(): String { //  重载invoke运算符
                return "$name - $age"
            }

            operator fun invoke(age: Int): Boolean { //  重载invoke运算符
                return age > 10
            }
        }

        val stu = Stu("SY", 24)
        assertEquals("SY - 24", stu.invoke()) // 正常调用
        assertEquals("SY - 24", stu())  // 约定后带调用
        assertEquals(true, stu.invoke(20))  // 正常调用
        assertEquals(true, stu(20)) // 约定后带调用

        class Sum {
            operator fun invoke(n1: Int, n2: Int): Int {
                return n1 + n2
            }
        }

        val sum = Sum()
        assertEquals(3, sum.invoke(1, 2)) // 正常调用
        assertEquals(3, sum(1, 2)) // 约定后带调用
    }

    /**
     * Binary operations - Augmented assignments
     * 二元操作 - 广义赋值
     */
    @Test
    fun test_AugmentedAssignments() {
        run {
            var a = 5
            val b = 10
            a += b
            assertEquals(15, a)
            assertEquals(15, 5.plus(10))
        }
        run {
            var a = 5
            val b = 10
            a -= b
            assertEquals(-5, a)
            assertEquals(-5, 5.minus(10))
        }

        run {
            var a = 5
            val b = 10
            a *= b
            assertEquals(50, a)
            assertEquals(50, 5.times(10))
        }

        run {
            var a = 11
            val b = 5
            a /= b
            assertEquals(2, a)
            assertEquals(2, 11.div(5))
        }

        run {
            var a = 11
            val b = 5
            a %= b
            assertEquals(1, a)
            assertEquals(1, 11.rem(5))
        }
    }

    @Test
    fun test_AugmentedAssignments_Overloading() {
        class IntValue(var n: Int) {
            operator fun plusAssign(p: IntValue) {
                n += p.n
            }

            operator fun minusAssign(p: IntValue) {
                n -= p.n
            }

            operator fun timesAssign(p: IntValue) {
                n *= p.n
            }

            operator fun divAssign(p: IntValue) {
                n /= p.n
            }

            operator fun remAssign(p: IntValue) {
                n %= p.n
            }
        }
        run {
            val a = IntValue(5)
            val b = IntValue(10)
            a += b
            assertEquals(15, a.n)

            val c = IntValue(5)
            val d = IntValue(10)
            c.plusAssign(d)
            assertEquals(15, c.n)
        }
        run {
            val a = IntValue(5)
            val b = IntValue(10)
            a -= b
            assertEquals(-5, a.n)

            val c = IntValue(5)
            val d = IntValue(10)
            c.minusAssign(d)
            assertEquals(-5, c.n)
        }

        run {
            val a = IntValue(5)
            val b = IntValue(10)
            a *= b
            assertEquals(50, a.n)

            val c = IntValue(5)
            val d = IntValue(10)
            c.timesAssign(d)
            assertEquals(50, c.n)
        }

        run {
            val a = IntValue(11)
            val b = IntValue(5)
            a /= b
            assertEquals(2, a.n)

            val c = IntValue(11)
            val d = IntValue(5)
            c.divAssign(d)
            assertEquals(2, c.n)
        }

        run {
            val a = IntValue(11)
            val b = IntValue(5)
            a %= b
            assertEquals(1, a.n)

            val c = IntValue(11)
            val d = IntValue(5)
            c.remAssign(d)
            assertEquals(1, c.n)
        }
    }

    /**
     * Binary operations - Equality and inequality operators
     * 二元操作 - 相等与不等操作符
     */
    @Test
    fun test_EqualityInequalityOperators() {
        val n1 = 5
        val n2 = 5
        val n3 = 10
        assertTrue(n1 == n2)
        assertTrue(n1.equals(n2))

        assertFalse(n1 == n3)
        assertFalse(n1.equals(n3))

        assertEquals(true, n1 != n3)
        assertEquals(true, !n1.equals(n3))
    }

    @Test
    fun test_EqualityInequalityOperators_Overloading() {
        class Point(val x: Int) {
            override operator fun equals(other: Any?): Boolean {
                if (this === other) {
                    return true
                }

                if (other !is Point) {
                    return false
                }
                return x == other.x
            }

            override fun hashCode(): Int {
                return 31 * x
            }
        }

        val n1 = Point(5)
        val n2 = Point(5)
        val n3 = Point(10)
        assertTrue(n1 == n2)
        assertTrue(n1.equals(n2))

        assertFalse(n1 == n3)
        assertFalse(n1.equals(n3))

        assertEquals(true, n1 != n3)
        assertEquals(true, !n1.equals(n3))
    }

    /**
     * Binary operations - Comparison operators
     * 二元操作 - 比较操作符
     */
    @Test
    fun test_ComparisonOperators() {
        val n1 = 10
        val n2 = 5
        assertTrue(n1 > n2)
        assertTrue(n1.compareTo(n2) > 0)

        assertTrue(n2 < n1)
        assertTrue(n2.compareTo(n1) < 0)

        assertTrue(n1 >= n2)
        assertTrue(n1.compareTo(n2) >= 0)

        assertTrue(n2 <= n1)
        assertTrue(n2.compareTo(n1) <= 0)
    }

    @Test
    fun test_test_ComparisonOperators_Overloading() {
        data class Point(val x: Int) {
            operator fun compareTo(p: Point): Int {
                return x.compareTo(p.x)
            }
        }

        val n1 = Point(10)
        val n2 = Point(5)
        assertTrue(n1 > n2)
        assertTrue(n1.compareTo(n2) > 0)

        assertTrue(n2 < n1)
        assertTrue(n2.compareTo(n1) < 0)

        assertTrue(n1 >= n2)
        assertTrue(n1.compareTo(n2) >= 0)

        assertTrue(n2 <= n1)
        assertTrue(n2.compareTo(n1) <= 0)
    }

    /**
     * Binary operations - Property delegation operators
     * 属性委托操作符
     */
    @Test
    fun test_PropertyDelegationOperators() {
        // TODO:Property delegation operators
    }

    @Test
    fun test_PropertyDelegationOperators_Overloading() {
        // TODO:Property delegation operators
    }

    // TODO: Infix calls for named functions

}