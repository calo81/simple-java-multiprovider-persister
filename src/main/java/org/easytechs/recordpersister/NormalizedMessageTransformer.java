package org.easytechs.recordpersister;

public interface NormalizedMessageTransformer <T extends Object>{

	NormalizedMessage transform(T object);

}
