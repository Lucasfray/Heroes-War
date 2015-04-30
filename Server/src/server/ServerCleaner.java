package server;

import java.util.concurrent.TimeUnit;

import server.cores.CoresManager;

public class ServerCleaner {

	private static ServerCleaner getSingleton;
	
	public static ServerCleaner getSingleton() {
		if (getSingleton == null)
			getSingleton = new ServerCleaner();
		
		return getSingleton;
	}
	
	public void execute() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				
				long startTime = System.currentTimeMillis();
				
				CoresManager.fastExecutor.purge();
				
				System.gc();
				
				long endTime = System.currentTimeMillis();
				
		        long elapsed = endTime-startTime;
		        
		        System.out.println("Cleaned Server in " + elapsed + "ms.");
				
			}
		}, 0, 5, TimeUnit.MINUTES);
	}
	
}
