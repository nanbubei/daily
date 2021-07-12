package com.wxn.algorithm.sort;

import org.apache.ibatis.reflection.ArrayUtil;

/**
 * 选择排序 O(n²) O(1) 非原地
 */
public class SelectionSort {
    /**
     * 分已排序区间和未排序区间。但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
     */

    static void sort(int[] nums) {
		for(int i = 1; i < nums.length - 1; i ++) {
			int minIndex = i + 1;
			int minNum = nums[minIndex];
			for(int j = minIndex; j < nums.length; j ++) {
				if(nums[j] < minNum) {
					minNum = nums[j];
					minIndex = j;
				}
			}
			int tmp = nums[i];
			nums[i] = nums[minIndex];
			nums[minIndex] = tmp;
		}
	}
	public static void sort1(int[] nums) {


	}


	public static void main(String[] args) {
		int[] nums = {1, 2, 4, 5, 6, 8, 1};
		sort1(nums);
		System.out.println(ArrayUtil.toString(nums));
	}
}
