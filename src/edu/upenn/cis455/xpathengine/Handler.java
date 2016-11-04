package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler {
	QueryIndex qi;
	int level;
	boolean[] isMatched;
	HashSet<String> completed;
	Stack<ArrayList<PathNode>> nodeText;
	ArrayList<PathNode> nodeList;
	
	public Handler() {
		super();
	}
	
	private void initQueryIndex(HashMap<String, XPath> xpath) {
		for (XPath xp : xpath.values()) {
			PathNode node = xp.head;
			node.resetCompletion();
			qi.addToCandidate(node.nodeName, node);
			recursiveAdd(node);
		}
	}
	
	private void recursiveAdd(PathNode node) {
		if (node.nextPathNodeSet.size() != 0) {
			for (PathNode pn : node.nextPathNodeSet) {
				pn.resetCompletion();
				qi.addToWait(pn.nodeName, pn);
				recursiveAdd(pn);
			}
		}
		
	}
	
	public void setHandler(HashMap<String, XPath> xpath, boolean[] isMatched) {
		this.qi = new QueryIndex();
		initQueryIndex(xpath);
		this.isMatched = isMatched;
		this.completed = new HashSet<String>();
		this.nodeText = new Stack<ArrayList<PathNode>>();
	}
	
	@Override
	public void startDocument() throws SAXException {
		level = 0;
		Arrays.fill(isMatched, false);
	}
	
	@Override
	public void endDocument() throws SAXException {
		// only check the paths not completed
		completed.clear();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qi.candidate.containsKey(localName)) {
			
			if (nodeList != null) {
				removeFromCandidate(localName, nodeList);
			}
		}
		nodeText.pop();
		level--;
		nodeList = null;
//		System.out.println("end: " + localName);
	}
	
	private void removeFromCandidate(String localName, ArrayList<PathNode> nodeList) {
		ArrayList<PathNode> list = qi.candidate.get(localName);
		list.removeAll(nodeList);
	}

	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		System.out.println("start: " + qName);
		level++;
		if (qi.candidate.containsKey(localName)) {
			// if the element is in candidate
			nodeList = null;
			nodeList = qi.candidate.get(localName);
			nodeText.push(new ArrayList<PathNode>(nodeList));
			for (PathNode node : nodeList) {
				if (!completed.contains(node.queryId)) {
					boolean isLevel = levelCheck(node);
					// didn't pass level check
					if (!isLevel) {
						completed.add(node.queryId);
						// isMatched keeps to be false
						continue;
					}

					// 2. filter check (just the attributes aka. @att=value)
					filterCheck(node, attributes);
					updateQueryIndex(node);
					
				} else {
					continue;
				}
			}
		} else {
			nodeText.push(new ArrayList<PathNode>());
			return;
		}
		
	}
	
	private void copyToCandidateList(PathNode node) {
		for (PathNode pn : node.nextPathNodeSet) {
			qi.addToCandidate(pn.nodeName, pn);
		}
		
	}

	private void updateQueryIndex(PathNode node) {
		if (node.checkExpressionTree()) {
			// all filter passed (all @att)
			// complete this node
			
			if (node.nextPathNodeSet.size() == 0) {
				node.updateComplete();
				// no next element, ending
				// need to trace back to root
				if (node.checkValid()) {
					isMatched[Integer.parseInt(node.queryId.substring(1))] = true;
					completed.add(node.queryId);
				}
			} else {
				copyToCandidateList(node);
			}
		}
	}
	
	private boolean levelCheck(PathNode node) {
		// 1. level check
		boolean levelCheck = false;
		if (node.level==1 && node.level == level) {
			// first element at level 1
			levelCheck = true;
		} else if (node.level==0 && node.relativePos+node.parent.level == level) {
			levelCheck = true;
			// update node level
			node.level = node.relativePos+node.parent.level;
		}
		return levelCheck;
	}
	
	private void filterCheck(PathNode node, Attributes attributes) {
		for (ExpressionTree et : node.filters) {
			if (et.type.equals("attribute")) {
				String value = attributes.getValue(et.name.substring(1)); // get rid of the '@'
				if (value != null) {
					System.out.println(value);
					String val =  et.value;
					val = val.substring(1, val.length()-1); // get rid of double quotes
					if (value.equals(val)) {
						et.isValid = true;
					} else {
						completed.add(node.queryId);
					}
					
				} 
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String(ch, start, length);
		ArrayList<PathNode> nodes = nodeText.peek();
		for (PathNode node : nodes) {
			if (!completed.contains(node.queryId)) {
				for (ExpressionTree et : node.filters) {
					if (et.type.equals("text") || et.type.equals("contains")) {
						String val = et.value;
						val = val.substring(1, val.length()-1); // get rid of double quote around it
						if (et.type.equals("text")) {
							if (val.equals(str)) {
								et.isValid = true;
							} else {
								completed.add(node.queryId);
							}
						} else {
							if (str.contains(val)) {
								et.isValid = true;
							} else {
								completed.add(node.queryId);
							}
						}
					}
				}
				
				updateQueryIndex(node);
			}
		}


//		System.out.println(str);
	}
	
}
