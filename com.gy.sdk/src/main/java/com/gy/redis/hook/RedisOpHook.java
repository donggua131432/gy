package com.gy.redis.hook;

import redis.clients.jedis.Jedis;

/**
 * @desc Redis 操作 hook，提供Redis后操作
 * @author Administrator
 *
 */
public interface RedisOpHook<T> {

	/**
	 * @desc
	 * @param redis
	 *            当前Redis连接
	 * @param param
	 *            参数
	 * @return
	 */
	T execute(Jedis redis, Object param);

	/**
	 * 
	 * @param times
	 */
	default void sleep(int times) {
		try {
			Thread.sleep(times);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
