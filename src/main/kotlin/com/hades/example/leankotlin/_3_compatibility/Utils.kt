/*
public final class MyUtils {
 */
//@file:JvmName("MyUtils")

@file:JvmName("MyUtils")
@file:JvmMultifileClass


package com.hades.example.leankotlin._3_compatibility

fun sayHi(name: String) {
    println("Hi $name")
}


/*
Kotlin Bytecode
public final class UtilsKt {
   public static final void sayHi(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      String var1 = "Hi " + name;
      System.out.println(var1);
   }
}
*/