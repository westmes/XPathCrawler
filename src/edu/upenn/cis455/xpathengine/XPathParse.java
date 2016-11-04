package edu.upenn.cis455.xpathengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPathParse {
	String xpath;
	String xpathParse;
	QueryIndex qi;
	XPath path;

	public static void main(String[] args) {
//		String xpath = "/foo[bar[sql[text() =  \"   people\"]]][@att=\"xyz\"]/bar/abc[text/path[contains(text(),\"chicken\")]]/def";
//		XPath path = new XPath("Q1");
//		QueryIndex qi = new QueryIndex();
//		XPathParse rdp = new XPathParse(qi);
//		rdp.setPath(xpath, path);
//		try {
//			rdp.recursiveParse(null, null);
//		} catch (Exception e) {
//			System.out.println("Malformatted XPath");
//			e.printStackTrace();
//		}
//		path.printXPath();
		
	}
	
	public XPathParse(QueryIndex qi) {
		this.qi = qi;
	}
	
	public void setPath(String xpath, XPath path) {
		this.path = path;
		this.xpath = xpath;
		this.xpathParse = xpath.trim();
	}
	
	public void recursiveParse(PathNode prev, PathNode curr){
		if (xpathParse.startsWith("/")) {
			axisStep(prev, curr);
		} else if (xpathParse.isEmpty()) {
			return;
		}
	}
	
	private void axisStep(PathNode prev, PathNode curr) {
		// get node name
		StringBuilder nodeName = new StringBuilder();
		int i=1;
		while (i<xpathParse.length() && xpathParse.charAt(i)!='/' && xpathParse.charAt(i)!='[' && xpathParse.charAt(i)!=']') {
			nodeName.append(xpathParse.charAt(i));
			i++;
		}

		curr = constructNewPathNode(prev, nodeName.toString());
		// TODO: maybe extract this into method
		// TODO: THIS HAS A PROBLEM: if the XPath is wrong then the nodes already in QueryIndex must be removed
		// Several solutions:
		// 1. Add all nodes when parsing is finished
		// 2. remove all the nodes already added since we have the path
		// 3. do primary check before parsing
		if (prev != null) {
			// if not first element
			// 1. update next list
			prev.addNextPathNode(curr);
			// 2. update wait list in QueryIndex
			qi.addToWait(curr.nodeName, curr);
		} else {
			// if first element 
			// 1. update XPath head
			path.head = curr;
			// 2. update candidate list in QueryIndex
			qi.addToCandidate(curr.nodeName, curr);
		}
		
		// update xpathParse (by removing /{nodeName})
		if (i<=xpathParse.length()) {
			xpathParse = xpathParse.substring(i);
		}

		// keep parsing test for the PathNode
		while (xpathParse.startsWith("[")) {
			test(prev, curr);
		}
		
		recursiveParse(curr, curr);
	}

	private PathNode constructNewPathNode(PathNode prev, String nodeName) {
		PathNode pn = null;
		if (prev == null) {
			pn = new PathNode(nodeName, this.path.qid, 1, 0, 1, prev);
		} else {
			pn = new PathNode(nodeName, this.path.qid, prev.position+1, 1, 0, prev);
		}
		return pn;
	}

	private void test(PathNode prev, PathNode curr) {
		if (xpathParse.startsWith("[")) {
			
			xpathParse = xpathParse.substring(1).trim();
			if (xpathParse.startsWith("@")) {
				attribute(curr);
			} else if (xpathParse.startsWith("contains")){
				containsText(curr);
			} else if (xpathParse.startsWith("text()")) {
				textCheck(curr);
			} else {
				// another path, add '/' so we can form axis+step
				xpathParse = "/" + xpathParse;
				// next so we move the current to prev
				recursiveParse(curr, curr);
			}
			
			// TODO: need a way to check for mal-formatted token
			
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
