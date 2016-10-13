package edu.upenn.cis.stormlite;

public class TopologyBuilder {
	Topology topo;
	
	public void setSpout(String streamID, ISpout spout, int parallelism) {
		
	}
	
	public BoltDeclarer setBolt(String streamID, IBolt bolt, int parallelism) {
		return null;
	}

	public Object createTopology() {
		// TODO Auto-generated method stub
		return null;
	}
}
