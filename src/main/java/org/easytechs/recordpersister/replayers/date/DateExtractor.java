package org.easytechs.recordpersister.replayers.date;

public interface DateExtractor<T extends Object> {
	long extractDate(T object);
}
