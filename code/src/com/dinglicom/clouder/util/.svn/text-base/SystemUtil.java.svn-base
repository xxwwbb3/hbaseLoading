package com.dinglicom.clouder.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 系统环境变量信息公共类
 * 
 * @author
 * 
 */
public class SystemUtil {
	private final static Logger LOG = Logger.getLogger(SystemUtil.class);
	
	static{
		LOG.warn("system's user.dir=" + System.getProperty("user.dir"));
	}
	/**
	 * MapReduce运行程序的路径的别名，该变量存放在系统环境变量中
	 */
	public static final String CONST_MR_PATH_ENV = "MR_PATH";
	
	public static final String CONST_CONFIG_PATH_PREFIX = "/home/viewer/cloudil-mcc/etc/subject/config/";
	
	/**
	 * 
	 * @return 用户工作目录
	 */
	public static String getUserDir() {
		String userDir = System.getProperty("user.dir");

		int index = userDir.indexOf("/bin");
		if (index > 0) {
			userDir = userDir.substring(0, index);
		}

		return userDir;
	}

	/**
	 * 判断运行系统是否windows系统
	 * @return
	 */
	public static boolean isWindows()
	{
		String os = System.getProperty("os.name");
		
		if (null != os && os.toLowerCase().startsWith("windows")) {
			return true;
		}
		
		return false;
	}
	    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getUserDir());
	}

	/**
	 * 
	 * @return
	 */
	public static String getSystemConfigPath() {
        Map<String, String> envMap = System.getenv();
        String home = envMap.get(CONST_MR_PATH_ENV);
        if (StringUtils.isEmpty(home))
        {
        	LOG.error("no get " + CONST_MR_PATH_ENV);

        	home = CONST_CONFIG_PATH_PREFIX;
        }
        else
        {
            return home + "/";
        }
        
        LOG.debug("Config path : " + home);
        
        return home;
	}

}
