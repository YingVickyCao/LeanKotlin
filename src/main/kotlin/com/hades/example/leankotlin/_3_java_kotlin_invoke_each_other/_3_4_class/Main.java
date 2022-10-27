package com.hades.example.leankotlin._3_java_kotlin_invoke_each_other._3_4_class;

public class Main {
    public static void main(String[] args) {
        System.out.println(NameOfJava.class.getSimpleName()); // NameOfJava
        System.out.println(GradeKt.class.getSimpleName()); // NameOfKotlinKt
    }
}