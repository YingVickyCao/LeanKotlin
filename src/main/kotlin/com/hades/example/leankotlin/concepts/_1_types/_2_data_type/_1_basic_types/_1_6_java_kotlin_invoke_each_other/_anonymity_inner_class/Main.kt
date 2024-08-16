package com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_6_java_kotlin_invoke_each_other._anonymity_inner_class

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