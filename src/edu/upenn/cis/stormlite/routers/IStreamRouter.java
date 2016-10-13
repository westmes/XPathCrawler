package edu.upenn.cis.stormlite.routers;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis.stormlite.OutputFieldsDeclarer;
import edu.upenn.cis.stormlite.bolt.IRichBolt;
import edu.upenn.cis.stormlite.tuple.Fields;
import edu.upenn.cis.stormlite.tuple.Tuple;

/**
 * A StreamRouter is an internal class used to determine where
 * an item placed on a stream should go.
 * 
 * @author zives
 *
 */
public abstract class StreamRouter implements OutputFieldsDeclarer {
	List<IRichBolt> bolts;
	Fields schema;
	
	public StreamRouter() {
		bolts = new ArrayList<>();
	}
	
	public StreamRouter(IRichBolt bolt) {
		this();
		bolts.add(bolt);
	}
	
	public StreamRouter(List<IRichBolt> bolts) {
		this.bolts = bolts;
	}
	
	/**
	 * Add another bolt instance as a consumer of this stream
	 * 
	 * @param bolt
	 */
	public void addBolt(IRichBolt bolt) {
		bolts.add(bolt);
	}

	
	/**
	 * The selector for the destination bolt
	 * 
	 * @param tuple
	 * @return
	 */
	protected abstract IRichBolt getBoltFor(List<Object> tuple);
	
	/**
	 * The destination bolts, as a list
	 * so we can assign each a unique position
	 * 
	 * @return
	 */
	public List<IRichBolt> getBolts() {
		return bolts;
	}
	
	/**
	 * Process a list of objects, ie a tuple with
	 * no schema specifier
	 * 
	 * @param tuple
	 */
	public void execute(List<Object> tuple) {
		IRichBolt bolt = getBoltFor(tuple);
		
		if (bolt == null)
			throw new RuntimeException("Unable to find a bolt for the tuple");
		
		bolt.execute(new Tuple(schema, tuple));
	}
	
	/**
	 * Process a tuple with fields
	 * 
	 * @param tuple
	 */
	public void execute(Tuple tuple) {
		execute(tuple.getValues());
	}

	/**
	 * Sets the schema of the object
	 */
	@Override
	public void declare(Fields fields) {
		schema = fields;
	}

}
