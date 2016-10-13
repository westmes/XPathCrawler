package edu.upenn.cis.stormlite;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class Topology {
	/**
	 * Spouts are the inputs, and each has a stream ID
	 */
	Map<String,ISpout> spouts = new HashMap<>();

	/**
	 * Bolts are the operators, and each has a stream ID
	 * disjoint from the spouts
	 */
	Map<String, IBolt> bolts = new HashMap<>();
	
	/**
	 * Bolts have multiple inputs connected to spouts
	 */
	Map<String, Pair<String,Integer>> boltConnectors = new HashMap<>();
	
	/**
	 * Each Stream has a set of fields, i.e., a schema
	 */
	Map<String, Fields> streamSchemas = new HashMap<>();
}
