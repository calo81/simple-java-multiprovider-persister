package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoDBSimpleNormalizedGenerator implements RecordGenerator<DBObject> {

	/**
	 */
	private String key;
	public MongoDBSimpleNormalizedGenerator(String key){
		this.key=key;
	}
	
	@Override
	public DBObject generate(NormalizedMessage message) {
		BasicDBObject doc = new BasicDBObject();
		doc.put(key, message.getValue("value"));
		return doc;
	}

}
