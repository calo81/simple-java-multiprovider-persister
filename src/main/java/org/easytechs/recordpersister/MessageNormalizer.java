package org.easytechs.recordpersister;

public interface MessageNormalizer <T extends Object>{

	NormalizedMessage transform(T object);

}
