package org.easytechs.recordpersister;

public class TestBean2 {

	/**
	 */
	private String symbol;
	/**
	 */
	private long tradeTime;
	/**
	 */
	private String price;

	public TestBean2(){
		
	}
	
	public TestBean2(String symbol, long time, String price) {
		this.symbol=symbol;
		this.tradeTime=time;
		this.price=price;
	}

	/**
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return
	 */
	public long getTradeTime() {
		return tradeTime;
	}

	/**
	 * @param time
	 */
	public void setTradeTime(long time) {
		this.tradeTime = time;
	}

	/**
	 * @return
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	
}
