package org.easytechs.recordpersister.denormalizers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.NormalizedMessage;


public class MapToBeanPropertiesDenormalizer<T extends Object> implements MessageDenormalizer<T>{

	/**
	 */
	private Class<T> klass;
	public MapToBeanPropertiesDenormalizer(Class<T> klass){
		this.klass=klass;
	}
	@Override
	public T transform(NormalizedMessage message) {
		try {
			return doTransform(message);
		} catch (InstantiationException | IllegalAccessException
				| InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to create object from Normalized Message",e);
		}
	}
	private T doTransform(NormalizedMessage message)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		T object = klass.newInstance();
		BeanUtils.populate(object, message.getValues());
		return object;
	}

}
