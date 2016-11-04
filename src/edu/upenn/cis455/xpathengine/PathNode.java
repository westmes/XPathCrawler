package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;
import java.util.HashSet;

public class PathNode {
	String queryId;
	String nodeName;
	int position;
	int relativePos;
	int level;
	PathNode parent;
	ArrayList<ExpressionTree> filters;
	// can contain multiple if nested path in filter
	ArrayList<PathNode> nextPathNodeSet; 
	HashSet<PathNode> set;
	
	public PathNode(String nodeName, String queryId, int position, int relativePos, int level, PathNode parent) {
		this.nodeName = nodeName;
		this.queryId = queryId;
		this.position = position;
		this.relativePos = relativePos;
		this.level = level;
		this.parent = parent;
		this.filters = new ArrayList<ExpressionTree>();
		this.nextPathNodeSet = new ArrayList<PathNode>();
		this.set = new HashSet<PathNode>();
	}
	
	public void updateComplete() {
		if (this.parent == null) {
			return;
		}
		this.parent.set.add(this);
		if (this.parent.set.size() == this.parent.nextPathNodeSet.size()) {
			this.parent.updateComplete();
		}
	}
	
	public boolean checkValid() {
		PathNode ptr = this;
		while (ptr.parent != null) {
			if (!ptr.checkExpressionTree()) return false;
			ptr = ptr.parent;
		}
		return ptr.checkExpressionTree() & ptr.nextPathNodeSet.size() == ptr.set.size();
	}
	
	public boolean checkExpressionTree() {
		boolean isValid = true;
		for (ExpressionTree et : this.filters) {
			isValid = isValid & et.isValid;
		}
		return isValid;
	}
	
	public void addNextPathNode(PathNode pn) {
		nextPathNodeSet.add(pn);
	}
	
	public void addfilter(ExpressionTree et) {
		filters.add(et);
	}
	
	public void resetCompletion() {
		set.clear();
		for (ExpressionTree et : filters) {
			et.isValid = false;
		}
	}
}
