package org.easytechs.recordpersister.transformers;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.NormalizedMessageTransformer;

public class ToStringTransformerInValueKey<T extends Object> implements NormalizedMessageTransformer<T>{

	@Override
	public NormalizedMessage transform(T tick) {
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", tick.toString());
		return message;
	}	
}
