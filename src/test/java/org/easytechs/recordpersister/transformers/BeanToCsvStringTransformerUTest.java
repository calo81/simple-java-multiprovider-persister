package org.easytechs.recordpersister.transformers;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.normalizers.BeanToCsvStringNormalizer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class BeanToCsvStringTransformerUTest {
	/**
	 */
	private BeanToCsvStringNormalizer<ExampleBean> testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new BeanToCsvStringNormalizer<>("one","two");
	}
	
	@Test
	public void shouldTransformTheBeanToCsvString(){
		ExampleBean bean = new ExampleBean();
		bean.setOne("One is");
		bean.setTwo(33);
		bean.setThree("the three muske");
		NormalizedMessage message = testObj.transform(bean);
		Assert.assertEquals(message.getValue("value"), "One is,33");
	}
	
	
	/**
	 * @author  cscarioni
	 */
	public static class ExampleBean{
		private String one;
		private int two;
		private String three;
		/**
		 * @return
		 */
		public String getOne() {
			return one;
		}
		/**
		 * @param one
		 */
		public void setOne(String one) {
			this.one = one;
		}
		/**
		 * @return
		 */
		public int getTwo() {
			return two;
		}
		/**
		 * @param two
		 */
		public void setTwo(int two) {
			this.two = two;
		}
		/**
		 * @return
		 */
		public String getThree() {
			return three;
		}
		/**
		 * @param three
		 */
		public void setThree(String three) {
			this.three = three;
		}
		
		
	}
}
