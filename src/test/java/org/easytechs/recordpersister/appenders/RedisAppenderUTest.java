package org.easytechs.recordpersister.appenders;

import java.util.List;

import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.RedisAppender;
import org.easytechs.recordpersister.appenders.redis.KeyValue;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;


public class RedisAppenderUTest extends AbstractTimedTest{
	private static final String KEY = "tickcc";
	/**
	 */
	private RedisAppender testObj;
	private static final String HOST = "127.0.0.1";

	/**
	 */
	@Mock
	private RecordGenerator<KeyValue> recordGenerator;

	/**
	 */
	private Jedis jedis;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testObj = new RedisAppender(HOST);
		testObj.setRecordGenerator(recordGenerator);
		jedis = new Jedis(HOST);
		jedis.connect();
		jedis.del(KEY);
	}

	@Test(invocationCount=10)
	public void shouldPersistSimpleRequest() {
		Mockito.when(recordGenerator.generate(Mockito.any(NormalizedMessage.class))).thenReturn(new KeyValue(KEY, "UK100SS"));
		doTimed(new IndexedRunnable() {			
			@Override
			public void run(int i) {
				NormalizedMessage message = new NormalizedMessage();
				//new KeyValue(KEY, "UK100SS");		
				testObj.append(message);
			}
		}, 20000);
		List<String> values = jedis.lrange(KEY,0,1);
		Assert.assertEquals(values.iterator().next(), "UK100SS");
		testObj.close();
		jedis.disconnect();
	}

}
