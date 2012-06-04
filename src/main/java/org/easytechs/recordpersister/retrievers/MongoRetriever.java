package org.easytechs.recordpersister.retrievers;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoRetriever extends AbstractDataRetriever<DBObject> {

	/**
	 */
	private DBCollection coll;

	public MongoRetriever(String host, String port, String dbName, String collection) throws Exception {
		Mongo m = new Mongo(host, Integer.parseInt(port));
		DB db = m.getDB(dbName);
		coll = db.getCollection(collection);
	}

	@Override
	protected List<DBObject> getElements(int howMany) {
		List<DBObject> list = new ArrayList<>();
		DBCursor cursor = coll.find();
		for (int i = 0; i < coll.count() && i < howMany; i++) {
			list.add(cursor.next());
		}
		return list;
	}
}
