package com.dinglicom.clouder.xml;

public class DataCell implements Comparable<DataCell> {
	@Override
	public int compareTo(DataCell o) {
		return m_id - o.getId();
	}
	@Override
	public String toString() {
		return "DataCell [name=" + m_name + ", id=" + m_id + "]";
	}
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	public int getId() {
		return m_id;
	}
	public void setId(int id) {
		m_id = id;
	}
	
	private String m_name = null;
	private int m_id = -1;

}
