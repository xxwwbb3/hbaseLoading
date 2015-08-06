/**
 * 
 */
package com.dinglicom.clouder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 配置资源描述，用于将来配置加载的来源定义及描述
 * 暂不支持代理方式访问网络
 * @author panzhen
 */
public class Resource {
	/**
	 * 配置资源类型
	 * @author panzhen
	 *
	 */
	public enum RESOURCE_TYPE {
		_URL,//url
		_FILE,//文件系统
		_CLASSPATH,//类路径
		_SOCKET,//socket
		_DATABASE,//数据库
		_INPUTSTREAM//输入流
	}
	
	private final static String CLASSPATH = "classpath";
	private final static String FILE = "file";
	
	protected RESOURCE_TYPE resourceType = RESOURCE_TYPE._FILE;
	protected String path = null;// 资源所在机器路径
	protected String hostname = null;//资源所在机器的hostname
	protected String ip = null;//资源所在机器的IP
	protected int port = -1;//资源所在机器的端口
	
	protected String userName = null;
	protected String password = null;
	
	private InputStream inputStream = null;
	
	public Resource() {
	}
	
	public Resource(RESOURCE_TYPE resourceType, String path) {
		this.resourceType = resourceType;
		this.path = path;
	}

	public RESOURCE_TYPE getResourceType() {
		return resourceType;
	}
	public void setResourceType(RESOURCE_TYPE resourceType) {
		this.resourceType = resourceType;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	protected void validatePath() throws IOException {
		if(null == path) {
			throw new IOException("Local File path not set.");
		}
	}
	protected void validateLocalFileSystemPath(File file) throws IOException {
		if(!file.exists()) {
			throw new IOException("Local File path<" + file.getAbsolutePath() + "> not exists.");
		}
	}

	protected InputStream initLocalFileSystemInputStream() throws IOException {
		validatePath();
		File file = new File(path);
		validateLocalFileSystemPath(file);
		this.inputStream = new FileInputStream(file);
		return this.inputStream;
	}
	
	protected InputStream initClassPathInputStream() throws IOException {
		validatePath();
		if(!path.startsWith("/")) {
			path = "/" + path;
		}
		this.inputStream = this.getClass().getResourceAsStream(path);
		return this.inputStream;
	}
	
	public InputStream getInputStream() throws IOException {
		InputStream in = null;
		switch (this.resourceType) {
		case _FILE:
			in = initLocalFileSystemInputStream();
			break;
		case _CLASSPATH:
			in = initClassPathInputStream();
			break;
		//...
		default:
			throw new IOException("Not support Resource Type.");
		}
		return in;
	}

	public static RESOURCE_TYPE getResourceType(String type) throws IOException {
		if(null == type) {
			throw new IOException("Resource Type is NULL.");
		}
		if(CLASSPATH.equalsIgnoreCase(type)) {
			return RESOURCE_TYPE._CLASSPATH;
		} else if(FILE.equalsIgnoreCase(type)) {
			return RESOURCE_TYPE._FILE;
		} else {
			throw new IOException("Resource Type<" + type + "> is Not support.");
		}
	}
	
	public void close() throws IOException {
		if(null != inputStream) {
			inputStream.close();
		} else {
			throw new IOException("Not Open any Resource. inputStream is NULL.");
		}
	}
}