package com.hades.example.leankotlin._4_java_kotlin_invoke_each_other

import com.hades.example.leankotlin._0_others._java_kotlin_invoke_each_other._kotlin_genetate_static_class.City

fun main() {
    // Kotlin 调用 Java
    City.sayHi("friend")

    val city = City();
    city.goHome("XY")
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