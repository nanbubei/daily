package com.wxn.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ImportDoc {

	public static void main(String[] args) throws Exception {
		System.out.println(1);
		createImage("你好今天是", new File("d:/1/test.png"));
	}

	public static void drawString(String str,int x,int y,int rate, Graphics2D g,int fontSize){
		String tempStr="";
		int tempx=x;
		int tempy=y;
		while (str.length()>0){
			tempStr=str.substring(0, 1);
			str=str.substring(1, str.length());
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawString(tempStr, tempx, tempy);
			tempx = tempx + fontSize - rate;
		}
	}
	public static void createImage(String str,File outFile) throws Exception{
		Font font = Font.createFont(0, new File("C:\\Users\\Administrator\\Desktop\\字体\\HYQiHei-55S.otf"));
	}
}
