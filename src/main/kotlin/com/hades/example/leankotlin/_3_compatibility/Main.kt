package com.hades.example.leankotlin._3_compatibility

fun main() {
    // Java 调用 Kotlin
    City.sayHi("friend")

    var city = City();
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