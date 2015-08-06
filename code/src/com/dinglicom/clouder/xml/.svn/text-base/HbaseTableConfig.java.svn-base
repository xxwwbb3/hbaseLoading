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
import com.dinglicom.clouder.util.Resource.RESOURCE_TYPE;
import com.dinglicom.clouder.util.StringUtil;
import com.dinglicom.clouder.util.XmlUtil;
import com.dinglicom.clouder.xml.FieldNode.Option;
import com.dinglicom.config.HbaseContext;

class Table{
	public String m_mainTale;
	public String m_indexTable;
	public Table(String mainTable, String indexTable) {
		m_mainTale = mainTable;
		m_indexTable = indexTable;
	}
};

public class HbaseTableConfig {
	public HbaseTableConfig(RESOURCE_TYPE resourceType, String path) {
		try {
			init(new Resource(resourceType, path));
			hbaseMapSource();
		} catch (IOException e) {
			LOG.error("read hbase table define file error! path:" + path, e);
		}
	}
	
	public SourceDataTypeNode getDataTypeNode(String tableName,
			String dataTypeName) {
		return m_tableMap.get(tableName).getDataTypeMap().get(dataTypeName);
	}
	
	public Table getTableName(String dataTypeName) {
		return m_dataTypeTableMap.get(dataTypeName);
	}
	
	public String getMainTableName() {
		return this.mainTableName;
	}
	
	public String getIndexTableName() {
		return this.indexTableName;
	}

	private void init(Resource rs) throws IOException {
		init(rs.getInputStream());
	}

	private void init(InputStream in) throws IOException {
		Document doc = XmlUtil.getDocument(in);
		if (doc == null) {
			LOG.error("Document is null! parser XML file error!");
			return;
		}
		parseXml(doc);
	}

	private void hbaseMapSource() {
		SourceDataConfig sourceDataConfig = HbaseContext.getInstance().getSourceDataConf();
		for(Map.Entry<String, TableNode> tableEntry:m_tableMap.entrySet()){
		     TableNode table = tableEntry.getValue();
		     Map<String, SourceDataTypeNode> dataTypeMap = table.getDataTypeMap();
		     for(Map.Entry<String, SourceDataTypeNode> datTypesEntry:dataTypeMap.entrySet()) {
		    	 SourceDataNode sourceDataNode = sourceDataConfig.getSourceDataNode(datTypesEntry.getKey());
		    	 SourceDataTypeNode sourceDataTypeNode = datTypesEntry.getValue();
		    	 dataTypeMap(sourceDataNode, sourceDataTypeNode);
		     }
		} 
	}
	
	private void dataTypeMap(SourceDataNode sourceDataNode, SourceDataTypeNode sourceDataTypeNode) {
		sourceDataTypeNode.getRowKey().map(sourceDataNode);
		sourceDataTypeNode.getTimestamp().map(sourceDataNode);
		sourceDataTypeNode.getIndexNode().map(sourceDataNode);
		sourceDataTypeNode.getcFnode().map(sourceDataNode);
	}
	
	@SuppressWarnings("unchecked")
	private void parseXml(Document doc) throws IOException {
		Element root = doc.getRootElement();
		List<Element> tables = root.elements(ConstString.ELEMENT_CONST_TABLE);

		for (Element table : tables) {
			String[] names = table.attributeValue(ConstString.ATTRIBUTE_FIELD_NAME).split(
					ConstString.ATTRIBUTE_NAMESPACE_SPLIT);
			if (names.length != 2) {
				LOG.error("name :" + table.attributeValue(ConstString.ATTRIBUTE_FIELD_NAME));
			}
			addTable(names[0]/* MAIN */, names[1]/* INDEX */, table);
			mainTableName = names[0];
			indexTableName = names[1];
		}
	}

	@SuppressWarnings("unchecked")
	private void addTable(String mainTalbeName, String indexTablename, Element tableElement) {
		TableNode tableNode = new TableNode(mainTalbeName, indexTablename);

		List<Element> dataTypeList = tableElement.elements(ConstString.ELEMENT_CONST_DATATYPE);
		for (Element dataType : dataTypeList) {
			SourceDataTypeNode dataTypeNode = new SourceDataTypeNode();
			String dataTypeName = dataType.attributeValue(ConstString.ATTRIBUTE_FIELD_NAME);
			if (tableNode.getDataTypeMap().containsKey(dataTypeName)) {
				continue;
			}

			dataTypeNode.setDataType(dataTypeName);
			Element key = dataType.element(ConstString.ELEMENT_CONST_ROWKEY);
			if (key != null) {
				addRowKey(dataTypeNode, key);
			} else {
				LOG.error("Rowkey " + ConstString.ERROR_MSG);
			}

			Element ts = dataType.element(ConstString.ELEMENT_CONST_TIMESTAMP);
			if (ts != null) {
				addTimestamp(dataTypeNode, ts);
			} else {
				LOG.error("timestamp " + ConstString.ERROR_MSG);
			}
			Element idx = dataType.element(ConstString.ELEMENT_CONST_INDEXS);
			if (idx != null) {
				addIndex(dataTypeNode, idx);
			} else {
				LOG.error("index " + ConstString.ERROR_MSG);
			}

			Element cf = dataType.element(ConstString.ELEMENT_CONST_COLUMN_FAMILY);
			if (cf != null) {
				addCF(dataTypeNode, cf);
			} else {
				LOG.error("cf " + ConstString.ERROR_MSG);
			}
			tableNode.putDataTypeNode(dataTypeName, dataTypeNode);
			Table table = new Table(mainTalbeName, indexTablename);
			m_dataTypeTableMap.put(dataTypeName, table);
		}

		m_tableMap.put(mainTalbeName, tableNode);
	}

	@SuppressWarnings("unchecked")
	private void addRowKey(SourceDataTypeNode dataTypeNode, Element key) {
		RowKey rowNode = new RowKey();
		List<Element> fields = key.elements();
		for (Element field : fields) {
			FieldNode fieldNode = parserField(field);
			rowNode.addField(fieldNode);
		}
		dataTypeNode.setRowKey(rowNode);
	}

	private void addTimestamp(SourceDataTypeNode dataTypeNode, Element key) {
		TimeStamp tmstmpNode = new TimeStamp();
		Element field = key.element(ConstString.ATTRIBUTE_FIELD);
		FieldNode fieldNode = parserField(field);
		tmstmpNode.setFieldNode(fieldNode);
		dataTypeNode.setTimestamp(tmstmpNode);
	}

	@SuppressWarnings("unchecked")
	private void addIndex(SourceDataTypeNode dataTypeNode, Element key) {
		IndexNode indexNode = new IndexNode();
		List<Element> cqs = key.elements(ConstString.ELEMENT_CONST_COLUMN_QULIFIER);
		for (Element cq : cqs) {
			CQNode cqNode = parserCQ(cq);
			indexNode.addCQ(cqNode);
		}
		dataTypeNode.setIndexNode(indexNode);
	}

	private void addCF(SourceDataTypeNode dataTypeNode, Element key) {
		CFNode cfNode = new CFNode();
		cfNode.setName(key.attributeValue(ConstString.ATTRIBUTE_FIELD_NAME));;
		Element cq = key.element(ConstString.ELEMENT_CONST_COLUMN_QULIFIER);
		CQNode cqNode = parserCQ(cq);
		cfNode.addCQ(cqNode);
		dataTypeNode.setcFnode(cfNode);
	}

	@SuppressWarnings("unchecked")
	private CQNode parserCQ(Element key) {
		CQNode cqNode = new CQNode();
		cqNode.setName(key.attributeValue(ConstString.ATTRIBUTE_FIELD_NAME));
		String orderId = key.attributeValue(ConstString.ATTRIBUTE_ORDER_ID);
		if (StringUtil.isNotEmpty(orderId)) {
			cqNode.setOrderId(Integer.valueOf(orderId));
		}
		cqNode.setSeperator(key.attributeValue(ConstString.ATTRIBUTE_SEPERATOR));
		cqNode.setTextType(key.attributeValue(ConstString.ATTRIBUTE_FIELD_TYPE));
		List<Element> fields = key.elements();
		for (Element field : fields) {
			FieldNode fieldNode = parserField(field);
			cqNode.addField(fieldNode);
		}
		return cqNode;
	}

	private FieldNode parserField(Element field) {
		FieldNode fieldNode = new FieldNode();
		String name = field.attributeValue(ConstString.ATTRIBUTE_FIELD_NAME);
		String id = field.attributeValue(ConstString.ATTRIBUTE_FIELD_ID).trim();
		String defvalue = field.attributeValue(ConstString.ATTRIBUTE_FIELD_DEFVALUE);
		String type = field.attributeValue(ConstString.ATTRIBUTE_FIELD_TYPE);
		String section = field.attributeValue(ConstString.ATTRIBUTE_FIELD_SECTION);
		String reverse = field.attributeValue(ConstString.ATTRIBUTE_FIELD_REVERSE);
		String value = field.attributeValue(ConstString.ATTRIBUTE_FIELD_VALUE);
		fieldNode.setFieldName(name);
		if (StringUtil.isNotEmpty(id)) {
			fieldNode.setOrderId(Integer.valueOf(id));
		}
		fieldNode.setFieldName(name);
		fieldNode.setDefValue(defvalue);
		if (StringUtil.isNotEmpty(value)) {
			fieldNode.setOption(Option.CONST);
			fieldNode.setConstValue(value);
		} else if (StringUtil.isNotEmpty(section)) {
			fieldNode.setOption(Option.SECTION);
			fieldNode.setSection(section);
		} else if (StringUtil.isNotEmpty(reverse)) {
			fieldNode.setOption(Option.REVERSSE);
			fieldNode.setFieldName(reverse);
		} else {
			fieldNode.setOption(Option.NORMAL);
		}
		fieldNode.setType(type);

		return fieldNode;
	}

	
	private final static Logger LOG = Logger.getLogger(HbaseTableConfig.class);
	private Map<String, TableNode> m_tableMap = new HashMap<String, TableNode>();
	private Map<String, Table> m_dataTypeTableMap = new HashMap<String, Table>();
	private String mainTableName = null;
	private String indexTableName = null;

	public static void main(String[] args) {
		try {
			HbaseTableConfig config = HbaseContext.getInstance().getHbaseDefine();
			SourceDataTypeNode node = config.getDataTypeNode("LTE_XDR_ZC",
					"gbiups2gpaging");
			node.getRowKey().print();
			node.getTimestamp().print();
			node.getIndexNode().print();
			node.getcFnode().print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
