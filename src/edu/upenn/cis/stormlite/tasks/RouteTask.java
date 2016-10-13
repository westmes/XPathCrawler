package edu.upenn.cis.stormlite.tasks;

import java.util.List;

import edu.upenn.cis.stormlite.routers.IStreamRouter;

public class RouteTask implements Runnable {
	
	IStreamRouter router;
	List<Object> data;
	
	public RouteTask(IStreamRouter router, List<Object> data) {
		this.data = data;
		this.router = router;
	}

	@Override
	public void run() {
		router.execute(data);
		
	}

}
