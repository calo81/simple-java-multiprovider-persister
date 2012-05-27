package org.easytechs.recordpersister.replayers;

public interface Replayer<T extends Object>{
	void replay(ReplayCallback<T> callback);
}
