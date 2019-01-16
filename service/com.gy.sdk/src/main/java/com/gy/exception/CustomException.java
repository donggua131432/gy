package com.gy.exception;

import com.gy.enums.ErrorCodeEnum;
import java.io.Serializable;

public class CustomException extends IRuntimeException implements Serializable {
	private static final long serialVersionUID = -3782479028806993497L;

	private static final String NAME = "自定义异常";

	
	public CustomException(String message, Throwable e) {
		super(NAME, ErrorCodeEnum.CUSTOM_ERROR.getCode(), message, e);
	}
	
	public CustomException(String message) {
		this(message, null);
	}

}
