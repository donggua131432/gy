package com.gy.exception;

import com.gy.enums.ErrorCodeEnum;

import java.io.Serializable;


/**
 * @描述: 系统异常
 * @作者: DuKai
 * @创建时间: 2018/12/14 15:48
 * @版本号: V1.0
 */
public class SystemException extends IRuntimeException implements Serializable {
	private static final long serialVersionUID = 2024764604915305502L;
	
	private static final String NAME = "系统异常";

	public SystemException(String errMessage, Throwable e) {
		super(NAME, ErrorCodeEnum.SYSYTEM_ERROR.getCode(), errMessage, e);
	}
	
	public SystemException(String errMessage) {
		super(NAME, ErrorCodeEnum.SYSYTEM_ERROR.getCode(), errMessage, null);
	}
	
	public SystemException() {
		super(NAME, ErrorCodeEnum.SYSYTEM_ERROR.getCode());
	}

}
