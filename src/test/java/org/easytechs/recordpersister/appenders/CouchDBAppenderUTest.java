package org.easytechs.recordpersister.appenders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.RedisAppender;
import org.easytechs.recordpersister.appenders.redis.KeyValue;
import org.easytechs.recordpersister.recordgenerators.MapFromNormalizerGenerator;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ViewResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.svenson.JSONParser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;


public class CouchDBAppenderUTest extends AbstractTimedTest{
	private static final String DB = "testdb";
	/**
	 */
	private CouchDBAppender testObj;
	private static final String HOST = "127.0.0.1";
	private Database db;

	/**
	 */
	private RecordGenerator<Map<String, String>> recordGenerator;


	@BeforeMethod
	public void setup() {
		recordGenerator = new MapFromNormalizerGenerator();
		db = new Database(HOST, DB);
		testObj = new CouchDBAppender(HOST, DB);
		testObj.setRecordGenerator(recordGenerator);
		
		
	}

	@Test(invocationCount=10)
	public void shouldPersistSimpleRequest() {
		doTimed(new IndexedRunnable() {
			@Override
			public void run(int index) throws Exception {
				NormalizedMessage message = new NormalizedMessage();		
				message.addValue("symbol", "SSRR");
				message.addValue("value", "10.99");
				testObj.append(message);
			}
			
		}, 20);
		ViewResult<Map> result = db.listDocuments(new Options().limit(1), new JSONParser());
		Map values = db.getDocument(Map.class, result.getRows().get(0).getId());
		Assert.assertEquals(values.get("value"), "10.99");
		Assert.assertEquals(values.get("symbol"), "SSRR");
	}

}
