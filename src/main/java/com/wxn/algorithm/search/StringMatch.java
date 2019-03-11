package com.wxn.algorithm.search;

/**
 * 字符串匹配
 * BM算法
 */
public class StringMatch {

	private static final int SIZE = 256; // 全局变量或成员变量

	public static void main(String[] args) {
	    System.out.println((int) 'c');
	}

	/**
	 * 模式串散列过程 计算模式串字符最后出现的位置
	 * 模式串 a,c,e,c 对应ascii(97, 99, 101, 99)
	 * 对应到散列表下标是ascii的元素值是模式串的最大key,
	 * 比如bc[97]=0, bc[99]=1, bc[101]=2, bc[99]=3.
	 * @param b 模式串
	 * @param bc 散列表(0-255)
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
	 * bm实现过程 只坏字符规则
	 * @param a 主串
	 * @param b 模式串
	 * @return
	 */
	public int bm(char[] a, char[] b) {
		int[] bc = new int[SIZE]; // 记录模式串中每个字符最后出现的位置
		generateBC(b, bc); // 构建坏字符哈希表
		int i = 0; // i 表示主串与模式串对齐的第一个字符
		while (i <= a.length - b.length) {
			int j;
			for (j = b.length - 1; j >= 0; --j) { // 模式串从后往前匹配
				if (a[i+j] != b[j]) break; // 坏字符对应模式串中的下标是 j
			}
			if (j < 0) {
				return i; // 匹配成功，返回主串与模式串第一个匹配的字符的位置
			}
			// 这里等同于将模式串往后滑动 j-bc[(int)a[i+j]] 位
			i = i + (j - bc[(int)a[i+j]]);
		}
		return -1;
	}




}
