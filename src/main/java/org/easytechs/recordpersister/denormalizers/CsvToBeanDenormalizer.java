package org.easytechs.recordpersister.denormalizers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.NormalizedMessage;


public class CsvToBeanDenormalizer <T extends Object> implements MessageDenormalizer<T>{

	private String[] properties;
	private Class<T> klass;
	public CsvToBeanDenormalizer(Class<T> klassTo,String... properties){
		this.properties=properties;
		this.klass=klassTo;
	}
	@Override
	public T transform(NormalizedMessage message) {
		try {
			return doTransform(message);
		} catch (InstantiationException | IllegalAccessException
				| InvocationTargetException e) {
			throw new RuntimeException("Unable to denormalize into bean of type "+klass,e);
		}
	}
	private T doTransform(NormalizedMessage message)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		T object = klass.newInstance();
		String[] values = message.getValue("value").split(",");
		for(int i=0;i<properties.length;i++){
			BeanUtils.setProperty(object, properties[i], values[i]);
		}
		
		return object;
	}

}
