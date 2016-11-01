package edu.upenn.cis455.xpathengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPathParse {
	String xpath;
	String xpathParse;
	QueryIndex qi;
	String queryId;
	private int pos;
	// TODO: link previous node to current
	PathNode prev;

	public static void main(String[] args) {
		String xpath = "/foo[bar][@att=\"xyz\"]/bar/abc[text/path[contains[text(),\"chicken\"]]/def";
		XPathParse rdp = new XPathParse(xpath, "Q1");
		rdp.recursiveParse();
	}
	
	public XPathParse(String xpath, String qid) {
		this.qi = new QueryIndex();
		this.xpath = xpath;
		this.xpathParse = xpath.trim();
		this.queryId = qid;
		this.pos = 1;
	}
	
	public void recursiveParse(){
		while (xpathParse.startsWith("/")) {
			axisStep();
		}
	}
	
	private boolean axisStep() {
		if (xpathParse.startsWith("/")) {
			StringBuilder nodeName = new StringBuilder();
			int i=1;
			while (i<xpathParse.length() && xpathParse.charAt(i)!='/' && xpathParse.charAt(i)!='[') {
				
				nodeName.append(xpathParse.charAt(i));
				i++;
			}
			
			PathNode pn = constructNewPathNode();
			prev.addNextPathNode(pn);
			
			// TODO: add nodeName to qi
			
			if (i<xpathParse.length()) {
				xpathParse = xpathParse.substring(i);
			}
			
			// keep parsing test for the PathNode
			while (xpathParse.startsWith("[")) {
				test(pn);
			}
		}
		return true;
	}

	private PathNode constructNewPathNode() {
		PathNode pn = new PathNode();
		pn.queryId = this.queryId;
		pn.position = this.pos;
		if (pos == 1) {
			pn.relativePos = 0;
			pn.level = 1;
		} else {
			// double check this
			pn.relativePos = 1;
			pn.level = 0;
		}
		pos++;
		return pn;
	}

	private void test(PathNode pn) {
		if (xpathParse.startsWith("[")) {
			
			xpathParse = xpathParse.substring(1).trim();
			
			if (xpathParse.startsWith("@")) {
				attribute(pn);
			} else if (xpathParse.startsWith("contains")){
				containsText(pn);
			} else if (xpathParse.startsWith("text()")) {
				textCheck(pn);
			} else  {
				// another path
				xpathParse = "/" + xpathParse;
				axisStep();
			}
			
			// remove ']'
			if (xpathParse.trim().startsWith("]")) {
				xpathParse = xpathParse.substring(1).trim();
			} else {
				// throw
				// return false;
			}
			
		}
		 
		
	}

	private void textCheck(PathNode pn) {
		Pattern pattern = Pattern.compile("(text\\(\\)\\s*=\\s*\".*?\")\\s*].*");
		Matcher match = pattern.matcher(xpathParse);
		if (match.matches()) {
			String text = match.group(1);
			ExpressionTree et = new ExpressionTree();
			et.type = "text";
			et.name = "text()";
			int ind = text.indexOf("=");
			// Note the value has quotation marks around it
			et.value = text.substring(ind+1).trim();
			
			// remove up to ']'
			xpathParse = xpathParse.replaceFirst(Pattern.quote(text),"").trim();
			
			// add expression tree to current PathNode
			pn.addfilter(et);
		} else {
			// start with text(), but not well formatted
			// TODO: throw
		}
	}
	
	/**
	 * This method is used to build a filter expression tree for 'contains(text(), "...")'
	 * @param pn
	 */
	private void containsText(PathNode pn) {
		Pattern pattern = Pattern.compile("(contains\\(\\s*text\\(\\)\\s*,\\s*\".*?\"\\))\\s*].*");
		Matcher match = pattern.matcher(xpathParse);
		if (match.matches()) {
			String text = match.group(1);
			ExpressionTree et = new ExpressionTree();
			et.type = "contains";
			et.name = "text()";
			int ind = text.indexOf(',');
			int ind2 = text.lastIndexOf(')');
			
			// Note the value has quotation marks around it
			et.value = text.substring(ind+1, ind2).trim();
			
			// remove up to ']'
			xpathParse = xpathParse.replaceFirst(Pattern.quote(text),"").trim();
			
			// add expression tree to current PathNode
			pn.addfilter(et);
		} else {
			// start with contains, but not well formatted
			// TODO: throw
		}
	}
	
	/**
	 * This method is used to build a filter expression tree for '@attname="..."'
	 * @param pn
	 */
	private void attribute(PathNode pn) {
		Pattern pattern = Pattern.compile("(@[a-zA-Z_:][a-zA-Z0-9-_\\.:]*\\s*=\\s*\".*?\")\\s*].*");
		Matcher match = pattern.matcher(xpathParse);
		if (match.matches()) {
			String text = match.group(1);
			ExpressionTree et = new ExpressionTree();
			et.type = "attribute";
			int ind = text.indexOf('=');
			et.name = text.substring(0,ind).trim();
			// Note the value has quotation marks around it
			et.value = text.substring(ind+1).trim();
			
			// remove up to ']'
			xpathParse = xpathParse.replaceFirst(Pattern.quote(text),"").trim();
			
			// add expression tree to current PathNode
			pn.addfilter(et);
		} else {
			// start with contains, but not well formatted
			// TODO: throw
		}
		
	}
}
