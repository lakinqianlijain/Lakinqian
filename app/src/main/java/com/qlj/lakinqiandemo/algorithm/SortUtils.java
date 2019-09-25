package com.qlj.lakinqiandemo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/1.
 */

public class SortUtils {

    // 选择排序
    // 先取出第一个数据，然后从第二个开始比较大小，每次将小的数据的下标复制给min，一次遍历后取到最小的，然后和数组第一个位置互换；
    // 然后取出第二个数据，再从第三个数据开始比较，每次讲笑的数据的下标复制给min,一次遍历后取到最小的，然后和数组第二个位置互换；
    // 就这样，一直到第i个数据，这时候前面的i-1个数据以及从小到大排列好，第i个数据开始，和后面的数据比较，找出最小的，和i进行位置互换
    // 时间复杂度，(n-1)+(n-2)+...+1，所以时间复杂度是O（n^2）
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }


    // 直接插入排序
    // 从第二个数据开始，与第一个数据比较，如果比第一小，则将第一个数往后移一位，将第二个数据插到前面，这样1、2位置形成一个有序数组
    // 取第三个数据，与1、2组成的有序数据从后开始比较，如果比前面的数字小，则将该数据往后移，知道找到比第三个数据小的数据的位置，插在他后面，这时候1、2、3形成有序数组
    // 这样就可以得到规律，取第i个数据，前面i-1个数据时一个有序数组，第i个数据开始从i-1开始往前比较，大于第i个数据往后移，一直找到比i小的数据，插到他后面就可以了
    // 时间复杂度1+2+3+...+(n-1)，所以时间复杂度是O（n^2）
    public static void insertSort(int[] array) {
        //外层循环确定待比较数值
        for (int i = 1; i < array.length; i++) {//必须i=1，因为开始从第二个数与第一个数进行比较
            int temp = array[i];//待比较数值
            int j = i - 1;
            //内层循环为待比较数值确定其最终位置
            for (; j > 0 && array[j] > temp; j--) {//待比较数值比前一位置小，应插往前插一位
                array[j + 1] = array[j];
            }
            array[j + 1] = temp; //待比较数值比前一位置大，最终位置无误
        }
    }

    // 二分法插入排序
    // 二分法插入排序其实也属于插入法排序，只不过直接插入排序每次拿到待插入数据后，遍历前面排好的有序数组，找到位置插入；
    // 而二分法插入，是用二分法替代遍历，来找到对应的位置，然后插入数据
    // 时间复杂度log1+log2+...+logN，所以时间复杂度为O(nlogn);
    public static void binaryInsertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];// 待插入的值
            int right = i - 1;
            int left = 0;
            int mid;
            while (left <= right) {
                mid = (left + right) / 2;
                if (array[mid] > temp) {
                    right = mid - 1;
                } else if (array[mid] < temp) {
                    left = mid + 1;
                }
            }
            // 比left右边大的值往后移一位，等待temp插入
            for (int j = i - 1; j >= left; j--) {
                array[j + 1] = array[j];
            }
            // 在找到的位置插入
            array[left] = temp;
        }
    }

    // 希尔排序为什么会归入插入排序呢，是因为希尔排序的过程中使用了插入排序
    // 希尔排序的核心是分组，分组的方法有很多种，我们用得多的是d = d / 2的算法
    // 从d=d/2开始，每次区一半，然后进行分组，
    // 对于分在同一组的数据，使用插入排序进行排序
    // 最后，当步长取到1时，用这个简单的插入排序就能正确的排出顺序了，因为只有相邻两个数据存在顺序相反的可能
    public static void shellSort(int[] arr) {
        int temp = 0;
        int d = arr.length;
        while (true) {
            d = d / 2;
            for (int k = 0; k < d; k++) {// 进行分组
                for (int i = k + d; i < arr.length; i += d) {// 分组之后，因为该组可能有很多个数据，这个时候就用一个插入排序来进行该组的排序
                    for (int j = i; j > k; j -= d) {
                        if (arr[j] < arr[j - d]) {
                            temp = arr[j];
                            arr[j] = arr[j - d];
                            arr[j - d] = temp;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (d == 1) {
                break;
            }
        }
    }



// 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。其满足如下公式：
// 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
//小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]

// 堆排序，首先从(arr.length - 1)/2开始，构建大堆，遍历到0以后，就形成了一个大堆，这个时候arr[0]最大，这个时候，我们将a[0]和最后
// 一个值互换，这样，除组后一个值外的所有值构成一个类似最大堆，为什么说是类似最大堆，因为换过去的现在的arr[0]有可能不满足完全二叉树，但是除
//这个arr[0]以外的其他数都满足，所以只需要用这个a[0]去构建一个大堆就可以，不需要遍历，即maxHeap(arr, i, 0)；最后再交换位置，一直循环即可
// 时间复杂度log1+log2+...+logN，所以时间复杂度为O(nlogn);
    public static void HeapSort(int[] arr){
        if (arr == null || arr.length <= 1) return;
        // 创建大堆
        int half = (arr.length - 1)/2; // 假设长度为9
        for (int i = half; i >= 0; i--){
            // 只需遍历43210
            maxHeap(arr, arr.length, i);
        }
        for (int i = arr.length - 1; i >= 1;i--){
            // 最大的元素已经排在下标为0的位置
            exchangeElements(arr, 0, i);
            maxHeap(arr, i, 0);
        }
    }

    // length表示用于构造大堆的数组的元素数量
    private static void maxHeap(int[] arr, int length, int i) {
        int left = i * 2 +1;
        int right = i * 2 + 2;
        int largest = i;
        if (left < length && arr[left] > arr[largest]){
            largest = left;
        }
        if (right < length && arr[right] > arr[largest]){
            largest = right;
        }
        if (i != largest){
            // 进行数据交换
            exchangeElements(arr, i, largest);
            maxHeap(arr, length, largest);
        }
    }

    // 在数组a里面进行两个下标元素交换
    private static void exchangeElements(int[] arr, int i, int largest) {
        int temp = arr[i];
        arr[i] = arr[largest];
        arr[largest] = temp;
    }


    // 快速排序
    // 快速排序是不稳定排序，平均时间复杂度O（nlogn），最坏时间复杂度O（n*n）,辅助空间O（logn）<每次都要分给一个额外空间，而总共有logn次>
    //每次分成两段，那么分的次数就是logn了，每一次处理需要n次计算，那么时间复杂度就是nlogn了！
    //根据平均情况来说是O(nlogn),因为在数据分布等概率的情况下对于单个数据来说在logn次移动后就会被放到正确的位置上了。
    //最坏是O(n^2).这种情况就是数组刚好的倒序，然后每次去中间元的时候都是取最大或者最小。
    
    public static void quickSort(int[] arr){
        if (arr.length <= 0) return;
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     *
     * @param arr
     * @param low 低位
     * @param high 高位
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high){
            int middle = getMiddle(arr, low, high); // 找到了首个基准元素的位置,从这个元素，将原来的数组分为两个数组，再进行递归
            quickSort(arr, 0, middle - 1);
            quickSort(arr, middle+1, high);
        }
    }

    /**
     * 获取基点的下标
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static int getMiddle(int[] arr, int low, int high) {
        int temp = arr[low]; // 以低位为基准元素
        // 这其实就是一个逼近算法，基准元素从高位开始，遇到比他小的就暂停高位循环，并将这时候的低位赋值为这个小的高位值，
        // 然后开始从低位开始循环，遇到比他大的值就暂停低位循环，并将刚才的高位值赋值为这个比较大的低位值，这时候，高位值又比基准元素大了
        // 然后又重复高位循环，遇到小的就暂停并赋值，再开始低位循环，这样，其实low和high就越来越近了，这个过程可以保证low以前的比基准元素小，、
        // high之后的比基准元素大，所以，当low = high时就找到了位置，同时也就跳出了循环
        while (low < high){
            while (low < high && arr[high] >= temp){
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= temp){
                low++;
            }
            arr[high] = arr[low];
        }

        arr[low] = temp; // 经过循环后，low = high，这就找到了基准元素在数组里面最终的位置

        return low;
    }

    // 归并排序
    public static void mergeSort(int[] arr){
        if (arr.length <= 0) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 归并排序方法，为了方便递归。该方法回有两个递归方法，一直去将arr数组从中间拆分，一直递归，直到拆到left = right,
     * 就跳出递归，开始合并，所以，很有很多层的合并
     * @param arr
     * @param left
     * @param right
     */
    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right){
            int middle = (left + right)/2;
            mergeSort(arr, left, middle);
            mergeSort(arr, middle+1, right);
            merge(arr, left, middle, right);
        }
    }

    /**
     * merge回被回调很多次，从一个个长度为1的数组开始，慢慢合并，这样，组成一个个有序的数组，到最后，合并成左右两个有序数组，然后再进行一次合并即可
     * @param arr
     * @param left
     * @param middle
     * @param right
     */
    private static void merge(int[] arr, int left, int middle, int right) {
        int[] tempArray = new int[arr.length];
        int rightStart = middle + 1;
        int temp = left;
        int third = left;
        // 比较两个小数组的下标位置对应的值的大小，小的先放进数组
        while (left <= middle && rightStart <= right){
            if (arr[left] <= arr[rightStart]){
                tempArray[third++] = arr[left++];
            }else {
                tempArray[third++] = arr[rightStart++];// 这里面相当三句代码，赋值，然后将两个值++
            }
            // 如果左边还有数据需要拷贝，把左边数组剩下的拷贝到新数组
            while (left <= middle){
                tempArray[third++] = arr[left++];
            }
            // 如果右边还有数据需要拷贝，把右边数组剩下的拷贝到新数组
            while (rightStart < right){
                tempArray[third++] = arr[rightStart++];
            }
            while (temp <= right){
                arr[temp] = tempArray[temp++];
            }
        }
    }

    // 基础排序
    // 基础排序的核心是比较数据的每一位，假如数组{0,22,1,34,45,342,567,3,66,89,217,438,36,68}
    // 先通过最后一位来生成二维数组{[0],[1],[22,342],[3],[34],[45],[66,36],[217,567],[68,438],[89]},合成一维数组[0,1,22,342,3,34,45,66,36,217,567,68,438,89]
    // 然后通过十位来生成二维数组{[0,1,3],[217],[22],[34,36,438],[45,342],[66,567,68],[89]},合并成一维数组[0,1,3,217,22,34,36,438,45,342,66,567,68,89];
    // 再通过百位来生成二维数组{[0,1,3,22,34,36,45,66,68,89],[217],[342],[438],[567]},合并成一维数组{0,1,3,22,34,36,45,66,68,89,217,342,438,567}完成排序

    // 本算法还有一个bug，就是有负数的时候，就会存在问题。因为负数和正数不能用同一套逻辑去求数据的位数，以及排序逻辑刚好想法，所以，我们可以将负数和正数非为两个数组，
    // 然后分别使用基数排序
    public static void basicSort(int[] arr){
        int max = 0;//获取最大值
        for (int i = 0; i < arr.length; i++){
            if (max < arr[i]){
                max = arr[i];
            }
        }
        int times = 0; // 获取最大值位数
        while (max > 0){
            max = max/10;
            times++;
        }
        // 创建一个二维数组，然后创建10个空数组，并添加到queue这个二维数组。
        // 为什么是10个呢，因为以某一位的数字来区分，那就是0,1,2,3,4,5,6,7,8,9，即十个数组
        List<ArrayList> queue = new ArrayList<ArrayList>();
        for (int i = 0; i < 10; i++){
            ArrayList q = new ArrayList();
            queue.add(q);
        }

        // 开始遍历，以最大值的位数为循环变量，依次用个位，十位，百位...来进行排序
        for (int i = 0; i < times; i++){
            for(int j = 0; j < arr.length; j++){
                // 获取对应的位的值（i为0是个位，1是十位，2是百位）
                int x = arr[j]%(int)Math.pow(10, i+1)/(int)Math.pow(10, i);
                ArrayList q = queue.get(x);
                q.add(arr[j]);
                queue.set(x, q);// 把分好的数组添加会原来的二维数组
            }

            // 到这里之后，queue已经整理为一个有序的二维数组，然后就把queue里面的数据一个个有序的添加到arr即可
            int count = 0;
            for (int j = 0; j < 10; j++){
                while (queue.get(j).size() > 0){
                    ArrayList<Integer> q = queue.get(j);// 获取二维数组里面的每一个数组
                    arr[count] = q.get(0);
                    q.remove(0);
                    count++;
                }
            }
        }
    }

}
