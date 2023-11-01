package com.hades.example.leankotlin._4_class_and_object._14_object_expressions_and_declarations._companion_objects

fun main() {
    val myClass = MyClass.Factory.create();
    myClass.print()

    val myClass2 = MyClass2.Companion.create()
    myClass2.print()
}


class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }

    fun print() {
        println("MyClass")
    }
}

// companion object 相当于  factory method
/*
 public final class MyClass {
   @NotNull
   public static final Factory Factory = new Factory((DefaultConstructorMarker)null);

   public static final class Factory {
      @NotNull
      public final MyClass create() {
         return new MyClass();
      }

      private Factory() {
      }
      public Factory(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
 */


//  name of companion object 可以被省略,那么name 是Companion
class MyClass2 {
    companion object {
        fun create(): MyClass2 = MyClass2()
    }

    fun print() {
        println("MyClass2")
    }
}