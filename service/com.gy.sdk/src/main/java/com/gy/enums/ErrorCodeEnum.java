
package com.gy.enums;


/**
 * @描述: 错误码
 * @作者: DuKai
 * @创建时间: 2018/8/1 13:41
 * @版本号: V1.0
 */
public enum ErrorCodeEnum {

	SUCCESS("00","请求成功"),
	FAILED("E000001","未知错误"),
	SYSYTEM_ERROR("E000002","系统错误"),
	DB_ERROR("E000003", "数据操作错误"),
	CACHE_ERROR("E000004", "缓存操作错误"),
	AUTH_ERROR("E000005", "权限不足"),
	INTERFACE_NOT_FOUND("E000006","接口不存在"),
	ISSUE_TOKEN_FAIL("E000007","签发动态秘钥失败"),
	LOGIN_NO("E000008","未登录"),
	AUTH_NO("E000009","未授权"),
	LOGIN_FAILED("E000010","登录失败"),
	REQUEST_INVALID("E000011","请求无效"),
	JWT_EXPIRE("E000012","JWT过期"),
	JWT_INVALID("E000013","JWT无效"),
	JWT_REQUEST_INVALID("E000014","请求未携带JWT"),
	UPLOAD_ERROR("E000015","文件上传失败"),
	CUSTOM_ERROR("E000016","自定义异常"),
	PARAM_FAILED("E000017","参数错误"),
	NOT_EXIST("E000018", "数据不存在"),
	DUPLICATE_KEY("E000019", "数据重复"),
	UPDATE_FAILED("E000020", "数据更新失败"),
	INSERT_FAILED("E000021", "重复数据，添加失败"),
	PARAM_VALUE_FAILED("E000022","参数值错误"),
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
