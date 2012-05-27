package org.easytechs.recordpersister;

public class GenericPersister<T extends Object> implements Persister<T>{

	private NormalizedMessageTransformer<T> normalizedMessageTransformer;
	private Appender appender;

	@Override
	public void persist(T object) {
		NormalizedMessage normalizedMessage = normalizedMessageTransformer.transform(object);
		appender.append(normalizedMessage);
		
	}

	public void setAppender(Appender appender) {
		this.appender=appender;
	}

	public void setNormalizedMessageTransformer(NormalizedMessageTransformer<T> normalizedMessageTransformer) {
		this.normalizedMessageTransformer=normalizedMessageTransformer;
	}

}
