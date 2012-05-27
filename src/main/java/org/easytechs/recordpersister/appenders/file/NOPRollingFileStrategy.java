package org.easytechs.recordpersister.appenders.file;


public class NOPRollingFileStrategy implements RollingFileStrategy {

	@Override
	public boolean shouldRoll() {
		return false;
	}

}
