package com.dinglicom.clouder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.dinglicom.clouder.util.Resource;
import com.dinglicom.clouder.util.StringUtil;
import com.dinglicom.clouder.util.XmlUtil;
import com.dinglicom.clouder.util.Resource.RESOURCE_TYPE;

public class SourceDataConfig {
	public SourceDataConfig (RESOURCE_TYPE resourceType, String path) {
		LOG.info("SourceDataConfig construct");
		m_fieldMap = new HashMap<String, SourceDataNode>();
		try {
			init(new Resource(resourceType, path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void init(Resource rs) throws IOException {
		init(rs.getInputStream());
	}

	private void init(InputStream in) throws IOException {
		Document doc = XmlUtil.getDocument(in);
		if (doc == null) {
			return;
		}
		parseXml(doc);
	}
	@SuppressWarnings("unchecked")
	private void parseXml(Document doc) throws IOException {
		Element root = doc.getRootElement();
		Element type = root.element(ConstString.ATTRIBUTE_FIELD_TYPE);
		m_fileFormatType = type.getTextTrim();
		Element encode = root.element(ConstString.ELEMENT_CONST_FILE_ENCODE);
		m_encode = encode.getTextTrim();
		Element separator = root.element(ConstString.ELEMENT_CONST_SEPARATOR);
		m_separator = separator.getTextTrim();
		Element qualifier = root.element(ConstString.ELEMENT_CONST_QUALIFIER);
		m_qualifier = qualifier.getTextTrim();
		List<Element> dataTypes = root.elements(ConstString.ELEMENT_CONST_DATA_TYPE);

		for (Element dataType : dataTypes) {
			String dataTypeName = dataType.attributeValue(ConstString.ELEMENT_CONST_NAME);
			addDataType(dataTypeName, dataType);
		}
	}
	@SuppressWarnings("unchecked")
	private void addDataType(String dataTypeName, Element dataType) {
		List<Element> fields = dataType.elements(ConstString.ELEMENT_CONST_FIELD);
		SourceDataNode sourceDataNode = new SourceDataNode(dataTypeName);
		for (Element field : fields) {
			DataCell dataCell = new DataCell();
			String dataCellName = field.attributeValue(ConstString.ELEMENT_CONST_NAME);
			String dataCellId = field.attributeValue(ConstString.ELEMENT_CONST_ID);
			if (StringUtil.isNotEmpty(dataCellId)) {
				dataCell.setId(Integer.valueOf(dataCellId));
			}
			dataCell.setName(dataCellName);
			sourceDataNode.putM_dataCellMap(dataCellName, dataCell);
		}
		m_fieldMap.put(dataTypeName, sourceDataNode);
	}
	
	public SourceDataNode getSourceDataNode(String dataTypeName) {
		return m_fieldMap.get(dataTypeName);
	}
	
	public String getM_fileFormatType() {
		return m_fileFormatType;
	}

	public String getM_encode() {
		return m_encode;
	}

	public String getM_separator() {
		return m_separator;
	}

	public String getM_qualifier() {
		return m_qualifier;
	}
	
	@Override
	public String toString() {
		return "SourceDataConfig [m_fileFormatType=" + m_fileFormatType
				+ ", m_encode=" + m_encode + ", m_separator=" + m_separator
				+ ", m_qualifier=" + m_qualifier + ", m_fieldMap=" + m_fieldMap
				+ "]";
	}
	
	private final static Logger LOG = Logger.getLogger(SourceDataConfig.class);
	private String m_fileFormatType = null;
	private String m_encode = null;
	private String m_separator = null;
	private String m_qualifier = null;
	private Map<String,SourceDataNode> m_fieldMap = null;
}
