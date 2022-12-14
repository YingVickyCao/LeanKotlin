package com.hades.example.leankotlin._3_java_kotlin_invoke_each_other._3_3_anonymity_inner_class

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