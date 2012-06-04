package org.easytechs.recordpersister.integration.spring.conf.parsers;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.QueuedPersisterDecorator;
import org.easytechs.recordpersister.appenders.AccumulatorAppender;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


public abstract class AbstractPersisterBeanDefinitionParser extends AbstractBeanDefinitionParser{

	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		
		BeanDefinitionBuilder persister = BeanDefinitionBuilder.rootBeanDefinition(GenericPersister.class);
		BeanDefinitionBuilder normalizer = BeanDefinitionBuilder.rootBeanDefinition(getNormalizerClass());
		BeanDefinitionBuilder appender = BeanDefinitionBuilder.rootBeanDefinition(getAppenderClass());
		BeanDefinitionBuilder generator = BeanDefinitionBuilder.rootBeanDefinition(getGeneratorClass());
		String buffered = element.getAttribute("buffered");
		String queued = element.getAttribute("queued");
		
		configureNormalizer(element,normalizer);
		configureAppender(element,appender);
		configureGenerator(element,generator);
		
		appender.addPropertyValue("recordGenerator", generator.getBeanDefinition());
		persister.addPropertyValue("appender", appender.getBeanDefinition());
		persister.addPropertyValue("normalizedMessageTransformer", normalizer.getBeanDefinition());
		
		if(buffered!=null && buffered.equalsIgnoreCase("true")){
			BeanDefinitionBuilder accumulateAppender = BeanDefinitionBuilder.rootBeanDefinition(AccumulatorAppender.class);
			accumulateAppender.addConstructorArgValue(10);
			accumulateAppender.addConstructorArgValue(appender.getBeanDefinition());
			appender = accumulateAppender;
		}
		
		if(queued!=null && queued.equalsIgnoreCase("true")){
			BeanDefinitionBuilder queuedPersister = BeanDefinitionBuilder.rootBeanDefinition(QueuedPersisterDecorator.class);
			queuedPersister.addConstructorArgValue(persister.getBeanDefinition());
			queuedPersister.addConstructorArgValue(10000);
			persister = queuedPersister;
		}
			
		return persister.getBeanDefinition();
	}

	protected abstract void configureGenerator(Element element,BeanDefinitionBuilder generator);

	protected abstract void configureAppender(Element element,BeanDefinitionBuilder appender);

	protected abstract void configureNormalizer(Element element,BeanDefinitionBuilder normalizer);

	protected abstract Class getGeneratorClass();

	protected abstract Class getAppenderClass();

	protected abstract Class getNormalizerClass();

}
