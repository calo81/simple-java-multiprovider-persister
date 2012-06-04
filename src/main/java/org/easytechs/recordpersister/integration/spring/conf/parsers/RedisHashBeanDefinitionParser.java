package org.easytechs.recordpersister.integration.spring.conf.parsers;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.RedisHashAppender;
import org.easytechs.recordpersister.normalizers.BeanToMapNormalizer;
import org.easytechs.recordpersister.recordgenerators.MapFromNormalizerGenerator;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


public class RedisHashBeanDefinitionParser extends AbstractBeanDefinitionParser{

	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder persister = BeanDefinitionBuilder.rootBeanDefinition(GenericPersister.class);
		BeanDefinitionBuilder normalizer = BeanDefinitionBuilder.rootBeanDefinition(BeanToMapNormalizer.class);
		BeanDefinitionBuilder appender = BeanDefinitionBuilder.rootBeanDefinition(RedisHashAppender.class);
		BeanDefinitionBuilder generator = BeanDefinitionBuilder.rootBeanDefinition(MapFromNormalizerGenerator.class);
		
		String host = element.getAttribute("host");
		String collection = element.getAttribute("list");
		String beanProperties = element.getAttribute("beanProperties");
		
		appender.addConstructorArgValue(host);
		appender.addConstructorArgValue(collection);
		appender.addPropertyValue("recordGenerator", generator.getBeanDefinition());
		
		normalizer.addConstructorArgValue(beanProperties.split(","));
		
		persister.addPropertyValue("normalizedMessageTransformer", normalizer.getBeanDefinition());
		persister.addPropertyValue("appender", appender.getBeanDefinition());
		return persister.getBeanDefinition();
	}

}
