package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryIndex {
	HashMap<String, ArrayList<ArrayList<PathNode>>> index;
	
	public QueryIndex() {
		index = new HashMap<String, ArrayList<ArrayList<PathNode>>>();
	}
	
	public void initializeIndex(String name, PathNode pn, int pos) {
		
	}
	
}
