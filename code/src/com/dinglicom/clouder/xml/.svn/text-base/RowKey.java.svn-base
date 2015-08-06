package com.dinglicom.clouder.xml;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.dinglicom.clouder.xml.FieldNode.Option;

public class RowKey {
	public Set<FieldNode> getFields() {
		return m_fields;
	}
	public void addField(FieldNode field) {
		m_fields.add(field);
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
		System.out.println("\nRowKey Index BEGIN : ");
		System.out.print("fieldIndex size = " + m_fieldIndex.length + "\n[");
		for(Integer i:m_fieldIndex) {
			System.out.print(i + ", ");
		}
		System.out.println("]\nRowKey Index END.\n");
	}

	@Override
	public String toString() {
		return "RowKey BEGIN[\n\tfields=" + m_fields + "RowKey END.]\n";
	}
	
	private final static Logger LOG = Logger.getLogger(RowKey.class);
	private Set<FieldNode> m_fields = Collections.synchronizedSortedSet(new TreeSet<FieldNode>());
	private int [] m_fieldIndex = null;
	private FieldNode[] m_fieldList = null;
}
