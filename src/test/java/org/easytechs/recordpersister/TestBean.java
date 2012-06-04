package org.easytechs.recordpersister;

public class TestBean {
	/**
	 */
	private String symbol;
	/**
	 */
	private String value;
	/**
	 */
	private long date;
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
	public String getValue() {
		return value;
	}
	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	/**
	 * @return
	 */
	public long getDate() {
		return date;
	}
	/**
	 * @param date
	 */
	public void setDate(long date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "TestBean [symbol=" + symbol + ", value=" + value + ", date="
				+ date + "]";
	}

}
