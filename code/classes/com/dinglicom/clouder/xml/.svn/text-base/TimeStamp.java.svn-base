package com.dinglicom.clouder.xml;

import org.apache.log4j.Logger;

import com.dinglicom.clouder.xml.FieldNode.Option;

public class TimeStamp {
	public FieldNode getFieldNode() {
		return m_fieldNode;
	}
	public void setFieldNode(FieldNode fieldNode) {
		m_fieldNode = fieldNode;
	}
	public int getFieldIndex() {
		return m_fieldIndex;
	}
	public void map(SourceDataNode sourceDataNode) {
		int index = -1;
		if (Option.CONST != m_fieldNode.getOption()) {
			index = sourceDataNode.getCellIndex(m_fieldNode.getFieldName());
			if (-1 == index) {
				LOG.fatal(m_fieldNode.getFieldName() + "not define in data source xml!");
			}
		}
		m_fieldIndex = index;
	}
	
	public void print() {
		System.out.println("TimeStamp Index BEGIN : ");
		System.out.print("fieldIndex size = 1\n[");
		System.out.print(m_fieldIndex);
		System.out.println("]\nTimeStamp Index END.\n");
	}

	@Override
	public String toString() {
		return "TimeStamp BEGIN [\n\tfieldNode=" + m_fieldNode + " TimeStamp END.]\n";
	}
	
	private final static Logger LOG = Logger.getLogger(TimeStamp.class);
	private FieldNode m_fieldNode = null;
	private int m_fieldIndex = -1;
}
