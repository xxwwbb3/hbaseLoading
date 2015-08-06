package com.dinglicom.clouder.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.dinglicom.clouder.util.Resource;
import com.dinglicom.clouder.util.Resource.RESOURCE_TYPE;

public class IniConfig {
	public IniConfig(RESOURCE_TYPE resourceType, String path) {
		try {
			initial(new Resource(resourceType, path).getInputStream());
		} catch (IOException e) {
			LOG.error("config parser initial error! fileName: " + path, e);
		}
	}

	public void initial(InputStream ins) {
		if (isInit()) {
			return;
		}
		LOG.info("Init ini conifg file.");
		m_editor = new IniEditor(true);
		try {
			m_editor.load(ins);
		} catch (IOException e) {
			LOG.error("load error!" + e);
		}
	}

	public String get(String section, String name) {
		return m_editor.get(section, name);
	}

	public IniEditor getConstIniEditor() {
		return m_editor;
	}

	private boolean isInit() {
		if (m_editor == null) {
			return false;
		}
		return true;
	}
	
	private final static Logger LOG = Logger.getLogger(IniConfig.class);
	private IniEditor m_editor = null;

	public static void main(String[] args) {
		IniConfig constIni = new IniConfig(RESOURCE_TYPE._CLASSPATH,
				"const.ini");
		System.out.println("CITY_CODE.519=" + constIni.get("CITY_CODE", "519"));
	}

}
