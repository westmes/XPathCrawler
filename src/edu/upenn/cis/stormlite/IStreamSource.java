package edu.upenn.cis.stormlite;

public interface IStreamSource {
	/**
	 * Returns the name of the stream
	 */
	String getStreamId();
	
	/**
	 * Set by the TopologyBuilder
	 * 
	 * @param stream
	 */
	public void setStreamId(String stream);
}
