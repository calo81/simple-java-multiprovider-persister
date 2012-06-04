package org.easytechs.recordpersister.normalizers;

import java.util.Map;

import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;


public class FromMapNormalizer implements MessageNormalizer<Map<String, String>>{

	@Override
	public NormalizedMessage transform(Map<String, String> object) {
		NormalizedMessage message = new NormalizedMessage();
		message.setValues(object);
		return message;
	}
}
