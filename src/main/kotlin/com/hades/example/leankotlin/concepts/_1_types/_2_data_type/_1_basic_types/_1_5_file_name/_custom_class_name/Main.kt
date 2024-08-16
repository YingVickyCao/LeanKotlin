@file:JvmName("LoginUtils")

package com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name

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