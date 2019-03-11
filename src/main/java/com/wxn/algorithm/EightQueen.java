package com.wxn.algorithm;

import java.util.Calendar;

/**
 * 八皇后问题 回溯算法
 */
public class EightQueen {

	public static void main(String[] args) {
		new EightQueen().cal8queens(0);
	}
	int[] result = new int[8];// 全局或成员变量, 下标表示行, 值表示 queen 存储在哪一列
	int num = 0;
	public void cal8queens(int row) {
		// 调用方式：cal8queens(0);
		if(row == 8) {
			printQueens(result);
			return;
		}
		for(int i = 0; i < 8; i ++) {
			System.out.println(num++);
			if(isOk(row, i)) {
				result[row] = i;
				cal8queens(row + 1);
			}
		}
	}
	// 判断 row 行 column 列放置是否合适
	private boolean isOk(int row, int column) {
		int left = column - 1,
				right = column + 1;
		for(int x = row - 1; x >=0 ; x --) {
			if(result[x] == column)
				return false;
			if(left >= 0 && result[x] == left)
				return false;
			if(right <= 7 && result[x] == right)
				return false;
			left --;
			right ++;
		}


		return true;
	}

	private void printQueens(int[] result) { // 打印出一个二维矩阵
		for (int row = 0; row < 8; ++row) {
			for (int column = 0; column < 8; ++column) {
				if (result[row] == column) System.out.print("Q ");
				else System.out.print("* ");
			}
			System.out.println();
		}
		System.out.println();
	}



}
