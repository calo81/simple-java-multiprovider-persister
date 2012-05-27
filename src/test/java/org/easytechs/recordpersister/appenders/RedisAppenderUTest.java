package org.easytechs.recordpersister.appenders;

import java.util.Set;

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
	private RedisAppender testObj;
	private static final String HOST = "localhost";

	@Mock
	private RecordGenerator<KeyValue> recordGenerator;

	private Jedis jedis;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testObj = new RedisAppender(HOST);
		testObj.setRecordGenerator(recordGenerator);
		jedis = new Jedis(HOST);
		jedis.connect();
	}

	@Test
	public void shouldPersistSimpleRequest() {
		doTimed(new IndexedRunnable() {			
			@Override
			public void run(int i) {
				NormalizedMessage message = new NormalizedMessage();
				KeyValue keyValue = new KeyValue("tick", "UK100SS");
				Mockito.when(recordGenerator.generate(message))
						.thenReturn(keyValue);
				testObj.append(message);
			}
		}, 2000);
		Set<String> values = jedis.smembers("tick");
		Assert.assertEquals(values.iterator().next(), "UK100SS");
		testObj.close();
		jedis.disconnect();
	}

}
