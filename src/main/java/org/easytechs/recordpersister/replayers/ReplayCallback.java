package org.easytechs.recordpersister.replayers;

public interface ReplayCallback<T> {
	void replay(T value);
}
