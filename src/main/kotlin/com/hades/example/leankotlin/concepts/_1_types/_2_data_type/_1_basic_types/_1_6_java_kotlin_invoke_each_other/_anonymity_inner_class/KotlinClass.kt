package com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_6_java_kotlin_invoke_each_other._anonymity_inner_class

// 匿名内部类： 单例模式
object Test {
    fun message(msg: String) {
        println(msg)
    }
}


/*
public final class Test {
   @NotNull
   public static final Test INSTANCE;

   public final void message(@NotNull String msg) {
      Intrinsics.checkNotNullParameter(msg, "msg");
      System.out.println(msg);
   }

   private Test() {
   }

   static {
      Test var0 = new Test();
      INSTANCE = var0;
   }
}

*/