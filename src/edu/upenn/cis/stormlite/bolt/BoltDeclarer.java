/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.upenn.cis.stormlite.bolt;

import java.io.Serializable;

import edu.upenn.cis.stormlite.routers.IStreamRouter;
import edu.upenn.cis.stormlite.routers.RoundRobin;
import edu.upenn.cis.stormlite.tuple.Fields;

public class BoltDeclarer implements Serializable {
	
	public static final String SHUFFLE = "shuffle";
	public static final String FIELDS = "fields";
	
	String stream;
	String type;
	Fields shardFields;
	
	IStreamRouter router;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BoltDeclarer() {
	}

	public BoltDeclarer(String typ) {
		setType(typ);
	}
	
	public String getType() { 
		return type; 
	}
	
	public void setType(String typ) {
		this.type = typ;
	} 
	
	public String getStream() {
		return stream;
	}
	
	public Fields getShardingFields() {
		return shardFields;
	}
	
	/**
	 * Round robin
	 * 
	 * @param key The stream name
	 */
	public void shuffleGrouping(String key) {
		this.stream = key;
		setType(SHUFFLE);
	}

	/**
	 * Partition (shard) by fields
	 * 
	 * @param key The stream name
	 * @param fields The fields to group and shard by
	 */
	public void fieldsGrouping(String key, Fields fields) {
		this.stream = key;
		shardFields = fields;
		setType(FIELDS);
	}
	
	public IStreamRouter getRouter() {
//		if (getType().equals(SHUFFLE)) {
		if (router == null)
			router = new RoundRobin();
//		}
		
		return router;
	}
}
