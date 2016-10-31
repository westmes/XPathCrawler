package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;

public class PathNode {
	String queryId;
	int position;
	int relativePos;
	int level;
	ArrayList<ExpressionTree> filters;
	ArrayList<PathNode> nextPathNodeSet; 
	
	public PathNode() {
		filters = new ArrayList<ExpressionTree>();
		nextPathNodeSet = new ArrayList<PathNode>();
	}
	
	public void addNextPathNode(PathNode pn) {
		nextPathNodeSet.add(pn);
	}
}
