package com.hades.example.leankotlin._2_data_type

fun main() {
    /**
     * Numbers representation on the JVM， START
     */
    run {
        // numbers are stored as primitive types : int, double and so on
        // Nullable numbers (Int? or generics ) is boxed in Java classes Integer,  Double and so on.

        // All nullable references to a are actually the same object because of the memory optimization that JVM applies to Integers between -128 and 127. It doesn't apply to the b references, so they are different objects.
        val a: Int = 100
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        println(boxedA === anotherBoxedA)    // true

        val b: Int = 10000
        val boxedB: Int? = b
        val anotherBoxedB: Int? = b
        println(boxedB === anotherBoxedB)    // false
        println(boxedB == anotherBoxedB)     // true

        /**
         * 结论：
         * (1) on JVM runtime, Int -> java int, Double -> java double
         * (2) on JVM runtime, Int? -> java Integer, Double -> java Double
         * (3) on JVM runtime, java Integer has cache [-128,127] (Integer.java -> IntegerCache). If value in this range, Int? -> java Integer -> int, or Int? -> java Integer
         * Byte also has cache [-128,127]
         * Long also has cache [-128,127]
         * === : justify object reference
         * == : justify value
         */
    }
}