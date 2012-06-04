package org.easytechs.recordpersister.integration.spring.conf.parsers;

import org.easytechs.recordpersister.appenders.FileAppender;
import org.easytechs.recordpersister.appenders.file.NOPPathFacilitator;
import org.easytechs.recordpersister.appenders.file.TimeBasedPathFacilitator;
import org.easytechs.recordpersister.appenders.file.postprocessors.NOPFilePostProcessor;
import org.easytechs.recordpersister.appenders.file.postprocessors.ZipFilePostProcessor;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.easytechs.recordpersister.recordgenerators.ToStringRecordGenerator;
import org.easytechs.recordpersister.utils.DateUtilsImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;


public class FileCsvBeanDefinitionParser extends AbstractPersisterBeanDefinitionParser{

	@Override
	protected void configureGenerator(Element element,BeanDefinitionBuilder generator) {
		generator.addConstructorArgValue("value");
	}

	@Override
	protected void configureAppender(Element element,BeanDefinitionBuilder appender) {
		String rootDir = element.getAttribute("rootDir");
		String filePrefix = element.getAttribute("filePrefix");
		String fileSuffix = element.getAttribute("fileSuffix");
		String buffered = element.getAttribute("buffered");
		String rollStrategyString = element.getAttribute("rollStrategy");
		String zip = element.getAttribute("zip");
		BeanDefinitionBuilder rollStrategy = null;
		if(rollStrategyString!=null){
			rollStrategy = BeanDefinitionBuilder.rootBeanDefinition(TimeBasedPathFacilitator.class);
			rollStrategy.addPropertyValue("rootDir", rootDir);
			rollStrategy.addPropertyValue("filePrefix", filePrefix);
			rollStrategy.addPropertyValue("fileSuffix", fileSuffix);
			rollStrategy.addPropertyValue("dateUtils", new DateUtilsImpl());
			rollStrategy.addPropertyValue("genericPrefix", "");
		}else{
			rollStrategy = BeanDefinitionBuilder.rootBeanDefinition(NOPPathFacilitator.class);
			rollStrategy.addConstructorArgValue(rootDir+"/"+filePrefix+fileSuffix);
		}
		if(zip!=null && zip.equalsIgnoreCase("true")){
			appender.addPropertyValue("postProcessor", new ZipFilePostProcessor());
		}
		else{
			appender.addPropertyValue("postProcessor", new NOPFilePostProcessor());
		}
		appender.addConstructorArgValue(rollStrategy.getBeanDefinition());
		appender.addConstructorArgValue(buffered);
	}

	@Override
	protected void configureNormalizer(Element element,BeanDefinitionBuilder normalizer) {
		String beanProperties = element.getAttribute("beanProperties");
		normalizer.addConstructorArgValue(beanProperties.split(","));
	}

	@Override
	protected Class getGeneratorClass() {
		return ToStringRecordGenerator.class;
	}

	@Override
	protected Class getAppenderClass() {
		return FileAppender.class;
	}

	@Override
	protected Class getNormalizerClass() {
		return BeanToCsvStringNormalizer.class;
	}

}
