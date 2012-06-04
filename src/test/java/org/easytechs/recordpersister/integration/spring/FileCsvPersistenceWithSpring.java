package org.easytechs.recordpersister.integration.spring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.easytechs.recordpersister.Appender;
import org.easytechs.recordpersister.Persister;
import org.easytechs.recordpersister.TestBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@ContextConfiguration(locations="/application-context-test.xml")
public class FileCsvPersistenceWithSpring extends AbstractTestNGSpringContextTests{
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "fileCsvPersister")
	private Persister persister;

	
	private Appender fileAppender;
	
	@BeforeMethod
	public void setup(){
		Persister innerPersister = (Persister)ReflectionTestUtils.getField(persister, "persister");
		fileAppender = (Appender)ReflectionTestUtils.getField(innerPersister, "appender");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldPersist() throws Exception{
		List<TestBean> ticks = createTradeTickList(1100);
		for(TestBean tick : ticks){
			persister.persist(tick);
		}
		Thread.sleep(3000);
		fileAppender.close();
	}

	private List<TestBean> createTradeTickList(int many) {
		List<TestBean> ticks = new ArrayList<>();
		for(int i=0; i<many; i++){
			TestBean tick = new  TestBean();
			tick.setDate(123l);
			tick.setSymbol("XX"+i);
			tick.setValue("123"+i);
			ticks.add(tick);
		}
		return ticks;
	}
}
