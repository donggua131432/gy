package com.gy.exception;

import com.gy.enums.ErrorCodeEnum;

public class IRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5410387705579835565L;


	private String errCode;
	private String errMessage;
	private String typeName;

	public IRuntimeException(String typeName, String errCode, String errMessage, Throwable e) {
		super(packageErrorMessage(typeName, errCode, errMessage, e), e);
		this.errCode = errCode;
		this.errMessage = errMessage;
		this.typeName = typeName;
	}

	public IRuntimeException(String typeName, String errCode, String errMessage) {
		this(typeName, errCode, errMessage, null);
	}

	public IRuntimeException(String typeName, ErrorCodeEnum eCode) {
		this(typeName, eCode.getCode());
	}

	public IRuntimeException(String typeName, String code) {
		this(typeName, code, null);
	}

	public String getErrMessage() {
		return errMessage;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	private static String packageErrorMessage(String typeName, String errCode, String errMessage, Throwable e) {
		StringBuffer sb = new StringBuffer(typeName);
		
		sb.append("-[").append(errCode).append("]");
		
		if(errMessage != null && errMessage.trim().length() > 0) {
			sb.append(" : {").append(errMessage).append("}");
		}
		
		if(e != null && e.getMessage() != null && e.getMessage().trim().length() > 0) {
			sb.append(" - {").append(e.getMessage()).append("}");
		}
		
		return sb.toString();
	}
}
