package org.easytechs.recordpersister;

import java.util.Date;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.MongoAppender;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.MongoDBSimpleNormalizedGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestBean2PersisterITest {

	/**
	 */
	private GenericPersister<TestBean2> testObj;

	@BeforeMethod
	public void setup() throws Exception {
		testObj = new GenericPersister<>();
		MongoAppender appender = new MongoAppender("127.0.0.1", "27017", "test-db", "ticks");
		appender.setRecordGenerator(new MongoDBSimpleNormalizedGenerator("tick"));
		BeanToCsvStringNormalizer<TestBean2> transformer = new BeanToCsvStringNormalizer<TestBean2>("symbol", "price", "tradeTime");
		testObj.setNormalizedMessageTransformer(transformer);
		testObj.setAppender(appender);
	}

	@Test
	public void shouldPersistOneItem() {
		TestBean2 tick = new TestBean2("GBPUSD", new Date().getTime(), "123.88");
		testObj.persist(tick);
	}

	@Test
	public void shouldPersistManyItems() {
		for (int i = 0; i < 100; i++) {
			TestBean2 tick = new TestBean2("GBPUSD", new Date().getTime(), "123.88");
			testObj.persist(tick);
		}
	}
}
