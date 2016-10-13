package edu.upenn.cis.stormlite.spout;

import java.util.List;

import edu.upenn.cis.stormlite.IOutputCollector;
import edu.upenn.cis.stormlite.routers.StreamRouter;

/**
 * Simplified version of Storm output queues
 * 
 * @author zives
 *
 */
public class SpoutOutputCollector implements IOutputCollector  {
	StreamRouter router;
	
	public SpoutOutputCollector(StreamRouter router) {
		this.router = router;
	}
	
	/**
	 * Emits a tuple to the stream destination
	 * @param tuple
	 */
	public void emit(List<Object> tuple) {
		router.execute(tuple);
	}

}
