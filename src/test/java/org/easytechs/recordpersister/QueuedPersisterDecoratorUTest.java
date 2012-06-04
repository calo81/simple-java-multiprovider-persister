package org.easytechs.recordpersister;

import org.easytechs.recordpersister.Persister;
import org.easytechs.recordpersister.QueuedPersisterDecorator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class QueuedPersisterDecoratorUTest {
	private QueuedPersisterDecorator<TestBean> testObj;
	
	private Persister<TestBean> delegatePersister;
	
	int queueSize = 2;
	
	@BeforeMethod
	public void setup(){
		delegatePersister= new LongDelayPersister();
		testObj = new QueuedPersisterDecorator<>(delegatePersister,queueSize);
	}
	
	@Test
	public void shouldQueueRequests() throws Exception{
		TestBean tick1 = new TestBean();
		TestBean tick2 = new TestBean();
		testObj.persist(tick1);
		testObj.persist(tick2);
		Thread.sleep(1000);
		Assert.assertEquals(testObj.remainingCapacity(), 1);
	}
	
	private static class LongDelayPersister implements Persister<TestBean>{

		@Override
		public void persist(TestBean object) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
