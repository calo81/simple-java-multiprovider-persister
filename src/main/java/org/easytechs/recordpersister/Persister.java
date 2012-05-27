package org.easytechs.recordpersister;

public interface Persister<T extends Object> {
	void persist(T object);
}
