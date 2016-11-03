package edu.upenn.cis455.xpathengine;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;

public class XPathEngineImpl implements XPathEngine {
	QueryIndex qi;

	public XPathEngineImpl() {
		// Do NOT add arguments to the constructor!!
		this.qi = new QueryIndex();
	}

	public void setXPaths(String[] s) {
		for (int i=0; i<s.length; i++) {
			XPath path = new XPath("Q"+i);
			XPathParse rdp = new XPathParse(qi, s[i], path);
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

		return null;
	}

}
