package org.easytechs.recordpersister.appenders;

import java.util.ArrayList;
import java.util.List;

import org.easytechs.recordpersister.Appender;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;



public abstract class AbstractAppender<T extends Object> implements Appender{
	/**
	 */
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
	
	@Override
	public final void append(List<NormalizedMessage> messages){
		List<T> records = new ArrayList<>();
		for(NormalizedMessage message:messages){
			records.add(recordGenerator.generate(message));
		}
		doBatchAppend(records);
	}

	/**
	 * Basic implementation. Override if the appender supports batch processing
	 * @param records
	 */
	protected void doBatchAppend(List<T> records){
		for(T record:records){
			try{
				doAppend(record);
			}catch(Exception e){
				e.printStackTrace();
				//Anything else to do here???
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}


	protected abstract void doAppend(T record) throws Exception;
	
	public void setRecordGenerator(RecordGenerator<T> recordGenerator){
		this.recordGenerator = recordGenerator;
	}
}
