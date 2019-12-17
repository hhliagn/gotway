package com.gotway.gotway.common.util.redis;

public interface JedisClient {

	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, long seconds);
	Boolean delete(String key);

}
