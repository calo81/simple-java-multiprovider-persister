package org.easytechs.recordpersister;

import org.easytechs.recordpersister.replayers.ReplayCallback;

public interface Replayer<T extends Object>{
	void replay(ReplayCallback<T> callback);
}
