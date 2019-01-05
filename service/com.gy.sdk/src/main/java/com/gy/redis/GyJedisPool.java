package com.gy.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

/**
 * 
 * @author Administrator
 *
 */
public class GyJedisPool extends JedisPool {

	private String name; //连接池的名称 

	private boolean isdefault; // 是否默认 
	
	private String host; // 主机地址
	
	private int port ; //端口号 

	public GyJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port, int timeout,
					   final String password) {
		super(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE, null);
		this.host = host ;
		this.port = port;
	} 
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefault() {
		return isdefault;
	}

	public void setDefault(boolean isdefault) {
		this.isdefault = isdefault;
	}
}