package edu.upenn.cis.stormlite.routers;

import java.util.List;

import edu.upenn.cis.stormlite.bolt.IRichBolt;

/**
 * Does hash partitioning on the tuple to determine
 * a destination
 * 
 * @author zives
 *
 */
public class FieldBased extends StreamRouter {
	List<Integer> fieldsToHash;
	
	public FieldBased(List<Integer> fields) {
		fieldsToHash = fields;
	}
	
	public IRichBolt getBoltFor(List<Object> tuple) {
		
		int hash = 0;
		
		for (Integer i: fieldsToHash)
			hash ^= tuple.get(i).hashCode();

		return getBolts().get(hash % getBolts().size());
	}

}
