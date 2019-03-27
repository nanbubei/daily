package com.wxn.algorithm;

/**
 * 0-1背包问题 动态规划解决思路
 */
public class Package01 {
	
	public static void main(String[] args) {
	    System.out.println(new Package01().knapsack3(new int[]{2, 2, 4, 9, 3}, new int[]{3, 4, 8, 17, 6}, 5, 9));
	}

	/**
	 * @param weight 重量
	 * @param value 价值
	 * @param n 物品个数
	 * @param w 总重量
	 * @return
	 */
	public static int knapsack3(int[] weight, int[] value, int n, int w) {
		int[][] states = new int[n][w+1];
		for (int i = 0; i < n; ++i) { // 初始化 states
			for (int j = 0; j < w+1; ++j) {
				states[i][j] = -1;
			}
		}
		states[0][0] = 0;
		states[0][weight[0]] = value[0];
		for (int i = 1; i < n; ++i) { // 动态规划，状态转移
			for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
				if (states[i-1][j] >= 0) states[i][j] = states[i-1][j];
			}
			for (int j = 0; j <= w-weight[i]; ++j) { // 选择第 i 个物品
				if (states[i-1][j] >= 0) {
					int v = states[i-1][j] + value[i];
					if (v > states[i][j+weight[i]]) {
						states[i][j+weight[i]] = v;
					}
				}
			}
		}
		// 找出最大值
		int maxvalue = -1;
		for (int j = 0; j <= w; ++j) {
			if (states[n-1][j] > maxvalue) maxvalue = states[n-1][j];
		}
		return maxvalue;
	}

}
