package com.dinglicom.ExecuteOperator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dinglicom.clouder.util.FileUtil;
import com.dinglicom.clouder.util.Configuration;
import com.dinglicom.clouder.xml.ConstString;
import com.dinglicom.config.HbaseContext;
import com.dinglicom.hbase.HbaseOperator;

public class ThreadExecuter implements Runnable {
	private final static Logger LOG = Logger.getLogger(ThreadExecuter.class);
	private static String m_outputDir=null;
	private static String m_errorDir=null;
	private static boolean m_isDelete=false;
	static {
		Configuration systemIniConfig = HbaseContext.getInstance().getSystemConfigIni();
		m_outputDir = systemIniConfig.getValue(ConstString.OUTPUT_DIR);
		m_errorDir = systemIniConfig.getValue(ConstString.ERROR_DIR);
		m_isDelete = Boolean.valueOf(systemIniConfig.getValue(ConstString.IS_DELETE));
		
		if (!m_outputDir.endsWith(File.separator)) {
			m_outputDir += File.separator;
		}
		if (!m_errorDir.endsWith(File.separator)) {
			m_errorDir += File.separator;
		}
		
		File outputDirFile = new File(m_outputDir);
		if(!m_isDelete && (fileCheck(m_outputDir, outputDirFile))){
			LOG.error("access outputDirFile [" + m_outputDir + "] fail!", new FileNotFoundException());
		}
		File errorDirFile = new File(m_errorDir);
		if(fileCheck(m_errorDir, errorDirFile)){
			LOG.error("access errorDirFile [" + m_errorDir + "] fail!", new FileNotFoundException());
		}
		
		LOG.info("outputDir = " + m_outputDir);
		LOG.info("errorDir = " + m_errorDir);
	}

	public ThreadExecuter(Set<String> operatorFileQueue, String fileName) {
		LOG.debug("ThreadExecuter(): " + m_fileName);
		m_fileName = fileName;
		m_operatorFileQueue = operatorFileQueue;
	}
	
	private static boolean fileCheck(String filePaht, File file) {
		return filePaht.isEmpty() || !file.exists() || !file.isDirectory() || !file.canWrite();
	}

	@Override
	public void run() {
		LOG.debug("ThreadExecuter::run(): " + m_fileName);
		boolean ret = HbaseOperator.process(m_fileName);
		if (ret) {
			if (m_isDelete) {
				FileUtil.deleteFile(m_fileName);
			} else {
				FileUtil.mvFile(m_fileName, m_outputDir);
			}
		} else {
			FileUtil.mvFile(m_fileName, m_errorDir);
		}
		m_operatorFileQueue.remove(m_fileName);
	}

	private String m_fileName = null;
	private Set<String> m_operatorFileQueue = null;
}