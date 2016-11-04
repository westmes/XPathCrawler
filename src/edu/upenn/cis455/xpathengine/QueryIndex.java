package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryIndex {
	HashMap<String, ArrayList<PathNode>> candidate;
	HashMap<String, ArrayList<PathNode>> wait;
	
	public QueryIndex() {
		candidate = new HashMap<String, ArrayList<PathNode>>();
		wait = new HashMap<String, ArrayList<PathNode>>();
	}

	public void addToWait(String nodeName, PathNode pn) {
		ArrayList<PathNode> list = wait.get(nodeName);
		if (list == null) {
			list = new ArrayList<PathNode>();
		}
		list.add(pn);
		wait.put(nodeName, list);
	}

	public void addToCandidate(String nodeName, PathNode pn) {
		ArrayList<PathNode> list = candidate.get(nodeName);
		if (list == null) {
			list = new ArrayList<PathNode>();
		}
		if (!list.contains(pn)) {
			list.add(pn);
			candidate.put(nodeName, list);
		}
	}
	
}
