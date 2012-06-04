package org.easytechs.recordpersister;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class QueuedPersisterDecorator<T extends Object> implements Persister<T> {

	private Persister<T> persister;
	private BlockingQueue<T> queue;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public QueuedPersisterDecorator(Persister<T> persister, int queueSize) {
		this.persister = persister;
		this.queue = new LinkedBlockingQueue<>(queueSize);
		queuePollAndDelegate();
	}

	@Override
	public void persist(T object) {
		queue.add(object);
	}

	private void queuePollAndDelegate() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					T object;
					try {
						object = queue.take();
						persister.persist(object);
					} catch (InterruptedException e) {
						// What can I do here?
						e.printStackTrace();
					}					
				}
			}
		});
	}

	public int remainingCapacity() {
		return queue.remainingCapacity();
	}

}
