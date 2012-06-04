package org.easytechs.recordpersister;

import org.apache.log4j.Logger;

public class GenericPersister<T extends Object> implements Persister<T> {

	/**
	 */
	private MessageNormalizer<T> normalizedMessageTransformer;
	/**
	 */
	private Appender appender;

	private static Logger LOG = Logger.getLogger(GenericPersister.class);

	@Override
	public void persist(T object) {
		try {
			NormalizedMessage normalizedMessage = normalizedMessageTransformer
					.transform(object);
			appender.append(normalizedMessage);
		} catch (Exception e) {
			LOG.error("Unable to persist message " + object, e);
		}

	}

	/**
	 * @param appender
	 */
	public void setAppender(Appender appender) {
		this.appender = appender;
	}

	public void setNormalizedMessageTransformer(
			MessageNormalizer<T> normalizedMessageTransformer) {
		this.normalizedMessageTransformer = normalizedMessageTransformer;
	}

}
