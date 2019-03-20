package com.wxn.test.font;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class FontMain {
	public static void main(String[] args) throws Exception {
		int offset = 0;

		//写头
		ByteArrayOutputStream ttfOutputStream = new ByteArrayOutputStream();
		ttfOutputStream.write(getBytes((Integer) 65536)); //4
		int numTables = (Integer) 12;
		ttfOutputStream.write(getBytes((short)numTables));//2
		int temp = numTables;
		int searchRange = 16;
		short entrySelector = 0;
		while (temp > 1) {
			temp = temp >> 1;
			entrySelector++;
			searchRange = (searchRange << 1);
		}
		short rangeShift = (short) (numTables * 16 - searchRange);
		ttfOutputStream.write(getBytes((short) searchRange));
		ttfOutputStream.write(getBytes(entrySelector));
		ttfOutputStream.write(getBytes(rangeShift));
		offset += 12;




		//写字体信息到一个arrays
//		HashMap<String, Number> tableDirectory = new HashMap<String, Number>();
//		tableDirectory.put("offset", );
//		tableDirectory.put("compLength", );
//		tableDirectory.put("origLength", 96);
//		tableDirectory.put("origChecksum", );
//		tableDirectory.put("tag", );
//		offset += 16;
//		tableRecordEntries.add(tableDirectory);

		//arrays写入ttf输出流
//		writeTableRecordEntries(ttfOutputStream);


		//合成一步
		ttfOutputStream.write(getBytes((Integer) 1330851634));
		ttfOutputStream.write(getBytes(-389093242));
		ttfOutputStream.write(getBytes(204));
		ttfOutputStream.write(getBytes((Integer) 96));
		offset += 16;
		if (offset % 4 != 0) {
			offset += 4 - (offset % 4);
		}



		//写字体数据



		String filePath = "C:\\Users\\Administrator\\Desktop\\字体";
		String fileName = "test.ttf";
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(ttfOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}


	}


	private static byte[] getBytes(int i) {
		return ByteBuffer.allocate(4).putInt(i).array();
	}

	private static byte[] getBytes(short h) {
		return ByteBuffer.allocate(2).putShort(h).array();
	}
}
