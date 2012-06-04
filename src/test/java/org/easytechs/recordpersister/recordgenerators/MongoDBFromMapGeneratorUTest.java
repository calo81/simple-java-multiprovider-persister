package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.recordgenerators.MongoDBFromMapGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mongodb.DBObject;

public class MongoDBFromMapGeneratorUTest {
	/**
	 */
	private MongoDBFromMapGenerator testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new MongoDBFromMapGenerator();
	}
	
	@Test
	public void testGeneration(){
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("key1", "345");
		message.addValue("key2", "yeah");
		DBObject object = testObj.generate(message);
		Assert.assertEquals(object.get("key1"), "345");
		Assert.assertEquals(object.get("key2"), "yeah");
	}
}
