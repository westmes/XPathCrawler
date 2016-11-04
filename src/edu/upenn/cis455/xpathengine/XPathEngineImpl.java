package edu.upenn.cis455.xpathengine;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;

public class XPathEngineImpl implements XPathEngine {
	QueryIndex qi;
	XPathParse rdp;
	HashMap<String, XPath> paths;
	boolean[] isValid;

	public XPathEngineImpl() {
		// Do NOT add arguments to the constructor!!
		this.paths = new HashMap<String, XPath>();
	}

	public void setXPaths(String[] s) {
		this.paths.clear();
		this.qi = new QueryIndex();
		this.rdp = new XPathParse(qi);
		isValid = new boolean[s.length];
		Arrays.fill(isValid, false);
		
		for (int i=0; i<s.length; i++) {
			XPath path = new XPath("Q"+i);
			paths.put("Q"+i, path);
			rdp.setPath(s[i], path);
			try {
				rdp.recursiveParse(null, null);
			} catch (Exception e) {
				System.out.println("Malformatted XPath");
				e.printStackTrace();
			}
//			path.printXPath();
		}
	}

	public boolean isValid(int i) {
		/* TODO: Check which of the XPath expressions are valid */
		return false;
	}

	public boolean[] evaluate(Document d) { 
		/* TODO: Check whether the document matches the XPath expressions */
		return null; 
	}

	@Override
	public boolean isSAX() {
		// This returns true by implementing SAX/Event-driven
		return true;
	}

	@Override
	public boolean[] evaluateSAX(InputStream document, DefaultHandler handler) {
		// TODO: query index needs to be cloned before passing into handler, so that we don't need generate a new QueryIndex for every XML
		return null;
	}

}
