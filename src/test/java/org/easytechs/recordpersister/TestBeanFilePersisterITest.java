package org.easytechs.recordpersister;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.FileAppender;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.ToStringRecordGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestBeanFilePersisterITest extends AbstractTimedTest {

	/**
	 */
	private GenericPersister<TestBean> testObj;
	private FileAppender appender;

	@BeforeMethod
	public void setup() throws Exception {
		testObj = new GenericPersister<>();
		appender = new FileAppender("/tmp/tickfiletest.test");
		appender.setRecordGenerator(new ToStringRecordGenerator("value"));
		testObj.setNormalizedMessageTransformer(new BeanToCsvStringNormalizer<TestBean>("symbol", "value"));
		testObj.setAppender(appender);
	}

	@Test
	public void shouldPersistOneItem() {
		TestBean tick = new TestBean();
		tick.setSymbol("XX");
		tick.setValue("100.00");
		testObj.persist(tick);
		appender.close();
	}

	@Test
	public void shouldPersistManyItems() {
		for (int i = 0; i < 10; i++) {
			doOneRun();
		}
		appender.close();
	}

	private void doOneRun() {
		doTimed(new IndexedRunnable() {
			@Override
			public void run(int index) throws Exception {
				TestBean tick = new TestBean();
				tick.setSymbol("XX");
				tick.setValue("100.00");
				testObj.persist(tick);

			}
		}, new Runnable() {

			@Override
			public void run() {
				appender.flush();
			}
		}, 20000);
		
	}

}
