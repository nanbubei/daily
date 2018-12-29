package com.wxn.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    /**
     * 如果要排序数组中下标从 p 到 r 之间的一组数据，
     * 我们选择 p 到 r 之间的任意一个数据作为 pivot（分区点）。
     *
     */


    /**
     * 将数组a的p-r排序
     * @param a
     * @param p
     * @param r
     */
    public static void quickSort(int[] a, int p, int r) {
        if(p >= r)
            return;
        int q = partition(a, p, r);
        quickSort(a, p, q - 1);
        quickSort(a, q + 1, r);
    }
    public static void main(String[] args) {
        int[] a = new int[]{8, 10, 6, 3, 2, 1, 5};
        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 就是随机选择一个元素作为 pivot (这里以a[r]为point)
     * 然后对 A[p…r] 分区(把小于pivot的放前面,大于pivot的放后面中间是pivot)，
     * 函数返回 pivot 的下标。
     *  例  132-> 123 return 1
     *      951-> 159 return 0
     * @param a
     * @param p
     * @param r
     * @return
     */
    private static int partition(int[] a, int p, int r) {
        int point = a[r];
        int i = p;
        for(int j = p; j <= r - 1; j ++) {
            if(a[j] < point) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i ++;
            }
        }
        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;
        return i;
    }

}
