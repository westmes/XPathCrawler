package edu.upenn.cis455.xpathengine;

public class ExpressionTree<T> {
	boolean evaluation;
	String type;
	String name;
	T value;
	
	public ExpressionTree() {
		this.evaluation = false;
	}
}
 