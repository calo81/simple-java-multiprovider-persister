package org.easytechs.recordpersister.normalizers;

import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;

public class ToStringInValueKeyNormalizer<T extends Object> implements MessageNormalizer<T>{

	@Override
	public NormalizedMessage transform(T object) {
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", object.toString());
		return message;
	}	
}
