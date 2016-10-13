package edu.upenn.cis.stormlite;

import java.util.Map;

public interface IBolt extends IStreamSource {
	
	/**
	 * Called when a bolt is about to be shut down
	 */
	public void cleanup();
	
	/**
	 * Processes a tuple
	 * @param input
	 */
	public void execute(Tuple input);
	
	/**
	 * Called when this task is initialized
	 * 
	 * @param stormConf
	 * @param context
	 * @param collector
	 */
	public void prepare(Map<String,String> stormConf,
            TopologyContext context,
            OutputCollector collector);
	
}
