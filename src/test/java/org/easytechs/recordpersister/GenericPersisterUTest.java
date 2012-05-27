package org.easytechs.recordpersister;

import org.easytechs.recordpersister.Appender;
import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.NormalizedMessageTransformer;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GenericPersisterUTest {
	private GenericPersister<Object> testObj;
	
	@Mock
	private Appender appender;
	
	@Mock
	private NormalizedMessageTransformer<Object> normalizedMessageTransformer;
	
	@BeforeMethod
	public void setup(){
		MockitoAnnotations.initMocks(this);
		testObj = new GenericPersister<>();
		testObj.setAppender(appender);
		testObj.setNormalizedMessageTransformer(normalizedMessageTransformer);
	}
	
	@Test
	public void shouldPersistAnyObject(){
		Object x = new Object();
		NormalizedMessage normalizedMessage = new NormalizedMessage();
		Mockito.when(normalizedMessageTransformer.transform(x)).thenReturn(normalizedMessage);
		testObj.persist(x);
		Mockito.verify(normalizedMessageTransformer).transform(x);
		Mockito.verify(appender).append(normalizedMessage);
	}
}
