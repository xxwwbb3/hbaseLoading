package com.dinglicom.clouder.xml;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class IndexNode {
	@Override
	public String toString() {
		return "IndexNode BEGIN [cqLists=" + m_cqSets + " IndexNode END.]\n";
	}
	public Set<CQNode> getCqSets() {
		return m_cqSets;
	}
	public void addCQ(CQNode cqNode) {
		m_cqSets.add(cqNode);
	}
	public void map(SourceDataNode sourceDataNode) {
		for(CQNode cqNode: m_cqSets) {
			cqNode.map(sourceDataNode);
		}
	}
	
	public void print() {
		System.out.print("Index index BEGIN : \n");
		for(CQNode cqNode:m_cqSets) {
			System.out.print(cqNode.getName() + "\t");
			cqNode.print();
		}
		System.out.println("Index Index END.\n");
	}
	
	private Set<CQNode> m_cqSets = Collections.synchronizedSortedSet(new TreeSet<CQNode>());
}
