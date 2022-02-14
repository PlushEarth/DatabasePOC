package com.test.util;

public class SqlNameValuePair {

	private final String name;
	private final String value;
	
	public SqlNameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
