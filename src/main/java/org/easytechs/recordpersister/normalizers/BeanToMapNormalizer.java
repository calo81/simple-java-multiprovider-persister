package org.easytechs.recordpersister.normalizers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;


public class BeanToMapNormalizer  <T extends Object> implements MessageNormalizer<T>{
 
	/**
	 */
	private String[] properties;
	
	public BeanToMapNormalizer(String... properties){
		this.properties=properties;
	}
	
	@Override
	public NormalizedMessage transform(T object) {
		NormalizedMessage message = new NormalizedMessage();
		for(String property:properties){
			try {
				message.addValue(property, PropertyUtils.getSimpleProperty(object, property).toString());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException("Imposible to normalize this message ",e);
			}
		}
		return message;
	}

}
