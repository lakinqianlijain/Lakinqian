package com.qlj.lakinqiandemo.algorithm;

/**
 * Created by Administrator on 2019/8/27.
 */

public class SearchUtils {

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

    // 非递归二分法查找，同样需要先将数组排序,因为整个数组是有序的，所以一直二分缩小数组即可
    public int directBinarySearch(int[] arr, int elem) {
        int low = 0;
        int high = arr.length - 1;
        SortUtils.quickSort(arr); //将数组排序
        while (low < high) {
            int middle = (low + high) / 2;
            if (elem > arr[middle]) {
                // 右边找
                low = middle + 1;
            } else if (elem < arr[middle]) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}
