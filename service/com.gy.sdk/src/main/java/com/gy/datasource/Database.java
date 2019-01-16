package com.gy.datasource;

public enum Database {
	AC("ac"),
	LC("lc");

	private String name;
	
	private Database(String name) {
		this.name = name;
	}
	
	public String get() {
		return this.name;
	}
}
