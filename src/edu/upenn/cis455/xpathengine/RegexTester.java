package edu.upenn.cis455.xpathengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTester {

	public static void main(String[] args) {
		String text = "/abc/def[@name=value][text()=\"abc\"]/foo]";
		String text2 = "/abc/def[@name=value][text()=\"abc\"]/foo]/bar";
		String a = "contains[text(),\"chicken\"]/def";
		Pattern pattern = Pattern.compile("(contains\\(\\s*text\\(\\)\\s*,\\s*\".*?\"\\))\\s*].*");
		Matcher match = pattern.matcher(a);
		System.out.println(match.matches());

	}

}
