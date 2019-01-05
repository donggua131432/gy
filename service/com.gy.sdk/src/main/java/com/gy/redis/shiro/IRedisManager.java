package com.gy.redis.shiro;

import java.util.Set;

/**
 * redisManager interface
 *
 **/

public interface IRedisManager {

    /**
     * get value from redis
     * @param key
     * @return
     */
    byte[] get(byte[] key);

    /**
     * set
     * @param key
     * @param value
     * @return
     */
    byte[] set(byte[] key, byte[] value, int expire);

    /**
     * del
     * @param key
     */
    void del(byte[] key);

    /**
     * size
     */
    Long dbSize(byte[] pattern);

    /**
     * keys
     * @param pattern
     * @return
     */
    Set<byte[]> keys(byte[] pattern);

}
