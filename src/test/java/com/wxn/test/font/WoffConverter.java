package com.wxn.test.font;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class WoffConverter {

	public static void main(String[] args) throws Exception {
		WoffConverter wc = new WoffConverter();
		DataInputStream woffFileStream = new DataInputStream(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\字体\\从前有座山山上有座庙庙里有个老和尚讲故事中国.ttf")));
		getFile(wc.convertToTTFOutputStream(new DataInputStream(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\字体\\从前有座山山上有座庙庙里有个老和尚讲故事中国.woff")))).toByteArray(), "", "");








		System.out.print(woffFileStream.readInt() + "   ");
		short numTables = new DataInputStream(woffFileStream).readShort();
		System.out.print(numTables + "   ");
		System.out.print(woffFileStream.readShort() + "   ");
		System.out.print(woffFileStream.readShort() + "   ");
		System.out.print(woffFileStream.readShort() + "   ");
		System.out.println();
		for(short i = 0; i < numTables; i ++) {
			System.out.print("tag:" + woffFileStream.readInt() + "  ");
			System.out.print("checkSum:" + woffFileStream.readInt() + "  ");
			System.out.print("offset:" + woffFileStream.readInt() + "  ");
			System.out.print("length:" + woffFileStream.readInt() + "  ");
			System.out.println();
		}
		byte[] b = new byte[96];
		woffFileStream.read(b);

//		ByteArrayOutputStream ttfOutputStream = new ByteArrayOutputStream();
//		wc.writeOffsetTable(ttfOutputStream);
//		wc.getTableRecordEntries(new DataInputStream(woffFileStream));
//		wc.writeTableRecordEntries(ttfOutputStream);
//		wc.writeFontData(woffFileStream, ttfOutputStream);


	}
	public static void getFile(byte[] bfile, String filePath,String fileName) {
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
			bos.write(bfile);
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







		@SuppressWarnings("serial")
	private static final LinkedHashMap<String, Integer> woffHeaderFormat = new LinkedHashMap<String, Integer>() {
		{
			put("signature", 4);
			put("flavor", 4);
			put("length", 4);
			put("numTables", 2);
			put("reserved", 2);
			put("totalSfntSize", 4);
			put("majorVersion", 2);
			put("minorVersion", 2);
			put("metaOffset", 4);
			put("metaLength", 4);
			put("metaOrigLength", 4);
			put("privOffset", 4);
			put("privOrigLength", 4);
		}
	};

	@SuppressWarnings("serial")
	private static final LinkedHashMap<String, Integer> tableRecordEntryFormat = new LinkedHashMap<String, Integer>() {
		{
			put("tag", 4);
			put("offset", 4);
			put("compLength", 4);
			put("origLength", 4);
			put("origChecksum", 4);
		}
	};

	private HashMap<String, Number> woffHeaders = new HashMap<String, Number>();

	private ArrayList<HashMap<String, Number>> tableRecordEntries = new ArrayList<HashMap<String, Number>>();

	private int offset = 0;

	private int readOffset = 0;

	/**
	 * woff文件输入流转ttf文件byte数组
	 * @param woffFileStream
	 * @return
	 * @throws IOException
	 * @throws DataFormatException
	 */
	public byte[] convertToTTFByteArray(InputStream woffFileStream)
			throws IOException, DataFormatException {
		ByteArrayOutputStream ttfOutputStream = convertToTTFOutputStream(woffFileStream);
		byte[] ttfByteArray = ttfOutputStream.toByteArray();
		return ttfByteArray;
	}

	/**
	 * woff文件输入流转ttf输出流
	 * @param woffFileStream
	 * @return
	 * @throws IOException
	 * @throws DataFormatException
	 */
	public ByteArrayOutputStream convertToTTFOutputStream(InputStream woffFileStream)
			throws  IOException, DataFormatException {
		getHeaders(new DataInputStream(woffFileStream));
		if ((Integer) woffHeaders.get("signature") != 2001684038) {
			throw new RuntimeException("Invalid woff file");
		}
		ByteArrayOutputStream ttfOutputStream = new ByteArrayOutputStream();
		writeOffsetTable(ttfOutputStream);//头
		//目录 写入 tableRecordEntries(这个list存的偏移量什么的是woff文件的数据还没转成ttf呢)
		getTableRecordEntries(new DataInputStream(woffFileStream));
		/**
		 * //tableRecordEntries-ttf流 和 计算offset
		 * 第一条目录offset = 头长度12 + 每条目录长度16(4*4) * 目录条数12 = 204
		 * 第二条目录offset = 第一条目录offset204 + 96 补4 = 300
		 * ...
		 */
		writeTableRecordEntries(ttfOutputStream);

		//数据 写入 ttf
		writeFontData(woffFileStream, ttfOutputStream);
		return ttfOutputStream;
	}

	private void getHeaders(DataInputStream woffFileStream) throws IOException {
		readTableData(woffFileStream, woffHeaderFormat, woffHeaders);
	}


	/**
	 * ttf文件需要的头信息 什么偏移量乱七八糟的
	 * @param ttfOutputStream
	 * @throws IOException
	 */
	private void writeOffsetTable(ByteArrayOutputStream ttfOutputStream)
			throws IOException {
		ttfOutputStream.write(getBytes((Integer) woffHeaders.get("flavor"))); //4
		int numTables = (Integer) woffHeaders.get("numTables");
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
	}

	private void getTableRecordEntries(DataInputStream woffFileStream)
			throws IOException {
		int numTables = (Integer) woffHeaders.get("numTables");
		for (int i = 0; i < numTables; i++) {
			HashMap<String, Number> tableDirectory = new HashMap<String, Number>();
			readTableData(woffFileStream, tableRecordEntryFormat,
					tableDirectory);
			offset += 16;
			tableRecordEntries.add(tableDirectory);
		}
	}

	private void writeTableRecordEntries(ByteArrayOutputStream ttfOutputStream)
			throws IOException {
		for (HashMap<String, Number> tableRecordEntry : tableRecordEntries) {
			ttfOutputStream.write(getBytes((Integer) tableRecordEntry
					.get("tag")));
			ttfOutputStream.write(getBytes((Integer) tableRecordEntry
					.get("origChecksum")));
			ttfOutputStream.write(getBytes(offset));
			ttfOutputStream.write(getBytes((Integer) tableRecordEntry
					.get("origLength")));
			tableRecordEntry.put("outOffset", offset);
			offset += (Integer) tableRecordEntry.get("origLength");
			if (offset % 4 != 0) {
				offset += 4 - (offset % 4);
			}
		}
	}

	private void writeFontData(InputStream woffFileStream,
							   ByteArrayOutputStream ttfOutputStream) throws IOException,
			DataFormatException {
		for (HashMap<String, Number> tableRecordEntry : tableRecordEntries) {
			int tableRecordEntryOffset = (Integer) tableRecordEntry
					.get("offset");
			int skipBytes = tableRecordEntryOffset - readOffset;
			if (skipBytes > 0)
				woffFileStream.skip(skipBytes);
			readOffset += skipBytes;
			int compressedLength = (Integer) tableRecordEntry.get("compLength");
			int origLength = (Integer) tableRecordEntry.get("origLength");
			byte[] fontData = new byte[compressedLength]; //字体数据
			byte[] inflatedFontData = new byte[origLength]; //充气数据?
			int readBytes = 0;
			while (readBytes < compressedLength) {
				readBytes += woffFileStream.read(fontData, readBytes,
						compressedLength - readBytes);
			}
			readOffset += compressedLength;
			inflatedFontData = inflateFontData(compressedLength,
					origLength, fontData, inflatedFontData);
			ttfOutputStream.write(inflatedFontData);
			offset = (Integer) tableRecordEntry.get("outOffset")
					+ (Integer) tableRecordEntry.get("origLength");
			int padding = 0;
			if (offset % 4 != 0)
				padding = 4 - (offset % 4);
			ttfOutputStream.write(getBytes(0), 0, padding);
		}
	}

	private byte[] inflateFontData(int compressedLength, int origLength,
								   byte[] fontData, byte[] inflatedFontData) {
		if (compressedLength != origLength) {
			Inflater decompressor = new Inflater();
			decompressor.setInput(fontData, 0, compressedLength);
			try {
				decompressor.inflate(inflatedFontData, 0, origLength);
			} catch (DataFormatException e) {
				throw new RuntimeException("Malformed woff file");
			}
		} else
			inflatedFontData = fontData;
		return inflatedFontData;
	}

	private byte[] getBytes(int i) {
		return ByteBuffer.allocate(4).putInt(i).array();
	}

	private byte[] getBytes(short h) {
		return ByteBuffer.allocate(2).putShort(h).array();
	}

	private void readTableData(DataInputStream woffFileStream,
							   LinkedHashMap<String, Integer> formatTable,
							   HashMap<String, Number> table) throws IOException {
		Iterator<String> headerKeys = formatTable.keySet().iterator();
		while (headerKeys.hasNext()) {
			String key = headerKeys.next();
			int size = formatTable.get(key);
			if (size == 2) {
				table.put(key, woffFileStream.readUnsignedShort());
			} else if (size == 4) {
				int offset = woffFileStream.readInt();
				table.put(key, offset);
			}

			readOffset += size;
		}
		System.out.println(table);
	}

}
