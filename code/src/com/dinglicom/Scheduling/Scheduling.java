package com.dinglicom.Scheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;

import com.dinglicom.ExecuteOperator.ThreadExecuter;
import com.dinglicom.config.HbaseContext;
import com.dinglicom.clouder.util.Configuration;
import com.dinglicom.clouder.xml.ConstString;

public class Scheduling {
	public static String CONFIG_PATH;
	private final static Logger LOG = Logger.getLogger(Scheduling.class);
	private static Queue<String> m_waitingFileQueue = new LinkedList<String>();
	private static Set<String> m_processingSet = new HashSet<String>();
	private static Set<String> m_operatorFileQueue = new CopyOnWriteArraySet<String>();
	
	public Scheduling(String configPaht) {
		if (!configPaht.endsWith(File.separator)) {
			CONFIG_PATH = configPaht + File.separator;
		} else {
			CONFIG_PATH = configPaht;
		}
		inti();
		checkConfig();
		generatorLscmd();
	}
	private void checkConfig() {
		if (!m_inputDir.endsWith(File.separator)) {
			m_inputDir += File.separator;
		}
		
		File inputDirFile = new File(m_inputDir);
		if(fileCheck(m_inputDir, inputDirFile)){
			LOG.error("access inPutDir [" + m_inputDir + "] fail!", new FileNotFoundException());
		}

		if (m_threadNum < 0 || m_threadNum > 20) {
			LOG.fatal("threadNum = " + m_threadNum + " is illogical!(0~20)");
		}
		
		if (m_findFileInterval < 0 || m_findFileInterval > Integer.MAX_VALUE) {
			LOG.fatal("findFileInterval = " + m_findFileInterval + " is illogical!");
		}
		
		if (m_quequeFullWaitTime < 0 || m_quequeFullWaitTime > Integer.MAX_VALUE) {
			LOG.fatal("quequeFullWaitTime = " + m_quequeFullWaitTime + " is illogical!");
		}
		
	}

	private boolean fileCheck(String filePath, File file) {
		return filePath.isEmpty() || !file.exists() || !file.isDirectory()
				|| !file.canWrite();
	}
	
	private void inti() {
		Configuration systemIniConfig = HbaseContext.getInstance().getSystemConfigIni();
		m_inputDir = systemIniConfig.getValue(ConstString.INPUT_DIR);
		m_threadNum = Integer.valueOf(systemIniConfig.getValue(ConstString.THREAD_NUM));
		m_fileNameFilter = systemIniConfig.getValue(ConstString.FILE_NAME_FILTER);
		m_findFileInterval = Integer.valueOf(systemIniConfig.getValue(ConstString.FIND_FILE_INTERVAL));
		m_quequeFullWaitTime = Integer.valueOf(systemIniConfig.getValue(ConstString.QUEQUE_FULL_WAIT_TIME));
		LOG.info("inputDir = " + m_inputDir);
		LOG.info("threadNum = " + m_threadNum);
		LOG.info("fileNameFilter = " + m_fileNameFilter);
		LOG.info("findFileInterval = " + m_findFileInterval);
	}

	/**
	 * 探测新文件
	 * 
	 * @param inputDir
	 */
	private void probeNewfile(String inputDir) {
		List<String> fileNameList = new GetFileList().submitSH(m_lsCMD);
		for (String fileName : fileNameList) {
			String file = fileName;
			if (!m_processingSet.contains(file)) {
				StringBuffer sb = new StringBuffer("add filename: ");
				sb.append(file.substring(file.lastIndexOf("/")+1));
				
				if (!m_waitingFileQueue.offer(file)) {
					sb.append(" Fail");
					LOG.info(sb);
//					LOG.info("add filename: " + file.substring(file.lastIndexOf("/")+1) + " Fail");
				} else {
					sb.append(" success");
					LOG.info(sb);
//					LOG.info("add filename: " + file.substring(file.lastIndexOf("/")+1) + " success");
				}
			}
		}
	}

	public void generatorLscmd() {
		StringBuilder sb = new StringBuilder();
		sb.append("ls -tr ").append(m_inputDir);
		if (null != m_fileNameFilter) {
			sb.append("*");
			if (!m_fileNameFilter.startsWith(".")) {
				sb.append(".");
			}
			sb.append(m_fileNameFilter);
		}
		sb.append(" | head -100");
		m_lsCMD = sb.toString();
		if (null == m_lsCMD) {
			LOG.error("Not ls cmd generator.");
		} else {
			LOG.info("ls cmd[" + m_lsCMD + "]");
		}
	}

	public void schedule() {
		probeNewfile(m_inputDir);
		ScheduledExecutorService service = Executors
				.newScheduledThreadPool(m_threadNum);
		try {
			while (true) {
				if (Thread.interrupted()) {
					break;
				}
				if (m_operatorFileQueue.size() < m_threadNum) {
					String file = m_waitingFileQueue.poll();
					if (null != file) {
						m_operatorFileQueue.add(file);
						service.execute(new ThreadExecuter(m_operatorFileQueue, file));
					} else {
						m_processingSet.addAll(m_operatorFileQueue);
						probeNewfile(m_inputDir);// 如果探测文件数量为0，并且运行队列为空，可以多休息一会
						m_processingSet.clear();
						if (m_waitingFileQueue.size() <= 0) {
							Thread.sleep(m_findFileInterval);
						}
					}
				} else {
					Thread.sleep(m_quequeFullWaitTime);
				}
			}
		} catch (InterruptedException e) {
			LOG.warn("waiting interrupted:", e);
		} finally {
			service.shutdown();
		}
	}
	
	private String m_lsCMD = null;
	private String m_inputDir=null;
	private String m_fileNameFilter=null;
	private int m_threadNum=0;
	private int m_findFileInterval=0;
	private int m_quequeFullWaitTime=0;
}
