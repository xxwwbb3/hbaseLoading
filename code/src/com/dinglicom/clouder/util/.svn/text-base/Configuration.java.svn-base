package com.dinglicom.clouder.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dinglicom.clouder.util.Resource.RESOURCE_TYPE;

public class Configuration {
	public Configuration(RESOURCE_TYPE resourceType, String path) {
		try {
			ins = new Resource(resourceType, path).getInputStream();
			init(ins);
		} catch (IOException e) {
			LOG.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在 path = " + path, e);
		}
	}

	public Configuration(String filePath) {
		propertie = new Properties();
		try {
			ins = new FileInputStream(filePath);
			init(ins);
		} catch (FileNotFoundException ex) {
			LOG.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在 path = " + filePath, ex);
		}
	}
	
	public void init(InputStream ins) {
		propertie = new Properties();
		try {
			propertie.load(ins);
			ins.close();
		} catch (FileNotFoundException ex) {
			LOG.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在", ex);
		} catch (IOException ex) {
			LOG.error("装载文件--->失败!", ex);
		}
	}

	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}
	
	private final static Logger LOG = Logger.getLogger(Configuration.class);
	private Properties propertie;
	private InputStream ins;
	
	public static void main(String[] args) {
		String fileName = "/home/xwb/Desktop/csv2hbase/config/systemConfig.ini";
//		Configuration rc = new Configuration(fileName);
//		Configuration rc = new Configuration(RESOURCE_TYPE._CLASSPATH, ConstString.SYSTEM_CONFIG_INI);
		Configuration rc = new Configuration(RESOURCE_TYPE._FILE, fileName);

		String inputDir = rc.getValue("inputDir");
		int threadNum = Integer.valueOf(rc.getValue("threadNum"));

		System.out.println("inputDir = " + inputDir);
		System.out.println("threadNum = " + threadNum);

	}

}
