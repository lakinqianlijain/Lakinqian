package com.qlj.lakinqiandemo.algorithm;

import android.util.Log;

/**
 * Created by Administrator on 2019/8/28.
 */

public class recursionUtils {

    int count = 0;

    // 递归二分法查找，需要先将数组排序
    public static int binarySearch(int[] arr, int elem) {
        int low = 0;
        int high = arr.length - 1;
        SortUtils.quickSort(arr); //将数组排序
        return binarySearch(arr, elem, low, high);
    }

    public static int binarySearch(int[] arr, int elem, int low, int high) {
        int middle = (low + high) / 2;
        if (arr[middle] == elem) {
            return middle;
        }
        if (arr[middle] < elem) {
            // 找右边
            return binarySearch(arr, elem, middle + 1, high);
        }
        if (arr[middle] > elem){
            // 找左边
            return binarySearch(arr, elem, low, middle -1 );
        }
        return -1;
    }

    // 汉诺塔算法
    // 汉诺塔（又称河内塔）问题是源于印度一个古老传说的益智玩具。大梵天创造世界的时候做了三根金刚石柱子，
    // 在一根柱子上从下往上按照大小顺序摞着64片黄金圆盘。大梵天命令婆罗门把圆盘从下面开始按大小顺序重新摆放在另一根柱子上。
    // 并且规定，在小圆盘上不能放大圆盘，在三根柱子之间一次只能移动一个圆盘。

    // 代码看起来特别简单，但运算实际特别复杂

    public static void hanNota(int n, char from, char dependOn, char to){
        if (n == 1){
            move(1, from, to);
        } else {
            hanNota(n - 1, from, to, dependOn); //将n-1个盘子由A经过C移动到B
            move(n, from, to); //执行最大盘子n移动
            hanNota(n - 1, dependOn, from, to); //剩下的n-1盘子，由B经过A移动到C
        }
    }

    private static void move(int count, char from, char to) {
        Log.e("lakinqian", "移动"+count+"："+from+"----"+to );
    }

    // 欧几里德算法
    // (m>n)m和n的最大公约数= n和m%n的最大公约数
    // 36 24——> 24和12——>12和0
    public static int GCD(int m, int n){
        if (n == 0){
            return m;
        }else {
            return GCD(n, m%n);
        }
    }

    // 阶乘求解算法
    public static int factorial(int n){
        if (n == 1){
            return n;
        } else {
            return n*factorial(n - 1);
        }
    }
}
