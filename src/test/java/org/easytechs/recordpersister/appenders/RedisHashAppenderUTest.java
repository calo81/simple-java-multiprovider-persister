package org.easytechs.recordpersister.appenders;

import java.util.List;
import java.util.Map;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.appenders.RedisHashAppender;
import org.easytechs.recordpersister.recordgenerators.MapFromNormalizerGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;


public class RedisHashAppenderUTest {
	private static final String COLLECTION = "tickitacka";
	/**
	 */
	private RedisHashAppender testObj;
	/**
	 */
	private Jedis jedis;
	
	@BeforeMethod
	public void setup(){
		testObj = new RedisHashAppender("127.0.0.1", COLLECTION);
		testObj.setRecordGenerator(new MapFromNormalizerGenerator());
		jedis = new Jedis("127.0.0.1");
		jedis.connect();
		jedis.del(COLLECTION);
	}
	
	@Test
	public void shouldStoreTheHashes(){
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("p1", "23");
		message.addValue("p2", "xcx");
		testObj.append(message);
		List<String> keys = jedis.lrange(COLLECTION, 0,2);
		Assert.assertEquals(keys.size(), 1);
		for(String key:keys){
			Map<String, String> values = jedis.hgetAll(key);
			Assert.assertEquals(values.get("p1"), "23");
			Assert.assertEquals(values.get("p2"), "xcx");
		}
	}
}
