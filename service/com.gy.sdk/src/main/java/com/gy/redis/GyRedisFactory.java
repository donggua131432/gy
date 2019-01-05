package com.gy.redis;

import com.gy.exception.SystemException;
import com.gy.util.StringUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @描述: Redis工厂类
 * @作者: DuKai
 * @创建时间: 2018/7/23 10:07
 * @版本号: V3.0
 */
public class GyRedisFactory {

	private static String DEFAULT_REDIS_POOL = "default_redis_pool";

	private static Logger logger = LoggerFactory.getLogger(GyRedisFactory.class);

	private static Map<String, GyJedisPool> jedisCacheMap = new HashMap<>();

	private static Map<String, RedisService> redisServiceCache = new ConcurrentHashMap<>();

	private GyRedisFactory() {

	}


	private static class MhsRedisFactoryHodler {
		private static GyRedisFactory instance = new GyRedisFactory();
	}


	public static GyRedisFactory getInstance() {
		return MhsRedisFactoryHodler.instance;
	}


	@Deprecated
	public RedisService getRedisService() {
		if (redisServiceCache.containsKey(DEFAULT_REDIS_POOL)) {
			return redisServiceCache.get(DEFAULT_REDIS_POOL);
		}
		GyJedisPool pool = getDefaultRedisPool();
		RedisService redisService = RedisService.newRedisService(pool);
		redisServiceCache.putIfAbsent(DEFAULT_REDIS_POOL, redisService);
		return redisService;
	}

	@Deprecated
	public RedisService getRedisService(String name) {
		if (redisServiceCache.containsKey(name)) {
			return redisServiceCache.get(name);
		}
		GyJedisPool pool = getRedisPool(name);
		RedisService redisService = RedisService.newRedisService(pool);
		redisServiceCache.putIfAbsent(name, redisService);
		return redisService;
	}

	/**
	 * 
	 * @param config
	 */
	public void init(GyRedisBean config) {
		List<GyRedisInfo> redisInfos = config.getRedisInfos();
		if (redisInfos != null && !redisInfos.isEmpty()) {
			JedisPoolConfig conf = new JedisPoolConfig();
			conf.setLifo(true);
			conf.setMaxIdle(config.getMaxIdle());
			conf.setMinIdle(config.getMinIdle());
			conf.setMaxTotal(config.getMaxActive());
			conf.setBlockWhenExhausted(false);
			conf.setTestOnBorrow(config.isTestOnBorrow());
			conf.setTestOnReturn(config.isTestOnReturn());
			conf.setTestOnCreate(true);
			conf.setMaxWaitMillis(config.getMaxWait());
			for (GyRedisInfo redisInfo : redisInfos) {
				initRedisPool(conf, redisInfo, config.getTimeout());
			}
		}
	}

	/**
	 * 
	 * @param config
	 * @param redisInfo
	 * @param timeOut
	 */
	private void initRedisPool(GenericObjectPoolConfig config, GyRedisInfo redisInfo, int timeOut) {
		GyJedisPool jedisPool = new GyJedisPool(config, redisInfo.getHost(), redisInfo.getPort(), timeOut,
				redisInfo.getAuthKey());
		jedisPool.setDefault(redisInfo.isDefault());
		jedisPool.setName(redisInfo.getName());
		String name = redisInfo.getName();
		logger.info("initRedisPool:{}", name);
		jedisCacheMap.put(name, jedisPool);
	}

	/**
	 * 
	 * @param name
	 */
	private GyJedisPool getRedisPool(String name) {
		for (GyJedisPool redisPool : jedisCacheMap.values()) {
			if (StringUtil.equals(name, redisPool.getName())) {
				return redisPool;
			}
		}
		return getDefaultRedisPool();
	}

	/**
	 * 
	 * @return
	 */
	private GyJedisPool getDefaultRedisPool() {
		for (GyJedisPool redisPool : jedisCacheMap.values()) {
			if (redisPool.isDefault()) {
				return redisPool;
			}
		}
		logger.error("not found defaultRedisPool");
		throw new SystemException("config error ,please check config !");
	}

	/**
	 * 
	 */
	@PreDestroy
	public static void destory() {
		for (GyJedisPool jedisPool : jedisCacheMap.values()) {
			if (jedisPool != null && !jedisPool.isClosed()) {
				jedisPool.close();
			}
		} 
	} 
}
