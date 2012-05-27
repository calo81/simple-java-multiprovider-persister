package org.easytechs.recordpersister.appenders;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoAppender extends AbstractAppender<BasicDBObject>{

	private DBCollection coll;
	public MongoAppender(String host, String port, String dbName, String collection) throws Exception{
		Mongo m = new Mongo(host , Integer.parseInt(port));
		DB db = m.getDB(dbName);
		coll = db.getCollection(collection);
	}

	@Override
	public void close() {
		
	}

	@Override
	protected void doAppend(BasicDBObject record) throws Exception {
		coll.insert(record);
	}

}
