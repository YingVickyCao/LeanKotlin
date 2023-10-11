package com.hades.example.leankotlin._1_basic_types._1_5_java_kotlin_invoke_each_other._anonymity_inner_class

fun main() {
    Test.message("welcome")
}


/**
 * public final class MainKt {
 *    public static final void main() {
 *       Test.INSTANCE.message("welcome");
 *    }
 *
 *    // $FF: synthetic method
 *    public static void main(String[] var0) {
 *       main();
 *    }
 * }
 */