package org.easytechs.recordpersister.normalizers;

import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;

import com.mongodb.DBObject;

public class MongoDbObjectToMapNormalizer implements MessageNormalizer<DBObject>{

	@Override
	public NormalizedMessage transform(DBObject object) {
		NormalizedMessage message = new NormalizedMessage();
		for(String key:object.keySet()){
			message.addValue(key, object.get(key).toString());
		}
		return message;
	}
}
