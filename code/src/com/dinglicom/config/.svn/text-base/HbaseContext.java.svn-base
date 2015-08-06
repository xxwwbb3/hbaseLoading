package com.dinglicom.config;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.dinglicom.Scheduling.Scheduling;
import com.dinglicom.clouder.util.IniConfig;
import com.dinglicom.clouder.util.Resource.RESOURCE_TYPE;
import com.dinglicom.clouder.util.Configuration;
import com.dinglicom.clouder.xml.ConstString;
import com.dinglicom.clouder.xml.HbaseTableConfig;
import com.dinglicom.clouder.xml.SourceDataConfig;
import com.dinglicom.clouder.xml.SourceDataNode;
import com.dinglicom.clouder.xml.SourceDataTypeNode;

public class HbaseContext {
	private HbaseContext () {
		LOG.info("HbaseContext construct.");
	}
	public synchronized static HbaseContext getInstance() {
		if (null == m_instance) {
			LOG.info("getInstance()");
			m_instance = new HbaseContext();
			init();
		}
		return m_instance;
	}
	public SourceDataConfig getSourceDataConf() {
		return m_sourceDataConf;
	}
	public HbaseTableConfig getHbaseDefine() {
		return m_hbaseDefine;
	}
	public IniConfig getConstIni() {
		return m_constIni;
	}
	public Configuration getSystemConfigIni() {
		return m_systemConfig;
	}
	private static void init(){
		LOG.info("init() BEGIN");
		m_systemConfig = new Configuration(RESOURCE_TYPE._FILE, Scheduling.CONFIG_PATH+ConstString.SYSTEM_CONFIG_INI);
//		m_systemConfig = new Configuration(RESOURCE_TYPE._FILE, "/home/xwb/Desktop/csv2hbase/etc/"+ConstString.SYSTEM_CONFIG_INI);
		String csvConfigPath = m_systemConfig.getValue(ConstString.CSV_CONFIG);
		if (fileCheck(csvConfigPath)) {
			LOG.error("access csvConfigPath [" + csvConfigPath + "] fail!", new FileNotFoundException());
		}
		if (!csvConfigPath.endsWith(File.separator)) {
			csvConfigPath = csvConfigPath + File.separator;
		}
		m_sourceDataConf = new SourceDataConfig(RESOURCE_TYPE._FILE, csvConfigPath+ConstString.SOURCE_DATA_CONFIG_XML);
		m_hbaseDefine = new HbaseTableConfig(RESOURCE_TYPE._FILE, csvConfigPath+ConstString.HBASE_CONFIG_XML);
		m_constIni = new IniConfig(RESOURCE_TYPE._FILE, csvConfigPath+ConstString.CONST_CONFIG_INI);
		
		LOG.info("init() END");
	}
	
	private static boolean fileCheck(String filePath) {
		File csvConfigFile = new File(filePath);
		return filePath.isEmpty() || !csvConfigFile.exists() || !csvConfigFile.isDirectory()
				|| !csvConfigFile.canRead();
	}
	
	private final static Logger LOG = Logger.getLogger(HbaseContext.class); 
	private static HbaseContext m_instance = null;
	private static SourceDataConfig m_sourceDataConf = null;
	private static HbaseTableConfig m_hbaseDefine = null;
	private static IniConfig m_constIni = null;
	private static Configuration m_systemConfig = null;
	
	public static void main(String [] args) {
		try {
			HbaseContext hbaseContext = HbaseContext.getInstance();
			SourceDataConfig sourceDataConf = hbaseContext.getSourceDataConf();
			HbaseTableConfig hbaseConfig = hbaseContext.getHbaseDefine();
			IniConfig constIni = hbaseContext.getConstIni();
			SourceDataTypeNode node = hbaseConfig.getDataTypeNode("LTE_XDR_ZC",	"gbiups2gpaging");
			System.out.println("Const  [GLOBAL].MACHINE_ID : " + constIni.get("GLOBAL", "MACHINE_ID"));
			System.out.println("------------- HBase Config Info : ---------------------------");
			System.out.println("LTE_XDR_ZC:gbiups2gpaging--\n" + node);
			SourceDataNode sourceDataNode = sourceDataConf.getSourceDataNode("gbiups2gpaging");
			System.out.println("------------- Source Config Info : ---------------------------");
			System.out.println("gbiups2gpaging--\n" + sourceDataNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
