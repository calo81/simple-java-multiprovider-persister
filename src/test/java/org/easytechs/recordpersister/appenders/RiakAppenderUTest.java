package org.easytechs.recordpersister.appenders;

import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.redis.KeyValue;
import org.easytechs.recordpersister.recordgenerators.HashCodeKeyValueRecordGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakFactory;

public class RiakAppenderUTest extends AbstractTimedTest{
	private static final String BUCKET = "col";
	/**
	 */
	private RiakAppender testObj;
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8087;

	private IRiakClient riakClient;

	/**
	 */
	
	private RecordGenerator<KeyValue> recordGenerator;


	@BeforeMethod
	public void setup() throws Exception{
		recordGenerator = new HashCodeKeyValueRecordGenerator();
		testObj = new RiakAppender(HOST,PORT,BUCKET);
		testObj.setRecordGenerator(recordGenerator);
		riakClient = RiakFactory.pbcClient(HOST,PORT);
		
	}

	@Test(invocationCount=10)
	public void shouldPersistSimpleRequest() throws Exception{
		doTimed(new IndexedRunnable() {			
			@Override
			public void run(int i) {
				NormalizedMessage message = new NormalizedMessage();
				message.addValue("value", "store this");
				testObj.append(message);
			}
		}, 200);
		 riakClient.fetchBucket(BUCKET).execute();
	}
}
