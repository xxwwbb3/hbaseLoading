package com.dinglicom.clouder.xml;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class CFNode {
	@Override
	public String toString() {
		return "CFNode BEGIN [name=" + m_name + ", cqLists=" + m_cqSets + " CFNode END ]\n";
	}
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	public Set<CQNode> getCqSets() {
		return m_cqSets;
	}
	public void addCQ(CQNode cqNode) {
		m_cqSets.add(cqNode);
	}
	public void map(SourceDataNode sourceDataNode){
		for(CQNode cqNode: m_cqSets) {
			cqNode.map(sourceDataNode);
		}
	}
	
	public void print() {
		System.out.println("CF index BEGIN :");
		for(CQNode cqNode:m_cqSets) {
			System.out.print(cqNode.getName() + "\t");
			cqNode.print();
		}
		System.out.println("CF Index END.");
	}
	
	private String m_name = null;
	private Set<CQNode> m_cqSets = Collections.synchronizedSortedSet(new TreeSet<CQNode>());

}
