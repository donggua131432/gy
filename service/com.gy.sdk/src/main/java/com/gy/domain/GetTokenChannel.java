package com.gy.domain;

import java.io.Serializable;

/**
 * @描述: 获取Token的订阅频道
 * @作者: DuKai
 * @创建时间: 2018/11/20 14:18
 * @版本号: V1.0
 */
public class GetTokenChannel implements Serializable {

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
