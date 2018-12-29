package com.gy.datasource;

public class Global {
	private int reapTimeout;
	private int poolSize;
	private String defaultDatasource;
	private String user;
	private String password;
 
	
	private String driverClassName;
	private int minIdle;
	private int maxActive;
	private int maxWait;
	private int minEictableIdleTimeMillis;
	
	public int getReapTimeout() {
		return reapTimeout;
	}
	public void setReapTimeout(int reapTimeout) {
		this.reapTimeout = reapTimeout;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public String getDefaultDatasource() {
		return defaultDatasource;
	}
	public void setDefaultDatasource(String defaultDatasource) {
		this.defaultDatasource = defaultDatasource;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
 
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getMinEictableIdleTimeMillis() {
		return minEictableIdleTimeMillis;
	}
	public void setMinEictableIdleTimeMillis(int minEictableIdleTimeMillis) {
		this.minEictableIdleTimeMillis = minEictableIdleTimeMillis;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
}
