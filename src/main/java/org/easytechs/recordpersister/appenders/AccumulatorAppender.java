package org.easytechs.recordpersister.appenders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccumulatorAppender<T extends Object> extends AbstractAppender<T>{

	private int maxApproximate;
	private AbstractAppender<T> delegateAppender;
	private List<T> accumulated;

	@SuppressWarnings("unchecked")
	public AccumulatorAppender(int max,AbstractAppender<T> delegateAppender){
		this.maxApproximate= max;
		this.delegateAppender=delegateAppender;
		accumulated = (List<T>)Collections.synchronizedList(new ArrayList<>(max));
	}
	
	@Override
	public void close() {
		delegateAppender.close();
	}

	@Override
	protected void doAppend(T record) throws Exception {
		accumulated.add(record);
		if(accumulated.size()>=maxApproximate){
			synchronized (accumulated) {
				this.delegateAppender.doBatchAppend(new ArrayList<>(accumulated));	
				accumulated.clear();
			}		
		}	
	}

}
