package com.dinglicom.clouder.xml;

import java.util.HashMap;
import java.util.Map;

public class TableNode {
	public TableNode(String mainTableName, String indexTableName) {
		m_mainTableName = mainTableName;
		m_indexTableName = indexTableName;
	}

	public String getNameSpace() {
		return m_mainTableName;
	}
	public void setNameSpace(String mainTableName) {
		m_mainTableName = mainTableName;
	}
	public String getIndexTableName() {
		return m_indexTableName;
	}
	public void setIndexTableName(String indexTableName) {
		m_indexTableName = indexTableName;
	}
	public Map<String, SourceDataTypeNode> getDataTypeMap() {
		return m_dataTypeMap;
	}
	
	public void putDataTypeNode(String dataTypeName, SourceDataTypeNode sourceDataTypeNode) {
		m_dataTypeMap.put(dataTypeName, sourceDataTypeNode);
	}
	
	// main table
	private String m_mainTableName = null;
	// index table name
	private String m_indexTableName = null;
	//key :data_type name
	private Map<String, SourceDataTypeNode> m_dataTypeMap = new HashMap<String, SourceDataTypeNode>();
}
