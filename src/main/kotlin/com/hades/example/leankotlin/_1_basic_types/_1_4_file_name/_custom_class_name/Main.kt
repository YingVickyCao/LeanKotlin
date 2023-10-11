@file:JvmName("LoginUtils")

package com.hades.example.leankotlin._1_basic_types._1_4_file_name._custom_class_name

fun f(name: String) {
    println("Hi $name")
}


/*
Kotlin Bytecode

public final class LoginUtils {
   public static final void login(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      String var1 = "Hi " + name;
      System.out.println(var1);
   }
}
*/