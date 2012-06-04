package org.easytechs.recordpersister;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.CassandraAppender;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.CassandraRowToStringBasedGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestBeanCassandraPersisterITest extends AbstractTimedTest{

	/**
	 */
	private GenericPersister<TestBean> testObj;
	
	private static final String HOST = "localhost";
	private static final int PORT = 9160;
	private static final String KEYSPACE = "DEMO";
	private static final String COLUMN_PARENT = "RawTicks";
	

	@BeforeMethod
	public void setup() throws Exception {
		testObj = new GenericPersister<>();
		CassandraAppender appender = new CassandraAppender(HOST, PORT, KEYSPACE, COLUMN_PARENT);
		appender.setRecordGenerator(new CassandraRowToStringBasedGenerator());
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