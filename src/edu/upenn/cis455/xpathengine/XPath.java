package edu.upenn.cis455.xpathengine;

import java.util.LinkedList;
import java.util.Queue;

public class XPath {
	public String qid;
	public PathNode head;
	
	public XPath(String qid) {
		this.qid = qid;
	}
	
	public void printXPath() {
		Queue<PathNode> queue = new LinkedList<PathNode>();
		if (head != null) {
			queue.offer(head);
			while (!queue.isEmpty()) {
				int len = queue.size();
				for (int i=0; i<len; i++) {
					PathNode pn = queue.poll();
					String filter = "";
					for (ExpressionTree et: pn.filters) {
						filter += et.type + ": " + et.name + "=" + et.value + "; ";
					}
					System.out.print(pn.nodeName + "(" +  filter + ")" + "\t");
					for (PathNode node :pn.nextPathNodeSet) {
						queue.offer(node);
					}
				}
				System.out.println();
			}
			
		} else {
			System.out.println("Path is empty.");
		}
	}
	
}
