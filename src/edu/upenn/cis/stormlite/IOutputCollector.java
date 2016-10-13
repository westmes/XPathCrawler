package edu.upenn.cis.stormlite;

import java.util.List;

/**
 * A stream propagation interface, used by a spout or bolt to send
 * tuples to the next stage
 * 
 * @author zives
 *
 */
public interface IOutputCollector {
	/**
	 * Propagates a tuple (list of objects) to a particular
	 * named stream
	 * 
	 * @param streamId
	 * @param tuple
	 */
	public void emit(String streamId, List<Object> tuple);
}
