
package com.gy.enums;


/**
 * @描述: 错误码
 * @作者: DuKai
 * @创建时间: 2018/8/1 13:41
 * @版本号: V3.0
 */
public enum ErrorCodeEnum {

	SUCCESS("E000000","请求成功"),
	FAILED("E000001","未知错误"),
	SYSYTEM_ERROR("E000002","系统错误"),
	DB_ERROR("E000003", "数据操作错误"),
	CACHE_ERROR("E000004", "缓存操作错误"),
	AUTH_ERROR("E000005", "权限不足"),
	INTERFACE_NOT_FOUND("E000006","接口不存在"),



	;


	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	private ErrorCodeEnum(String code, String desc){
		this.code=code;
		this.desc=desc;
	}

	public static void main(String[] args){
		System.out.println(ErrorCodeEnum.SUCCESS.getCode());
		System.out.println(ErrorCodeEnum.SUCCESS.getDesc());
	}
}
