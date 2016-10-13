package edu.upenn.cis.stormlite;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple tuple class
 * 
 * @author zives
 *
 */
public class Tuple {
	private Fields fields;
	private List<Object> values;

	/**
	 * Initialize tuple with list of fields and values
	 * 
	 * @param fields2
	 * @param tuple
	 */
	public Tuple(Fields fields2, List<Object> tuple) {
		fields = fields2;
		
		values = tuple;
		
		if (fields.size() != values.size())
			throw new IllegalArgumentException("Cardinality mismatch between fields and values");
	}

	/**
	 * Initialize a unary tuple with a field name
	 * 
	 * @param fieldName
	 * @param value
	 */
	public Tuple(String fieldName, Object value) {
		fields = new Fields(fieldName);
		
		values = new ArrayList<>();
		values.add(value);
	}

	/**
	 * The Fields we are representing
	 * @return
	 */
	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	/**
	 * Values, in list order
	 * 
	 * @return
	 */
	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	public Object getObjectByField(String string) {
		int i = fields.indexOf(string);
		
		if (i < 0)
			return null;
		else
			return values.get(i);
	}

	public String getStringByField(String string) {
		return (String)getObjectByField(string);
	}
	
	public Integer getIntegerByField(String string) {
		return (Integer)getObjectByField(string);
	}
	
}
