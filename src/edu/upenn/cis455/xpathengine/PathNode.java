package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;

public class PathNode {
	String queryId;
	String nodeName;
	int position;
	int relativePos;
	int level;
	ArrayList<ExpressionTree> filters;
	// can contain multiple if nested path in filter
	ArrayList<PathNode> nextPathNodeSet; 
	
	public PathNode(String nodeName, String queryId, int position, int relativePos, int level) {
		this.nodeName = nodeName;
		this.queryId = queryId;
		this.position = position;
		this.relativePos = relativePos;
		this.level = level;
		filters = new ArrayList<ExpressionTree>();
		nextPathNodeSet = new ArrayList<PathNode>();
	}
	
	public void addNextPathNode(PathNode pn) {
		nextPathNodeSet.add(pn);
	}
	
	public void addfilter(ExpressionTree et) {
		filters.add(et);
	}
	
	
}
