package com.gy.model;

import com.gy.enums.ErrorCodeEnum;

import java.io.Serializable;


/**
 * @描述: 公共返回信息模板
 * @作者: DuKai
 * @创建时间: 2018/7/30 15:56
 * @版本号: V3.0
 */

public class ReturnInfo implements Serializable {

	private static final long serialVersionUID = -3988381038549725335L;

	public ReturnInfo(){}

	public ReturnInfo(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public ReturnInfo(String code,String msg, Object data){
		this(code, msg);
		this.data = data;
	}

	public static ReturnInfo getInstance() {
		return new ReturnInfo();
	}

	//代码
	private String code;
	//信息
	private String msg;
	//数据
	private Object data;

	public ReturnInfo setResult(ErrorCodeEnum errorCodeEnum) {
		this.code = errorCodeEnum.getCode();
		this.msg = errorCodeEnum.getDesc();
		return this;
	}

	public String getCode() {
		return code;
	}

	public ReturnInfo setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ReturnInfo setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ReturnInfo setData(Object data) {
		this.data = data;
		return this;
	}

}
