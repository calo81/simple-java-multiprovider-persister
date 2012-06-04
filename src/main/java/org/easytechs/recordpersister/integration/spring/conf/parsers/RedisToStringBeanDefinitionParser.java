package org.easytechs.recordpersister.integration.spring.conf.parsers;

import org.easytechs.recordpersister.GenericPersister;
import org.easytechs.recordpersister.appenders.RedisAppender;
import org.easytechs.recordpersister.normalizers.ToStringInValueKeyNormalizer;
import org.easytechs.recordpersister.recordgenerators.RedisKeyValueRecordGenerator;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


public class RedisToStringBeanDefinitionParser extends AbstractBeanDefinitionParser{

	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder persister = BeanDefinitionBuilder.rootBeanDefinition(GenericPersister.class);
		BeanDefinitionBuilder normalizer = BeanDefinitionBuilder.rootBeanDefinition(ToStringInValueKeyNormalizer.class);
		BeanDefinitionBuilder appender = BeanDefinitionBuilder.rootBeanDefinition(RedisAppender.class);
		BeanDefinitionBuilder generator = BeanDefinitionBuilder.rootBeanDefinition(RedisKeyValueRecordGenerator.class);
		
		String host = element.getAttribute("host");
		String collection = element.getAttribute("list");
		
		generator.addConstructorArgValue(collection);
		
		appender.addConstructorArgValue(host);
		appender.addPropertyValue("recordGenerator", generator.getBeanDefinition());
		
		
		persister.addPropertyValue("normalizedMessageTransformer", normalizer.getBeanDefinition());
		persister.addPropertyValue("appender", appender.getBeanDefinition());
		
		return persister.getBeanDefinition();
	}

}
