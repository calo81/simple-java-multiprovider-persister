package org.easytechs.recordpersister;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.RedisHashAppender;
import org.easytechs.recordpersister.normalizers.BeanToMapNormalizer;
import org.easytechs.recordpersister.recordgenerators.MapFromNormalizerGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestBeanRedisHashPersisterITest extends AbstractTimedTest{

	/**
	 */
	private GenericPersister<TestBean> testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new GenericPersister<>();
		RedisHashAppender appender = new RedisHashAppender("localhost","ticksppp");
		appender.setRecordGenerator(new MapFromNormalizerGenerator());
		testObj.setNormalizedMessageTransformer(new BeanToMapNormalizer<TestBean>("symbol","value"));
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
