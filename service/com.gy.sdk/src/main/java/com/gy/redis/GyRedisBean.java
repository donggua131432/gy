package com.gy.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * 
 * @author Administrator
 *
 */
@ConfigurationProperties(prefix="redis.pool")
public class GyRedisBean {

	private List<GyRedisInfo> redisInfos;
	
	/**
	 * 默认dbindex
	 */
	private int defaultDbIndex;
	/**
	 * session超时时间
	 */
	private int sessionTimeout;
	
	private int minIdle;
	
	private int maxIdle;
	
	private int maxWait;
	
	private boolean testOnReturn;
	
	private boolean testOnBorrow;
	
	private int timeout;
	
	private int maxActive;
	
	
	public void setRedisInfos(List<GyRedisInfo> redisInfos) {
		this.redisInfos = redisInfos;
	}
	
	public List<GyRedisInfo> getRedisInfos() {
		return redisInfos;
	}
	 
	public int getDefaultDbIndex() {
		return defaultDbIndex;
	}

	public void setDefaultDbIndex(int defaultDbIndex) {
		this.defaultDbIndex = defaultDbIndex;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	} 
}
