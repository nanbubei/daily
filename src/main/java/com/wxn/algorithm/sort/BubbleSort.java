package com.wxn.algorithm.sort;

import org.apache.ibatis.reflection.ArrayUtil;

/**
 * 冒泡-O(n²) O(1) 原地
 */
public class BubbleSort {

    /**
     * 冒泡排序 升序
     * 内层循环, 循环数组未排序阶段, 把最大的一位放到未排序阶段的最后一位;<br />
     *  比如7 9 0 3 2 1 一次完整的内层循环后是7 0 3 2 1 9 第二次是032179<br />
     * 外层循环, 循环内层, 把所有最大值放到数组的(length - i - 1)位<br />
     * 完整的运行:<br />
     * i=0:
     * 	[7, 9, 0, 3, 2, 1]   j:0
     * 	[7, 0, 9, 3, 2, 1]   j:1
     * 	[7, 0, 3, 9, 2, 1]   j:2
     * 	[7, 0, 3, 2, 9, 1]   j:3
     * 	[7, 0, 3, 2, 1, 9]   j:4
     * i=1:
     * 	[0, 7, 3, 2, 1, 9]   j:0
     * 	[0, 3, 7, 2, 1, 9]   j:1
     * 	[0, 3, 2, 7, 1, 9]   j:2
     * 	[0, 3, 2, 1, 7, 9]   j:3
     * i=3:
     * 	[0, 3, 2, 1, 7, 9]   j:0
     * 	[0, 2, 3, 1, 7, 9]   j:1
     * 	[0, 2, 1, 3, 7, 9]   j:2
     * i=4:
     * 	[0, 2, 1, 3, 7, 9]   j:0
     * 	[0, 1, 2, 3, 7, 9]   j:1
     * i=5:
     * 	[0, 1, 2, 3, 7, 9]   j:0
     *  <br />
     *  <br />
     * 	后续-
     *  外层循环开始时 加入一个提前退出冒泡循环的标记默认false;
     *  内层循环的判断里修改为true;
     *  整个内层循环结束如果flag还是false,说明没有交换的数据,证明整个数组是有效的. 直接break
     * @param nums
     */
    public static void sort(int[] nums) {
        if(nums == null || nums.length < 2) {
            return;
        }
        for(int i = 0; i < nums.length; i ++) {
            // 提前退出冒泡循环的标志位
            boolean flag = false;
            for(int j = 0; j < nums.length - i - 1; j ++) {
                if(nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                    flag = true;
                }
            }
            if(! flag) { break;}
        }
    }

    public static void sortAgain(int[] nums) {
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 5, 6, 8, 1};
        sortAgain(nums);
        System.out.println(ArrayUtil.toString(nums));
    }


}
