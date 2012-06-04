package org.easytechs.recordpersister.replayers;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.TestBean;
import org.easytechs.recordpersister.normalizers.StringToValueNormalizedNormalizer;
import org.easytechs.recordpersister.replayers.RecordDateReplayer;
import org.easytechs.recordpersister.replayers.ReplayCallback;
import org.easytechs.recordpersister.replayers.date.BeanPropertyDateExtractor;
import org.easytechs.recordpersister.replayers.date.DateExtractor;
import org.easytechs.recordpersister.retrievers.AbstractDataRetriever;
import org.easytechs.recordpersister.retrievers.RedisRetriever;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;


public class RedisBackedReplayerITest {



	private static final String HOST = "127.0.0.1";
	private static final String KEY = "ticksx";
	
	/**
	 */
	private RecordDateReplayer<TestBean> testObj;

	/**
	 */
	private DateExtractor<TestBean> dateExtractor = new BeanPropertyDateExtractor<TestBean>("date");

	/**
	 */
	private MessageDenormalizer<TestBean> normalizedInverseTransformer = new NormalizedToTradeTickTransformer();

	/**
	 */
	private AbstractDataRetriever<String> retriever;

	@BeforeMethod
	public void setup() throws Exception{
		saveACoupleOfRedisRecords();
		testObj = new RecordDateReplayer<>();
		testObj.setDateExtractor(dateExtractor);
		testObj.setNormalizedTransformer(normalizedInverseTransformer);
		retriever =  new RedisRetriever(HOST, KEY);
		retriever.setTransformer(new StringToValueNormalizedNormalizer());
		testObj.setDataRetriever(retriever);
	}

	@Test
	public void shouldReplayTwo()throws Exception {
		final AtomicInteger counter = new AtomicInteger(0);
		testObj.replay(new ReplayCallback<TestBean>() {
			@Override
			public void replay(TestBean value) {
				counter.addAndGet(1);
			}
		});
		Assert.assertEquals(counter.get(), 0);
		Thread.sleep(10000);
		Assert.assertEquals(counter.get(), 2);
	}

	private void saveACoupleOfRedisRecords() throws Exception{
		Jedis jedis = new Jedis(HOST);
		jedis.connect();
		jedis.del(KEY);
		jedis.rpush(KEY, "GBPUSD,78.99,"+datePlusSeconds(5));
		jedis.rpush(KEY, "GBPUSD,178.99,"+datePlusSeconds(8));
	}

	private long datePlusSeconds(int seconds){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.SECOND, seconds);
		return cal.getTimeInMillis();
	}

	private static class NormalizedToTradeTickTransformer implements MessageDenormalizer<TestBean> {

		@Override
		public TestBean transform(NormalizedMessage message) {
			String csv = message.getValue("value");
			String[] fields = csv.split(",");
			TestBean tick = new TestBean();
			tick.setDate(Long.parseLong(fields[2]));
			tick.setSymbol(fields[0]);
			tick.setValue(fields[1]);
			return tick;
		}

	}


}
