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
		
		String path1 = "/rss[@version=\"2.0\"]/channel[docs[contains(text(),\"http\")]]/image/title[text()=\"BBC News\"]";
		String path2 = "/rss[@version=\"2.0\"][docs[contains(text(),\"http\")]]/channel/image";
		String path3 = "/rss[@version=\"2.0\"]/channel/docs";
		String path4 = "/dwml/head/product[@concise-name=\"time-series\"]";
		String path5 = "/rss/channel/description[text()=\"Visit BBC News for up-to-the-minute news, breaking news, video, audio and feature stories. BBC News provides trusted World and UK news as well as local and regional perspectives. Also entertainment, business, science, technology and health news.\"]";
		String[] xpaths = new String[5];
		xpaths[0] = path1;
		xpaths[1] = path2;
		xpaths[2] = path3;
		xpaths[3] = path4;
		xpaths[4] = path5;
		
		XPathEngine xpe = XPathEngineFactory.getXPathEngine();
		xpe.setXPaths(xpaths);
		DefaultHandler handler = XPathEngineFactory.getSAXHandler();
		try {
			evaluate(xpe, handler, "frontpage.xml");
			evaluate(xpe, handler, "weather.xml");
//			fis = new FileInputStream("frontpage.xml");
//			boolean[] isMatched = xpe.evaluateSAX(fis, handler);
//			for (int i=0; i<isMatched.length; i++) {
//				System.out.print(isMatched[i] + " ");
//			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluate(XPathEngine xpe, DefaultHandler handler, String filename) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		boolean[] isMatched = xpe.evaluateSAX(fis, handler);
		for (int i=0; i<isMatched.length; i++) {
			System.out.print(isMatched[i] + " ");
		}
		System.out.println();
	}
}
