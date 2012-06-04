package org.easytechs.recordpersister.normalizers;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;


public class BeanToCsvStringNormalizer <T extends Object> implements MessageNormalizer<T>{

	/**
	 */
	private String[] properties;

	public BeanToCsvStringNormalizer(String... properties){
		this.properties = properties;
	}
	
	@Override
	public NormalizedMessage transform(T object) {
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", generateCsv(object));
		return message;
	}

	private String generateCsv(T object) {
		StringBuilder csv = new StringBuilder();
		for(String property: properties){
			try {
				csv.append(PropertyUtils.getSimpleProperty(object, property)).append(",");
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
		return csv.deleteCharAt(csv.length()-1).toString();
	}
}
