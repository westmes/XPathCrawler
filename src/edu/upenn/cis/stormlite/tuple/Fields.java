package edu.upenn.cis.stormlite;

import java.util.ArrayList;

/**
 * Simple field list
 * 
 * @author zives
 *
 */
public class Fields extends ArrayList<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Fields(String s) {
		super();
		add(s);
	}
	
	public Fields(String... lst) {
		super();
		for (String s: lst)
			add(s);
	}

}
