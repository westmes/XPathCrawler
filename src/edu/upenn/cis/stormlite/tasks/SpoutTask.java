package edu.upenn.cis.stormlite.tasks;

import java.util.Queue;

import edu.upenn.cis.stormlite.spout.IRichSpout;

public class SpoutTask implements Runnable {
	
	IRichSpout spout;
	Queue<Runnable> queue;
	
	public SpoutTask(IRichSpout theSpout, Queue<Runnable> theQueue) {
		spout = theSpout;
		queue = theQueue;
	}

	@Override
	public void run() {
		spout.nextTuple();
		
		// Schedule ourselves again at the end of the queue
		queue.add(this);
	}

}
