package com.wxn.test;

import com.wxn.algorithm.sort.QuickSort;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        int[] nums = new int[100000];
        for(int i = 0; i < nums.length; i ++) {
            nums[i] = new Random().nextInt(10000);
        }
        System.out.println(nums[99]);
        long start = System.currentTimeMillis();
//        //2675ms
        QuickSort.quickSort(nums, 0, nums.length - 1);
//        //1290
//        MergeSort.sort(nums);
//        BubbleSort.sort(nums);
        System.out.println(System.currentTimeMillis() - start);
    }

}
