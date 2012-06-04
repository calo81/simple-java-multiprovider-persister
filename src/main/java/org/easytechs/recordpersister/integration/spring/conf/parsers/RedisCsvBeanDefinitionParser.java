package org.easytechs.recordpersister.integration.spring.conf.parsers;

import org.easytechs.recordpersister.appenders.RedisAppender;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.RedisKeyValueRecordGenerator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;


public class RedisCsvBeanDefinitionParser extends AbstractPersisterBeanDefinitionParser{

	@Override
	protected void configureGenerator(Element element,
			BeanDefinitionBuilder generator) {
		String collection = element.getAttribute("list");
		generator.addConstructorArgValue(collection);
	}

	@Override
	protected void configureAppender(Element element,
			BeanDefinitionBuilder appender) {
		String host = element.getAttribute("host");
		appender.addConstructorArgValue(host);
		
	}

	@Override
	protected void configureNormalizer(Element element,
			BeanDefinitionBuilder normalizer) {
		String beanProperties = element.getAttribute("beanProperties");
		normalizer.addConstructorArgValue(beanProperties.split(","));
		
	}

	@Override
	protected Class getGeneratorClass() {
		return RedisKeyValueRecordGenerator.class;
	}

	@Override
	protected Class getAppenderClass() {
		return RedisAppender.class;
	}

	@Override
	protected Class getNormalizerClass() {
		return BeanToCsvStringNormalizer.class;
	}

}
