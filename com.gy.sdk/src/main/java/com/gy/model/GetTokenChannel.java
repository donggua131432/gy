package com.gy.model;


/**
 * @desc 获取Token的订阅频道
 * @author Administrator
 *
 */
public class GetTokenChannel implements java.io.Serializable {

	private Long id; // Id

	private int tokenType; // tokenType 0 wechat 1 jd

	private Object param; // 参数

	public GetTokenChannel() {
		this.id = System.nanoTime();
	}

	public Long getId() {
		return id;
	}

	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}

	public int getTokenType() {
		return tokenType;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public Object getParam() {
		return param;
	}

}
