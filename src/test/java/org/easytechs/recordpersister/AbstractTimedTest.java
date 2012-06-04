package org.easytechs.recordpersister;


public abstract class AbstractTimedTest {

	protected void doTimed(IndexedRunnable r, int times) {
		long initTime = System.currentTimeMillis();
		for (int i = 1; i <= times; i++) {
			try {
				r.run(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken: " + (endTime - initTime));
	}
	
	protected void doTimed(IndexedRunnable r,Runnable closingRunnable, int times) {
		long initTime = System.currentTimeMillis();
		for (int i = 1; i <= times; i++) {
			try {
				r.run(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closingRunnable.run();
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken: " + (endTime - initTime));
	}
}
