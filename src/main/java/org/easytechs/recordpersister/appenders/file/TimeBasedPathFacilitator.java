package org.easytechs.recordpersister.appenders.file;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.easytechs.recordpersister.utils.DateUtils;


public class TimeBasedPathFacilitator implements PathFacilitator{

	private String filePrefix;
	private String fileSuffix;
	private String genericPrefix;
	private DateUtils dateUtils;
	private String rootDir;

	public void setFilePrefix(String prefix) {
		this.filePrefix = prefix;
	}

	public void setFileSuffix(String suffix) {
		this.fileSuffix = suffix;
	}

	public void setGenericPrefix(String prefix) {
		this.genericPrefix = prefix;
	}

	public void setDateUtils(DateUtils dateUtils) {
		this.dateUtils=dateUtils;
	}

	public Path getPath() {
		return Paths.get(rootDir, genericPrefix+dateUtils.getCurrentYear(),String.valueOf(dateUtils.getCurrentMonth()),String.valueOf(dateUtils.getCurrentDay()),filePrefix+"-"+dateUtils.getCurrentHour()+fileSuffix);
	}

	public void setRootDir(String dir) {
		this.rootDir=dir;
	}

}
