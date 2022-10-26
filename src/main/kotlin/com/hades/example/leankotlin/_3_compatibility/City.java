package com.hades.example.leankotlin._3_compatibility;

public class City {
    public static void sayHi(String name) {
        System.out.println("Hi " + name);
    }

    public void goHome(String name) {
        System.out.println(name);
    }
}
