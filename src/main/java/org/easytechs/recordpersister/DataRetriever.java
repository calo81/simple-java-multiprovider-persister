package org.easytechs.recordpersister;

import java.util.List;


public interface DataRetriever<T extends Object> {
	List<NormalizedMessage> retrieve(int howMany);
}
