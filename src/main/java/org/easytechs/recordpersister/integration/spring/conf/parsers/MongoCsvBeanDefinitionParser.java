package org.easytechs.recordpersister.integration.spring.conf.parsers;

import org.easytechs.recordpersister.appenders.MongoAppender;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.MongoDBSimpleNormalizedGenerator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;


public class MongoCsvBeanDefinitionParser extends AbstractPersisterBeanDefinitionParser{


	@Override
	protected void configureGenerator(Element element,
			BeanDefinitionBuilder generator) {
		
	}

	@Override
	protected void configureAppender(Element element,
			BeanDefinitionBuilder appender) {
		String host = element.getAttribute("host");
		String port = element.getAttribute("port");
		String db = element.getAttribute("db");
		String collection = element.getAttribute("collection");
		appender.addConstructorArgValue(host);
		appender.addConstructorArgValue(port);
		appender.addConstructorArgValue(db);
		appender.addConstructorArgValue(collection);
		
	}

	@Override
	protected void configureNormalizer(Element element,
			BeanDefinitionBuilder normalizer) {
		String beanProperties = element.getAttribute("beanProperties");
		normalizer.addConstructorArgValue(beanProperties);
		
	}

	@Override
	protected Class getGeneratorClass() {
		return MongoDBSimpleNormalizedGenerator.class;
	}

	@Override
	protected Class getAppenderClass() {
		return MongoAppender.class;
	}

	@Override
	protected Class getNormalizerClass() {
		return BeanToCsvStringNormalizer.class;
	}

}
