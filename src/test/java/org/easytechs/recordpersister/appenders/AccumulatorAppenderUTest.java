package org.easytechs.recordpersister.appenders;

import java.util.List;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.AbstractAppender;
import org.easytechs.recordpersister.appenders.AccumulatorAppender;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AccumulatorAppenderUTest extends AbstractAppender<Object>{
	
	private AccumulatorAppender<Object> testObj;
	
	@Mock
	private AbstractAppender<Object> delegateAppender;
	
	@Mock
	private RecordGenerator<Object> generator;
	
	@BeforeMethod
	public void setup(){
		MockitoAnnotations.initMocks(this);
		testObj = new AccumulatorAppender<>(2,delegateAppender);
		testObj.setRecordGenerator(generator);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldAccumulateMessages(){
		NormalizedMessage o1 = new NormalizedMessage();
		NormalizedMessage o2 = new NormalizedMessage();
		Object oo1=new Object();
		Object oo2 = new Object();
		Mockito.when(generator.generate(Mockito.same(o1))).thenReturn(oo1);
		Mockito.when(generator.generate(Mockito.same(o2))).thenReturn(oo2);
		testObj.append(o1);
		testObj.append(o2);
		ArgumentCaptor<List> listCapture = ArgumentCaptor.forClass(List.class);
		Mockito.verify(delegateAppender).doBatchAppend(listCapture.capture());
		List list = listCapture.getValue();
		Assert.assertEquals(list.get(0), oo1);
		Assert.assertEquals(list.get(1), oo2);
	}

	@Override
	public void close() {
		
	}

	@Override
	protected void doAppend(Object record) throws Exception {
		
	}
	
	@Override
	protected void doBatchAppend(List<Object> record){
		
	}
}
