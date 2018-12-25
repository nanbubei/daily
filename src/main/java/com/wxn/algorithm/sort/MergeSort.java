package com.wxn.algorithm.sort;

/**
 * 归并排序
 */
public class MergeSort {

    public static void sort(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
    }

    /**
     * 最终结果是把nums的p-r位排序 <br />
     * 把nums 从中间分成两部分, 分别排序; 然后合并
     * @param nums
     * @param p
     * @param r
     */
    private static void mergeSort(int[] nums, int p, int r) {
        if(p >= r) {
            return;
        }
        int q = (p + r) / 2;
        mergeSort(nums, p, q);
        mergeSort(nums, q + 1,r );
        merge(nums, p, q, r);
    }

    /**
     * 把两个有序数组nums[p...q] nums[q+1...r]合并到nums[p...r]中
     * 过程图见meger_1.jpg
     */
    private static void merge(int[] nums, int p, int q, int r) {
        /*
         * i:左边数组的游标,
         * j:右边数组的游标,
         * k:匹配总次数
         */
        int i = p, j = q + 1, k = 0;
        //申请临时数组tmps, 长度是0... r-p
        int[] tmps = new int[r - p + 1];
        /*
         * 循环: 比较这两个元素 A[i] 和 A[j]，
         *       如果 A[i]<=A[j]，我们就把 A[i] 放入到临时数组 tmp，并且 i后移一位，同时k+1
         *       否则将 A[j] 放入到数组 tmp，j 后移一位 同时k+1
         * 循环结束条件: 一个或两个数组已经遍历完毕
         */
        while(i <= q && j <= r) {
            if(nums[i] < nums[j]) {
                tmps[k ++] = nums[i ++];
            } else {
                tmps[k ++] = nums[j ++];
            }
        }
        //接下来需要把另一个没有遍历完的数组的所有数据依次放到tmps里

        //判断哪边还有剩余的: 如果j <= r, 说明第二个数组已经循环完了; else就是第一个数组已经循环完了或者都循环完了
        int start = (j <= r ? j : i),
            end = (j <= r ? r : q);

        //剩余拷贝到tmp
        while(start <= end) {
            tmps[k ++] = nums[start ++];
        }

        //tmp所有数据放到原数组中
        for(int z = 0; z < r - p + 1; z ++) {
            nums[z + p] = tmps[z];
        }

    }
}
