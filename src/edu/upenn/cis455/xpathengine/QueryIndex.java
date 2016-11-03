package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryIndex {
	HashMap<String, ArrayList<PathNode>> candidate;
	HashMap<String, ArrayList<PathNode>> wait;
	
	public QueryIndex() {
		candidate = new HashMap<String, ArrayList<PathNode>>();
		wait = new HashMap<String, ArrayList<PathNode>>();
	}
	
	public void initializeIndex(String name, PathNode pn, int pos) {
		
	}
	
}
