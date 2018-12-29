package com.gy.redis;

/**
 * 
 * @author Administrator
 *
 */
public class GyRedisInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private boolean isdefault;
	
	/**
	 * 主机地址 - redis.pool.host - String
	 */
	private String host;
	/**
	 * 端口号 - redis.pool.port - int
	 */
	private int port;
	/**
	 * redis登入秘钥
	 */
	private String authKey;

	/**
	 * redis主机名称
	 */
	private String masterName;
 
	/**
	 * 连接类型
	 */
	private String connectType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefault() {
		return  isdefault;
	}

	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
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

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	} 
}
