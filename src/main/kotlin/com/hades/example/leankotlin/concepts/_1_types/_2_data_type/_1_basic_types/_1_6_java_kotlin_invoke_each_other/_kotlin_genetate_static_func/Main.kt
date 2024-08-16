package com.hades.example.leankotlin._1_basic_types._1_6_java_kotlin_invoke_each_other

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_6_java_kotlin_invoke_each_other._kotlin_genetate_static_func.City

fun main() {
    // Kotlin 调用 Java
    com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_6_java_kotlin_invoke_each_other._kotlin_genetate_static_func.City.sayHi("friend")

    val city = com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_6_java_kotlin_invoke_each_other._kotlin_genetate_static_func.City();
    city.goHome("XY")

    // TODO : Kotlin 调用 Kotlin
}


/*
public final class MainKt {
   public static final void main() {
      City.sayHi("friend");
      City city = new City();
      city.goHome("XY");
   }

   // $FF: synthetic method
   public static void main(String[] var0) {
      main();
   }
}
*/