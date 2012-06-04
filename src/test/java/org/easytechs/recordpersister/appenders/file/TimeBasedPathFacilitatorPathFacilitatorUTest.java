package org.easytechs.recordpersister.appenders.file;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.easytechs.recordpersister.appenders.file.TimeBasedPathFacilitator;
import org.easytechs.recordpersister.utils.DateUtils;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TimeBasedPathFacilitatorPathFacilitatorUTest {
	private TimeBasedPathFacilitator testObj;
	
	@Mock
	private DateUtils dateUtils;
	
	@BeforeMethod
	public void setup(){
		MockitoAnnotations.initMocks(this);
		testObj = new TimeBasedPathFacilitator();
		testObj.setRootDir("/tmp");
		testObj.setFilePrefix("file");
		testObj.setFileSuffix(".tt");
		testObj.setGenericPrefix("tick");
		testObj.setDateUtils(dateUtils);
	}
	
	@Test
	public void shouldReturnPathForYearMonthAndDay(){
		Mockito.when(dateUtils.getCurrentYear()).thenReturn(2000);
		Mockito.when(dateUtils.getCurrentMonth()).thenReturn(12);
		Mockito.when(dateUtils.getCurrentDay()).thenReturn(18);
		Mockito.when(dateUtils.getCurrentHour()).thenReturn(12);
		Path path = testObj.getPath();
		Assert.assertEquals(path, Paths.get("/tmp", "tick2000/12/18/file-12.tt"));
		Mockito.when(dateUtils.getCurrentMonth()).thenReturn(5);
		path = testObj.getPath();
		Assert.assertEquals(path, Paths.get("/tmp", "tick2000/5/18/file-12.tt"));
	}
}
