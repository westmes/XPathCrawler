package edu.upenn.cis455.xpathengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;

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
		
		String path2 = "/rss[@version=\"2.0\"][docs[contains(text(),\"http\")]]/channel/image/title[text()=\"BBC News\"]";
		String path1 = "/rss[@version=\"2.0\"][docs[contains(text(),\"http\")]]/channel/imag/title";
//		String path3 = "/rss[@version=\"2.0\"]/docs";
		String path4 = "/rss/channel";
		String[] xpaths = new String[1];
		xpaths[0] = path1;
//		xpaths[1] = path2;
//		xpaths[2] = path3;
//		xpaths[3] = path4;
		
		XPathEngine xpe = XPathEngineFactory.getXPathEngine();
		xpe.setXPaths(xpaths);
		DefaultHandler handler = XPathEngineFactory.getSAXHandler();
		FileInputStream fis;
		try {
			fis = new FileInputStream("frontpage.xml");
			boolean[] isMatched = xpe.evaluateSAX(fis, handler);
			for (int i=0; i<isMatched.length; i++) {
				System.out.print(isMatched[i] + " ");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
