package com.wxn.algorithm.sort;

/**
 * 插入 - O(n²) O(1) 原地
 */
public class InsertionSort {

    /**
     * 过程图见insertion_1.jpg
     * @param nums
     */
    public static void sort(int[] nums) {
        //已排序区间nums[0] - nums[i - 1] 未排序区间 nums[i] - nums[length - 1]
        for(int i = 1; i < nums.length; i ++) {
            int value = nums[i];
            //循环nums[0] - nums[i - 1]去跟nums[i]比较
            int j = i - 1;
            for( ;j >= 0; j --) {
                if(nums[j] > value) {
                    /*
                     * 这里非常巧妙:
                     * 把nums[j]依次赋给下一位直到nums[j]小于等于`未排序区间的第一个元素`nums[i],
                     * 然后循环结束把未排序区间的第一个元素赋值给nums[j + 1]
                     * 比如:1 2 3 5 3 2, 0-3是已排序区间,4-5是未排序区间(i = 4, j = 3到-1, value=3)
                     * nums[3] = 5, 5 > 3 : 1 2 3 5 3 2 - 1 2 3 5 5 2
                     * nums[2] = 3, 3 = 3 : for结束
                     * value赋给nums[j + 1] : nums[3] = 1 2 3 3 5 2
                     * 执行结果依次是
                     */
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }
            nums[j + 1] = value;
        }

    }
}
