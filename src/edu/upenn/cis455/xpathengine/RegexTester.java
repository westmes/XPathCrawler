package edu.upenn.cis455.xpathengine;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RegexTester {

	public static void main(String[] args) throws ParserConfigurationException, SAXException {
//		String text = "/abc/def[@name=value][text()=\"abc\"]/foo]";
//		String text2 = "/abc/def[@name=value][text()=\"abc\"]/foo]/bar";
//		String a = "contains[text(),\"chicken\"]/def";
//		Pattern pattern = Pattern.compile("(contains\\(\\s*text\\(\\)\\s*,\\s*\".*?\"\\))\\s*].*");
//		Matcher match = pattern.matcher(a);
//		System.out.println(match.matches());
		
	    SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setNamespaceAware(true);
	    SAXParser saxParser = spf.newSAXParser();
	    DefaultHandler handler = XPathEngineFactory.getSAXHandler();
	    Handler h = (Handler) handler;
	    
	    try {
	    	FileInputStream fis = new FileInputStream("test.xml");
	    	h.setHandler(new QueryIndex(), new boolean[4]);
			saxParser.parse(fis, h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
