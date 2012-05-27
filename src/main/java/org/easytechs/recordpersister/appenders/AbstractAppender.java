package org.easytechs.recordpersister.appenders;

import org.easytechs.recordpersister.Appender;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;


public abstract class AbstractAppender<T extends Object> implements Appender{
	protected RecordGenerator<T> recordGenerator;
	
	@Override
	public void append(NormalizedMessage normalizedMessage) {
		T record = recordGenerator.generate(normalizedMessage);
		try{
			doAppend(record);
		}catch(Exception e){
			e.printStackTrace();
			//Anything else to do here???
		}
	}

	protected abstract void doAppend(T record) throws Exception;
	
	public void setRecordGenerator(RecordGenerator<T> recordGenerator){
		this.recordGenerator = recordGenerator;
	}
}
