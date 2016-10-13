package edu.upenn.cis.stormlite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simplified version of Storm output queues
 * 
 * @author zives
 *
 */
public class OutputCollector {
	static Set<IBolt> nullSet = new HashSet<>();
	
	Topology topology;
	
	Map<String,Set<IBolt>> bolts = new HashMap<>();
	
	public OutputCollector() {
		
	}
	
	/**
	 * @param streamId
	 * @param tuple
	 */
	public void emit(String streamId, List<Object> tuple) {
		for (IBolt bolt: getBolts(streamId)) {
			bolt.execute(
					new Tuple(topology.streamSchemas.get(streamId), 
							tuple));
		}
	}
	
	public Set<IBolt> getBolts(String stream) {
		Set<IBolt> ret = bolts.get(stream);
		
		if (ret == null)
			return nullSet;
		else
			return ret;
	}
	
	public void registerBolt(String stream, IBolt bolt) {
		if (!bolts.containsKey(stream))
			bolts.put(stream, new HashSet<IBolt>());
		
		bolts.get(stream).add(bolt);
	}
}
