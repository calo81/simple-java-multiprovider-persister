package org.easytechs.recordpersister;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.MongoAppender;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.MongoDBSimpleNormalizedGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestBeanMongoPersisterITest extends AbstractTimedTest{
	/**
	 */
	private GenericPersister<TestBean> testObj;

	@BeforeMethod
	public void setup() throws Exception {
		testObj = new GenericPersister<>();
		MongoAppender appender = new MongoAppender("127.0.0.1", "27017", "xxdb", "ticks");
		appender.setRecordGenerator(new MongoDBSimpleNormalizedGenerator("tick"));
		testObj.setNormalizedMessageTransformer(new BeanToCsvStringNormalizer<TestBean>("symbol", "value"));
		testObj.setAppender(appender);
	}

	@Test
	public void shouldPersistOneItem() {
		TestBean tick = new TestBean();
		tick.setSymbol("XX");
		tick.setValue("100.00");
		testObj.persist(tick);
	}

	@Test(invocationCount=10)
	public void shouldPersistManyItems() {
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
