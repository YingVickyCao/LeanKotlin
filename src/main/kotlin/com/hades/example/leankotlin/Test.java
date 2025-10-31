package com.hades.example.leankotlin;

public class Test {
    // 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。

    // 输入：nums = [1,2,3]
    //输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

    // 示例 2：
    //
    //输入：nums = [0,1]
    //输出：[[0,1],[1,0]]
    //示例 3：
    //
    //输入：nums = [1]
    //输出：[[1]]'
    public static void main(String[] args) {
//        int[] array = new int[3];
//        array[0] = 1;
//        array[1] = 2;
//        array[2] = 3;

//        test(array, 0);
    }

    private static void test(int[] array, int j){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
