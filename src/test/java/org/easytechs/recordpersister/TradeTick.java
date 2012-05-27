package org.easytechs.recordpersister;

public class TradeTick {
	private String symbol;
	private String value;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "TradeTick [symbol=" + symbol + ", value=" + value + "]";
	}
}
