package org.easytechs.recordpersister.integration.spring.conf;

import org.easytechs.recordpersister.integration.spring.conf.parsers.FileCsvBeanDefinitionParser;
import org.easytechs.recordpersister.integration.spring.conf.parsers.FileToStringBeanDefinitionParser;
import org.easytechs.recordpersister.integration.spring.conf.parsers.MongoCsvBeanDefinitionParser;
import org.easytechs.recordpersister.integration.spring.conf.parsers.MongoDocumentBeanDefinitionParser;
import org.easytechs.recordpersister.integration.spring.conf.parsers.RedisHashBeanDefinitionParser;
import org.easytechs.recordpersister.integration.spring.conf.parsers.RedisToStringBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class PersistenceNamespaceHandler extends NamespaceHandlerSupport{

	@Override
	public void init() {
		registerBeanDefinitionParser("mongo-document-persister", new MongoDocumentBeanDefinitionParser());
		registerBeanDefinitionParser("file-csv-persister", new FileCsvBeanDefinitionParser());
		registerBeanDefinitionParser("file-tostring-persister", new FileToStringBeanDefinitionParser());		
		registerBeanDefinitionParser("redis-hash-persister", new RedisHashBeanDefinitionParser());
		registerBeanDefinitionParser("redis-tostring-persister", new RedisToStringBeanDefinitionParser());
		registerBeanDefinitionParser("redis-csv-persister", new RedisToStringBeanDefinitionParser());
		registerBeanDefinitionParser("mongo-csv-persister", new MongoCsvBeanDefinitionParser());
	}

}
