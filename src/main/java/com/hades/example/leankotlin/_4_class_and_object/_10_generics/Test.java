package com.hades.example.leankotlin._4_class_and_object._10_generics;

public class Test {
    public static void main(String[] args) {
        MyJBox myJBox = new MyJBox();
        myJBox.put("abc");
        myJBox.put(null);
    }
}
