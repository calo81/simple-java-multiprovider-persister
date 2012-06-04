package org.easytechs.recordpersister.replayers.date;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanPropertyDateExtractor<T extends Object> implements DateExtractor<T>{

	/**
	 */
	private String property;
	public BeanPropertyDateExtractor(String property){
		this.property=property;
	}
	@Override
	public long extractDate(T object) {
		try {
			return (long)PropertyUtils.getSimpleProperty(object, property);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Can't extratc date long from this property. It should return a long ",e);
		}
	}
}
