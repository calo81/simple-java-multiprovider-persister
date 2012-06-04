package org.easytechs.recordpersister.normalizers;

import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;

public class StringToValueNormalizedNormalizer implements MessageNormalizer<String>{

	@Override
	public NormalizedMessage transform(String value) {
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", value);
		return message;
	}
}
