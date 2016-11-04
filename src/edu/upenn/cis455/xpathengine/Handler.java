package edu.upenn.cis455.xpathengine;

import java.util.ArrayList;
import java.util.Arrays;
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
	Stack<String> text;
	Stack<PathNode> nodeText;
	
	public Handler() {
		super();
	}
	
	public void setHandler(QueryIndex qi, boolean[] isMatched) {
		this.qi = qi;
		this.isMatched = isMatched;
		this.completed = new HashSet<String>();
		this.text = new Stack<String>();
		this.nodeText = new Stack<PathNode>();
	}
	
	@Override
	public void startDocument() throws SAXException {
		level = 0;
		Arrays.fill(isMatched, false);
	}
	
	@Override
	public void endDocument() throws SAXException {
		// TODO: reevaluate undetermined nodes/filters
		// only check the paths not completed
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("end: " + localName);
	}
	

	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qi == null) {
			// throw: need to set handler
			return;
		}
		
		level++;
		if (qi.candidate.containsKey(localName)) {
			// if the element is in candidate 
			ArrayList<PathNode> nodeList = qi.candidate.get(localName);
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
					if (node.checkExpressionTree()) {
						// all filter passed (all @att)
						// complete this node
						node.updateComplete();
						if (node.nextPathNodeSet.size() == 0) {
							// no next element, ending
							// need to trace back to root
							if (node.checkValid()) {
								isMatched[Integer.parseInt(node.queryId.substring(1))] = true;
								completed.add(node.queryId);
							}
						} else {
							// TODO: move to the next element(s) of node
							copyToCandidateList(node);
						}
					}
					
				} else {
					continue;
				}
			}
		} else {
			return;
		}
		System.out.println("start: " + localName);
	}
	
	private void copyToCandidateList(PathNode node) {
		// TODO Auto-generated method stub
		
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
		
		System.out.println(str);
	}
	
}
