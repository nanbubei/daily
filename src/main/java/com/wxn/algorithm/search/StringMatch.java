package com.wxn.algorithm.search;

import java.util.Arrays;

/**
 * 字符串匹配
 * BM算法
 */
public class StringMatch {

	private static final int SIZE = 256; //字符集长度



	public static void main(String[] args) {
	    System.out.println(new StringMatch().bm("asjifoqwjoifjqwoifjiowq".toCharArray(), "jio".toCharArray()));
	}

	/**
	 * BM算法
	 * @param a 字符串
	 * @param b 模式串
	 * @return
	 */
	// a,b 表示主串和模式串；n，m 表示主串和模式串的长度。
	public int bm(char[] a, char[] b) {
		int[] bc = new int[SIZE]; // 记录模式串中每个字符最后出现的位置
		generateBC(b, bc); // 构建坏字符哈希表
		int[] suffix = new int[b.length];
		boolean[] prefix = new boolean[b.length];
		generateGS(b, suffix, prefix);
		int i = 0; // j 表示主串与模式串匹配的第一个字符

		while (i <= a.length - b.length) {
			int j;
			for (j = b.length - 1; j >= 0; --j) { // 模式串从后往前匹配
				if (a[i+j] != b[j]) break; // 坏字符对应模式串中的下标是 j
			}
			if (j < 0) {
				return i; // 匹配成功，返回主串与模式串第一个匹配的字符的位置
			}
			int x = j - bc[(int)a[i+j]];
			int y = 0;
			if (j < b.length - 1) { // 如果有好后缀的话
				y = moveByGS(j, b.length, suffix, prefix);
			}
			i = i + Math.max(x, y);
		}
		return -1;
	}


	// j 表示坏字符对应的模式串中的字符下标 ; m 表示模式串长度
	private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
		int k = m - 1 - j; // 好后缀长度
		if (suffix[k] != -1) return j - suffix[k] +1;
		for (int r = j+2; r <= m-1; ++r) {
			if (prefix[m-r] == true) {
				return r;
			}
		}
		return m;
	}


	/**
	 * 根据模式串构建简易散列表
	 * 	完成后以单个字符的ascii码为下标取bc的值就是字符出现的最后位置
	 * @param b 模式串
	 * @param bc 构建完的散列表
	 */
	private void generateBC(char[] b, int[] bc) {
		for (int i = 0; i < SIZE; ++i) {
			bc[i] = -1; // 初始化 bc
		}
		for (int i = 0; i < b.length; ++i) {
			int ascii = (int)b[i]; // 计算 b[i] 的 ASCII 值
			bc[ascii] = i;
		}
	}

	/**
	 * suffix 和 prefix的计算过程
	 * @param b 模式串
	 * @param suffix
	 * @param prefix
	 */
	private void generateGS(char[] b, int[] suffix, boolean[] prefix) {
		for (int i = 0; i < b.length; ++i) { // 初始化
			suffix[i] = -1;
			prefix[i] = false;
		}
		for (int i = 0; i < b.length - 1; ++i) { // b[0, i]
			int j = i;
			int k = 0; // 公共后缀子串长度
			while (j >= 0 && b[j] == b[b.length - 1 - k]) { // 与 b[0, m-1] 求公共后缀子串
				--j;
				++k;
				suffix[k] = j+1; //j+1 表示公共后缀子串在 b[0, i] 中的起始下标
			}
			if (j == -1) prefix[k] = true; // 如果公共后缀子串也是模式串的前缀子串
		}
	}

}
