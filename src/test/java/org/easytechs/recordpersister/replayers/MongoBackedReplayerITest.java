package org.easytechs.recordpersister.replayers;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.TestBean;
import org.easytechs.recordpersister.denormalizers.CsvToBeanDenormalizer;
import org.easytechs.recordpersister.normalizers.MongoDbObjectToValueNormalizedNormalizer;
import org.easytechs.recordpersister.replayers.RecordDateReplayer;
import org.easytechs.recordpersister.replayers.ReplayCallback;
import org.easytechs.recordpersister.replayers.date.BeanPropertyDateExtractor;
import org.easytechs.recordpersister.replayers.date.DateExtractor;
import org.easytechs.recordpersister.retrievers.AbstractDataRetriever;
import org.easytechs.recordpersister.retrievers.MongoRetriever;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoBackedReplayerITest {


	private static final String HOST = "127.0.0.1";
	private static final String PORT = "27017";
	private static final String DB = "test-db";
	private static final String COLLECTION = "ticksx";
	
	/**
	 */
	private RecordDateReplayer<TestBean> testObj;

	/**
	 */
	private DateExtractor<TestBean> dateExtractor = new BeanPropertyDateExtractor<TestBean>("date");

	/**
	 */
	private MessageDenormalizer<TestBean> normalizedInverseTransformer = new CsvToBeanDenormalizer<>(TestBean.class, "symbol","value","date");

	/**
	 */
	private AbstractDataRetriever<DBObject> retriever;

	@BeforeMethod
	public void setup() throws Exception{
		saveACoupleOfMongoRecords();
		testObj = new RecordDateReplayer<>();
		testObj.setDateExtractor(dateExtractor);
		testObj.setNormalizedTransformer(normalizedInverseTransformer);
		retriever =  new MongoRetriever(HOST, PORT, DB, COLLECTION);
		retriever.setTransformer(new MongoDbObjectToValueNormalizedNormalizer("tick"));
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
	
	private void saveACoupleOfMongoRecords() throws Exception{
		Mongo m = new Mongo(HOST , Integer.parseInt(PORT));
		DB db = m.getDB(DB);
		DBCollection coll = db.getCollection(COLLECTION);
		coll.drop();
		BasicDBObject object1 = new BasicDBObject("tick", "gbpusd,123.44,"+datePlusSeconds(5));
		BasicDBObject object2 = new BasicDBObject("tick", "gbpusd,125.44,"+datePlusSeconds(8));
		coll.insert(object1,object2);
	}

	private long datePlusSeconds(int seconds){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.SECOND, seconds);
		return cal.getTimeInMillis();
	}
}
