@file:JvmName("LoginUtils")

package com.hades.example.leankotlin._3_java_kotlin_invoke_each_other._3_2_custom_class_name

fun login(name: String) {
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