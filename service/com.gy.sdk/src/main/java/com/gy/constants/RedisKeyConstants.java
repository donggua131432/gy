package com.gy.constants;

/**
 * @描述: Redis相关常量
 * @作者: DuKai
 * @创建时间: 2018/7/26 16:36
 * @版本号: V3.0
 */
public interface RedisKeyConstants {

	int REDIS_DB_INDEX_0 = 0; //redis db 索引
	int REDIS_DB_INDEX_1 = 1; //redis db 索引
	int REDIS_DB_INDEX_2 = 2; //redis db 索引
	int REDIS_DB_INDEX_3 = 3; //redis db 索引

	/** 手机验证码缓存时间 */
	int SMSCODE_TTL = 45; //45秒
	/** 手机验证码缓存文件夹 */
	String SMSCODE_CACHE = "phone_code";
	String SMSCODE_CACHE_BIZ = "phone_code_biz";

	String SHIRO_SESSION="shiro:session:";
	String SHIRO_BIZ="shiro:biz:";
	String SHIRO_WEB="shiro:web:";

}
