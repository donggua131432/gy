package com.gy.exception;

import com.gy.enums.ErrorCodeEnum;

public class SystemException extends IRuntimeException {
	/**
	 * 
	 */
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
