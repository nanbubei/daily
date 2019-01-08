package com.wxn.algorithm.search;

/**
 * 二分查找
 */
public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(search(new int[]{1,2,3,4,5,6}, 2));
    }
    public static int search(int[] nums, int findNum) {
        int low = 0;
        int high = nums.length - 1;
        while(low <= high) {
            int mid = (low + high) / 2;
            if(nums[mid] == findNum)
                return mid;
            else if(nums[mid] < findNum)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }


}
