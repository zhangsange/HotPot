package com.hotpot.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {

	/**
	 * 如果文件夹不存在的话创建文件夹
	 */
	public static void createDirs(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	/**
	 * 删除文件夹中的文件
	 */
	public static void delectDirs(String path) {
		File file = new File(path);
		if (!file.isDirectory())
			file.delete();
		else {
			File[] fileSet = file.listFiles();
			for (File delFile : fileSet)
				delectDirs(delFile.getPath());
		}
	}

	/**
	 * 读取数据流
	 * 
	 * @param in
	 *            数据流
	 * @return 读取的数据串
	 * @throws IOException
	 */
	public synchronized static String read(InputStream in) {
		BufferedReader reader = null;
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(in), 1000);
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (in != null)
					in.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * 将图片内容解析成字节数组
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	/**
	 * 生成一个不重复的key值
	 */
	public static String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

}
