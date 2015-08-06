package com.dinglicom.clouder.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

public final class FileUtil {
	public final static Logger LOG = Logger.getLogger(FileUtil.class);
	public final static String FILE_SPLIT_LINUX = "/";
	public final static String FILE_SPLIT_WINDOWS = "\\";

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			LOG.info("所删除的文件不存在！");
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			LOG.info("所删除的文件不存在！");
		}
	}

	public static void mvFile(String filePath, String newFilePath) {
		try {
			Runtime.getRuntime().exec("mv " + filePath + " "+newFilePath);
		} catch (IOException e) {
			LOG.error("mv "+filePath+" "+newFilePath,e);
		}
		/*
		File file = new File(filePath);
		File newFile = new File(newFilePath + file.getName());
		if (file.renameTo(newFile)) {
		} else {
			LOG.error(filePath + " move " + newFilePath + " error!");
		}*/
	}

	public static String getDirSubPath(String... dirs) {
		if (dirs.length < 2) {
			throw new RuntimeException("获取子目录失败！");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dirs[0]);
		if (!(dirs[0].endsWith(FILE_SPLIT_LINUX) || dirs[0]
				.endsWith(FILE_SPLIT_WINDOWS))) {
			sb.append(FILE_SPLIT_LINUX);
		}
		for (int i = 1; i < dirs.length - 1; i++) {
			if (StringUtil.isNotEmpty(dirs[i])) {
				sb.append(dirs[i]);
				sb.append(FILE_SPLIT_LINUX);
			}
		}
		sb.append(dirs[dirs.length - 1]);
		return sb.toString();
	}

	public static void createDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	/**
	 * 生成本地文件
	 * 
	 * @param content
	 * @param fileName
	 * @param localDir
	 */
	public static void generateLocalFile(String content, String fileName,
			String localDir) {
		BufferedOutputStream outBuff = null;
		try {
			outBuff = new BufferedOutputStream(new FileOutputStream(
					getDirSubPath(localDir, fileName)));
			byte[] bytes = content.getBytes();
			outBuff.write(bytes, 0, bytes.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outBuff.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 获取子文件
	 * 
	 * @param dir
	 * @return
	 */
	public static String[] getSubFile(String dir) {
		File parentDir = new File(dir);
		return parentDir.list();
	}

	/**
	 * 获取没有扩展名文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNameNotExtension(String fileName) {
		if (StringUtil.isEmpty(fileName)) {
			return "";
		} else {
			if (fileName.indexOf(".") >= 0) {
				return fileName.substring(0, fileName.indexOf("."));
			}
		}
		return fileName;
	}

	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return "";
		} else {
			if (filePath.lastIndexOf(FILE_SPLIT_LINUX) >= 0) {
				return filePath.substring(
						filePath.lastIndexOf(FILE_SPLIT_LINUX) + 1,
						filePath.length());
			}
			if (filePath.lastIndexOf(FILE_SPLIT_WINDOWS) >= 0) {
				return filePath.substring(
						filePath.lastIndexOf(FILE_SPLIT_WINDOWS) + 1,
						filePath.length());
			}
		}
		return filePath;

	}

	/**
	 * 是否是目录
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isDir(String filePath) {
		File file = new File(filePath);
		return file.isDirectory();
	}

	/**
	 * 是否存在文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 获取上级目录路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getPDirPath(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return "";
		} else {
			if (filePath.lastIndexOf(FILE_SPLIT_LINUX) >= 0) {
				return filePath.substring(0,
						filePath.lastIndexOf(FILE_SPLIT_LINUX));
			}
			if (filePath.lastIndexOf(FILE_SPLIT_WINDOWS) >= 0) {
				return filePath.substring(0,
						filePath.lastIndexOf(FILE_SPLIT_WINDOWS));
			}
		}
		return filePath;

	}

	/**
	 * 获取表名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getTableName(String fileName) {
		String[] tmp = fileName.split("-");
		return tmp[2];
	}

	/**
	 * 判断是否为空文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isEmptyFile(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return true;
		} else {
			if (!exists(filePath)) {
				return true;
			} else {
				File file = new File(filePath);
				return file.length() <= 0;
			}
		}

	}

}
