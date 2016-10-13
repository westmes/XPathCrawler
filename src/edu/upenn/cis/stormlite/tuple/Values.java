package edu.upenn.cis.stormlite;

import java.util.ArrayList;

public class Values<E> extends ArrayList<E> {
	public Values() {
		
	}
	
	public Values(E... values) {
		super(values.length);
		
		for (E e: values)
			add(e);
	}
}
