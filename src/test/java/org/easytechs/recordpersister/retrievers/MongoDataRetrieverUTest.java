package org.easytechs.recordpersister.retrievers;

import java.util.Date;
import java.util.List;

import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.retrievers.MongoRetriever;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDataRetrieverUTest {

	private static final String HOST = "127.0.0.1";
	private static final String PORT = "27017";
	private static final String DB = "test-db";
	private static final String COLLECTION = "ticks";

	/**
	 */
	private MongoRetriever testObj;
	
	/**
	 */
	@Mock
	private MessageNormalizer<DBObject> transformer;

	@BeforeMethod
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		testObj = new MongoRetriever(HOST, PORT, DB, COLLECTION);
		testObj.setTransformer(transformer);
		Mongo m = new Mongo(HOST, Integer.parseInt(PORT));
		DB db = m.getDB(DB);
		DBCollection coll = db.getCollection(COLLECTION);
		for (int i = 0; i < 100; i++) {
			BasicDBObject doc = new BasicDBObject();
			doc.put("tick", "GBPUSD,123.44," + new Date().getTime());
			coll.insert(doc);
		}
		Mockito.when(transformer.transform(Mockito.any(BasicDBObject.class))).thenReturn(new NormalizedMessage());
	}

	@Test
	public void shouldRetrieveAmountElements() {
		List<NormalizedMessage> messages = testObj.retrieve(52);
		Assert.assertEquals(messages.size(),52);
	}
}
