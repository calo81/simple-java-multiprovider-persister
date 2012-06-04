package org.easytechs.recordpersister.appenders;

import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.MongoAppender;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoAppenderUTest extends AbstractTimedTest {
	/**
	 */
	private MongoAppender testObj;
	private static final String HOST = "127.0.0.1";
	private static final String PORT = "27017";
	private static final String DB = "test-db";
	private static final String COLLECTION = "ticks";

	/**
	 */
	@Mock
	private RecordGenerator<DBObject> recordGenerator;

	@BeforeMethod
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		testObj = new MongoAppender(HOST, PORT, DB, COLLECTION);
		testObj.setRecordGenerator(recordGenerator);
	}

	@Test(invocationCount=10)
	public void shouldPersistSimpleObjects() {
		Mockito.when(recordGenerator.generate(Mockito.any(NormalizedMessage.class))).thenReturn(createBasicDbObjectWithIndexSomewhere(1));
		doTimed(new IndexedRunnable() {
			@Override
			public void run(int index) throws Exception {
				NormalizedMessage message = new NormalizedMessage();			
				testObj.append(message);
			}

		}, 20000);
	}

	private BasicDBObject createBasicDbObjectWithIndexSomewhere(int index) {
		BasicDBObject doc = new BasicDBObject();
		doc.put("tick", "Este es otro tick mas para grabar con numero " + index);
		return doc;
	}
}
