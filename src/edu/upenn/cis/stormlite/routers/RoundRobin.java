package edu.upenn.cis.stormlite.routers;

import java.util.List;

import edu.upenn.cis.stormlite.bolt.IRichBolt;

/**
 * Does round-robin among hte destination bolts
 * 
 * @author zives
 *
 */
public class RoundRobin extends StreamRouter {
	
	int inx = 0;

	/**
	 * Round-robin through the bolts
	 * 
	 */
	@Override
	protected IRichBolt getBoltFor(List<Object> tuple) {
		IRichBolt bolt = getBolts().get(inx);
		
		inx = (inx + 1) % getBolts().size();

		return bolt;
	}


}
