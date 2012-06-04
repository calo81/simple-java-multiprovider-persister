package org.easytechs.recordpersister.normalizers;

import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;

import com.mongodb.DBObject;

public class MongoDbObjectToValueNormalizedNormalizer implements MessageNormalizer<DBObject>{

	/**
	 */
	private String mongoPropertyWithValue;
	
	public MongoDbObjectToValueNormalizedNormalizer(String mongoPropertyWithValue){
		this.mongoPropertyWithValue=mongoPropertyWithValue;
	}
	
	@Override
	public NormalizedMessage transform(DBObject object) {
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", object.get(mongoPropertyWithValue).toString());
		return message;
	}

}
