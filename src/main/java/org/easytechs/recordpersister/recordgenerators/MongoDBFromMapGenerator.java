package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoDBFromMapGenerator implements RecordGenerator<DBObject> {

	@Override
	public DBObject generate(NormalizedMessage message) {
		BasicDBObject object = new BasicDBObject();
		for(String key: message.getValues().keySet()){
			object.append(key, message.getValue(key));
		}
		return object;
	}

}
