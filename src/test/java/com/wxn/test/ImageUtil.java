package com.wxn.test;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class ImageUtil {
	/** ppt封面的各个图片位置和大小 x, y, width, height **/
	private static final int[][] PPT_INFO= new int[][]{
			new int[]{12, 12, 233, 131},
			new int[]{2, 152, 125, 75},
			new int[]{129, 152, 125, 75},
			new int[]{2, 229, 125, 75},
			new int[]{129, 229, 125, 75},
			new int[]{2, 306, 125, 75},
			new int[]{129, 306, 125, 75},
	};
	/** word横版封面的各个图片位置和大小 x, y, width, height **/
	private static final int[][] WORD_TRANSVERSE_INFO= new int[][]{
			new int[]{12, 12, 232, 138},
			new int[]{12, 164, 232, 138},
	};
	/** word竖版封面的各个图片位置和大小 x, y, width, height **/
	private static final int[][] WORD_VERTICAL_INFO= new int[][]{
			new int[]{12, 12, 232, 289},
	};
	/** 视频封面的各个图片位置和大小 x, y, width, height **/
	private static final int[][] VIDEO_INFO= new int[][]{
			new int[]{12, 12, 233, 131},
			new int[]{2, 152, 125, 75},
			new int[]{129, 152, 125, 75},
			new int[]{2, 229, 125, 75},
			new int[]{129, 229, 125, 75},
	};



	/**
	 * 创建文档封面并上传
	 * @param type 类型 1ppt 2word 3视频
	 * @param productId 100id 只有ppt和word用传
	 * @param videoId 300id
	 */
	public static void createCoverAndUpload(int type, long productId, long videoId) throws Exception {
		BufferedImage[] realImgs;
		BufferedImage resultImg;
		if(type == 3) {
			realImgs = new BufferedImage[5];
			for(int i = 0; i < realImgs.length; i ++)
				realImgs[i] = ImageIO.read(new URL("http://211.147.220.169/videofs/file/" + videoId + "_" + i + "_380x214.jpg"));
			resultImg = makeCover(realImgs, VIDEO_INFO);
		} else if(type == 2) {
			//word还分横版竖版 拿到第一张图片判断是横版还是竖版
			BufferedImage firstImg = ImageIO.read(new URL("http://page.douding.cn/docinpage1/_" + productId + "_0.png"));
			//宽大于高 是横版,否则就看成竖版
			if(firstImg.getWidth() > firstImg.getHeight()) {
				realImgs = new BufferedImage[] {
					ImageIO.read(new URL("http://page.douding.cn/docinpage1/_" + productId + "_0.png")),
					ImageIO.read(new URL("http://page.douding.cn/docinpage1/_" + productId + "_1.png"))
				};
				resultImg = makeCover(realImgs, VIDEO_INFO);
			} else {
				realImgs = new BufferedImage[] {
					ImageIO.read(new URL("http://page.douding.cn/docinpage1/_" + productId + "_0.png"))
				};
				resultImg = makeCover(realImgs, VIDEO_INFO);
			}
		} else if(type == 1) {
			realImgs = new BufferedImage[7];
			for(int i = 0; i < realImgs.length; i ++)
				realImgs[i] = ImageIO.read(new URL("http://page.douding.cn/docinpage1/_" + productId + "_" + i + ".png"));
			resultImg = makeCover(realImgs, VIDEO_INFO);
		}
		//开始上传

	}



	public static BufferedImage makeCover(BufferedImage[] realImgs, int[][] sizeInfo) throws Exception{
		//最终封面图
		BufferedImage resultImg = ImageIO.read(new File("D:/ppt.png"));
		for(int i = 0; i < sizeInfo.length; i ++) {
			//变小之后的图
			BufferedImage smallImg;
			smallImg = new BufferedImage(sizeInfo[i][2], sizeInfo[i][3], BufferedImage.TYPE_INT_RGB);
			smallImg.getGraphics().drawImage(realImgs[i], 0, 0, smallImg.getWidth(), smallImg.getHeight(), null);
			//变小之后的图放到结果图中
			resultImg.getGraphics().drawImage(smallImg, sizeInfo[i][0], sizeInfo[i][1], smallImg.getWidth(), smallImg.getHeight(), null);
		}
//		ImageIO.write(resultImg, "png", new File("d:\\show111.png"));
		return resultImg;
	}

}
