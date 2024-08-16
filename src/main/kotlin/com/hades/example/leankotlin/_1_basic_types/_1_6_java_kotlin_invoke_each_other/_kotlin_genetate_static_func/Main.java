package com.hades.example.leankotlin._1_basic_types._1_6_java_kotlin_invoke_each_other._kotlin_genetate_static_func;

public class Main {
    public static void main(String[] args) {
        // Java 调用 Kotlin
        UtilsKt.sayHi("Vicky");


        // Java 调用 Java
        City.sayHi("Shanghai");

        City city = new City();
        city.goHome("Pu dong");
    }
}