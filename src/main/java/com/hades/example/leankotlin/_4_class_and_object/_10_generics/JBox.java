package com.hades.example.leankotlin._4_class_and_object._10_generics;

import org.jetbrains.annotations.NotNull;

public interface JBox {
    // Java中的@NotNull 只是提示非空，但是实际上还是可以允许传入空
    <T> void put(@NotNull T t);
}