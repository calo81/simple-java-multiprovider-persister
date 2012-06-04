package org.easytechs.recordpersister;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.RedisAppender;
import org.easytechs.recordpersister.normalizers.ToStringInValueKeyNormalizer;
import org.easytechs.recordpersister.recordgenerators.RedisKeyValueRecordGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestBeanRedisPersisterITest extends AbstractTimedTest{
	/**
	 */
	private GenericPersister<TestBean> testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new GenericPersister<>();
		RedisAppender appender = new RedisAppender("localhost");
		appender.setRecordGenerator(new RedisKeyValueRecordGenerator("ticksre"));
		testObj.setNormalizedMessageTransformer(new ToStringInValueKeyNormalizer<TestBean>());
		testObj.setAppender(appender);
	}
	
	@Test
	public void shouldPersistOneItem(){
		TestBean tick = new TestBean();
		tick.setSymbol("XX");
		tick.setValue("100.00");
		testObj.persist(tick);
	}
	
	@Test(invocationCount=10)
	public void shouldPersistManyItem(){
		doTimed(new IndexedRunnable() {
			
			@Override
			public void run(int index) throws Exception {
				TestBean tick = new TestBean();
				tick.setSymbol("XX");
				tick.setValue("100.00");
				testObj.persist(tick);
			}
		}, 20000);	
	}
}
