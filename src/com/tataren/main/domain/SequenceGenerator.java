package com.tataren.main.domain;

public class SequenceGenerator extends Entity {
	String key;

	String helpKey;

	String value;

	String helpValue;

	public int getValueAsInt() {
		return Integer.parseInt(value);
	}

	public long getValueAslong() {
		return Long.parseLong(value);
	}

	public int getHelpValueAsInt() {
		return Integer.parseInt(helpValue);
	}

	public void setValue(int v) {
		this.value = String.valueOf(v);
	}

	public void setValue(long v) {
		this.value = String.valueOf(v);
	}

	public String getHelpKey() {
		return helpKey;
	}

	public void setHelpKey(String helpKey) {
		this.helpKey = helpKey;
	}

	public String getHelpValue() {
		return helpValue;
	}

	public void setHelpValue(String helpValue) {
		this.helpValue = helpValue;
	}

	public void setHelpValue(int helpValue) {
		this.helpValue = String.valueOf(helpValue);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
