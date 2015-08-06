package com.dinglicom.clouder.xml;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SourceDataNode {
	@Override
	public String toString() {
		return "SourceDataNode [m_name=" + m_name + ", fields="
				+ m_fields + "]";
	}

	public SourceDataNode(String name) {
		m_name = name;
	}
	public String getM_name() {
		return m_name;
	}

	public void putM_dataCellMap(String name, DataCell dataCell) {
		m_fields.add(dataCell);
	}
	public int getCellCount(){
		return m_fields.size();
	}
	public void printSet() {
		for(DataCell dataCell : m_fields) {
			System.out.println("id = " + dataCell.getId() +"\tname = " + dataCell.getName());
		}
	}

	public int getCellIndex(String fieldName) {
		int i=0;
		Iterator<DataCell> itor = m_fields.iterator();  
		while (itor.hasNext()) {
			if (fieldName.equals(itor.next().getName())) {
				break;
			}
			i++;
		}
		if (m_fields.size() <= i) {
			return -1;
		}
		return i;
	}
	
	private String m_name = null;
	private Set<DataCell> m_fields = Collections.synchronizedSortedSet(new TreeSet<DataCell>());
	
	public static void main(String [] args) {
		SourceDataNode sourceDataNode = new SourceDataNode("xu");
		DataCell dataCell = new DataCell();
		dataCell.setId(1);
		dataCell.setName("beijing");
		DataCell dataCell3 = new DataCell();
		dataCell3.setId(10);
		dataCell3.setName("xian");
		DataCell dataCell2 = new DataCell();
		dataCell2.setId(2);
		dataCell2.setName("shanghai");
		sourceDataNode.putM_dataCellMap("beijing", dataCell);
		sourceDataNode.putM_dataCellMap("xian", dataCell3);
		sourceDataNode.putM_dataCellMap("shanghai", dataCell2);
		sourceDataNode.printSet();
		System.out.println("shanghai index = " + sourceDataNode.getCellIndex("shanghai"));
	}
}
