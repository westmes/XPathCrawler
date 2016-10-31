package edu.upenn.cis455.xpathengine;

public class XPathParse {
	String xpath;
	String xpathParse;
	QueryIndex qi;
	String queryId;
	private int pos;

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
			
			// TODO: add nodeName to qi
			
			
			if (i<xpathParse.length()) {
				xpathParse = xpathParse.substring(i);
			}
			
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
				xpathParse = xpathParse.substring(1);
			} else {
				// return false;
			}
			
		}
		
		
	}

	private void textCheck(PathNode pn) {
		// TODO Auto-generated method stub
		ExpressionTree et = new ExpressionTree();
	}

	private void containsText(PathNode pn) {
		// TODO Auto-generated method stub
		
	}

	private void attribute(PathNode pn) {
		// TODO Auto-generated method stub
		
	}
}
