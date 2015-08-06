package com.dinglicom.clouder.xml;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.dinglicom.clouder.xml.FieldNode.Option;

public class CQNode implements Comparable<CQNode> {
	@Override
	public int compareTo(CQNode o) {
		return m_orderId - o.getOrderId();
	}
	public void addField(FieldNode fieldNode) {
		m_fields.add(fieldNode);
	}
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	public String getSeperator() {
		return m_seperator;
	}
	public void setSeperator(String seperator) {
		m_seperator = seperator;
	}
	public int getOrderId() {
		return m_orderId;
	}
	public void setOrderId(int orderId) {
		m_orderId = orderId;
	}
	public String getTextType() {
		return m_textType;
	}
	public void setTextType(String textType) {
		m_textType = textType;
	}
	public int [] getFieldIndex() {
		return m_fieldIndex;
	}

	public void map(SourceDataNode sourceDataNode) {
		m_fieldList = new FieldNode[m_fields.size()];
		m_fieldIndex = new int[m_fields.size()];
		int i=0;
		for(FieldNode fieldNode: m_fields) {
			int index = -1;
			if (Option.CONST != fieldNode.getOption()) {
				index = sourceDataNode.getCellIndex(fieldNode.getFieldName());
				if (-1 == index) {
					LOG.fatal(fieldNode.getFieldName() + "not define in data source xml!");
				}
			}
			
			m_fieldIndex[i] = index;
			m_fieldList[i++] = fieldNode;
		}
	}
	
	public FieldNode [] getFieldList() {
		return m_fieldList;
	}
	
	public void print() {
		System.out.print("fieldIndex size = " + m_fieldIndex.length + "\n[");
		for(Integer i:m_fieldIndex) {
			System.out.print(i + ", ");
		}
		System.out.print("]\n");
	}
	
	@Override
	public String toString() {
		return "\n\tCQNode BEGIN [fields=\n" + m_fields + ", name=" + m_name + ", seperator="
				+ m_seperator + ", orderId=" + m_orderId + ", textType=" + m_textType
				+ " CQNode END.]";
	}
	
	private final static Logger LOG = Logger.getLogger(CQNode.class);
	private Set<FieldNode> m_fields = Collections.synchronizedSortedSet(new TreeSet<FieldNode>());
	private FieldNode[] m_fieldList = null;
	private int [] m_fieldIndex = null;
	private String m_name = null;
	private String m_seperator = null;
	private int m_orderId = -1;
	private String m_textType = null;
}
