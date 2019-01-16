package com.gy.exception;

import com.gy.enums.ErrorCodeEnum;

import java.io.Serializable;

/**
 * @描述: 文件上传异常
 * @作者: DuKai
 * @创建时间: 2018/12/14 15:48
 * @版本号: V1.0
 */
public class UploadException extends IRuntimeException implements Serializable {
	private static final long serialVersionUID = -3101978542423406932L;

	private static final String NAME = "文件上传异常";

	public UploadException(String errMessage, Throwable e) {
		super(NAME, ErrorCodeEnum.UPLOAD_ERROR.getCode(), errMessage, e);
	}

	public UploadException(String errMessage) {
		super(NAME, ErrorCodeEnum.UPLOAD_ERROR.getCode(), errMessage, null);
	}

	public UploadException() {
		super(NAME, ErrorCodeEnum.UPLOAD_ERROR.getCode());
	}
}
