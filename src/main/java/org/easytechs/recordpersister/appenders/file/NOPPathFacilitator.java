package org.easytechs.recordpersister.appenders.file;

import java.nio.file.Path;
import java.nio.file.Paths;


public class NOPPathFacilitator implements PathFacilitator {

	private Path filePath;
	public NOPPathFacilitator(String filePath){
		this.filePath=Paths.get(filePath, new String[]{});
	}
	@Override
	public Path getPath() {
		return filePath;
	}

}
