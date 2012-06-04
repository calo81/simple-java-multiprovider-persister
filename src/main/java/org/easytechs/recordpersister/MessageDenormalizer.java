package org.easytechs.recordpersister;

public interface MessageDenormalizer<T extends Object> {
	T transform(NormalizedMessage message);
}
