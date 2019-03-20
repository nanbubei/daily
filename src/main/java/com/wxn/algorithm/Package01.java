package com.wxn.algorithm;

/**
 * 0-1背包问题 动态规划解决思路
 */
public class Package01 {
	/**
	 * weight: 物品重量，n: 物品个数，w: 背包可承载重量
	 */
	public int knapsack(int[] weight, int n, int w) {
		boolean[][] states = new boolean[n][w+1]; // 默认值 false
		states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
		states[0][weight[0]] = true;
		for (int i = 1; i < n; ++i) { // 动态规划状态转移
			for (int j = 0; j <= w; ++j) {// 不把第 i 个物品放入背包
				if (states[i-1][j] == true) states[i][j] = states[i-1][j];
			}
			for (int j = 0; j <= w-weight[i]; ++j) {// 把第 i 个物品放入背包
				if (states[i-1][j]==true) states[i][j+weight[i]] = true;
			}
		}
		for (int i = w; i >= 0; --i) { // 输出结果
			if (states[n-1][i] == true) return i;
		}
		return 0;
	}

	public static void main(String[] args) {
	    System.out.println(new Package01().knapsack2(new int[]{4,2,4}, 3, 7));
	}
	public static int knapsack2(int[] items, int n, int w) {
		boolean[] states = new boolean[w+1]; // 默认值 false
		states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
		states[items[0]] = true;
		for (int i = 1; i < n; ++i) { // 动态规划
			for (int j = w-items[i]; j >= 0; --j) {// 把第 i 个物品放入背包
				if (states[j]==true) states[j+items[i]] = true;
			}
		}
		for (int i = w; i >= 0; --i) { // 输出结果
			if (states[i] == true) return i;
		}
		return 0;
	}

}
