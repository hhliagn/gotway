package com.gotway.gotway.common.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JedisClientPool implements JedisClient {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public JedisClientPool() {

	}

	@Override
	public String set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
		return value;
	}

	@Override
	public String get(String key) {
		Object o = redisTemplate.opsForValue().get(key);
		if(o!=null) return o.toString();
		else return null;
	}

	@Override
	public Boolean exists(String key) {
		Object o = redisTemplate.opsForValue().get(key);
		if(o!=null)return true;
		else return false;
	}

	@Override
	public Long expire(String key, long seconds) {
		String value = redisTemplate.opsForValue().get(key);
		redisTemplate.opsForValue().set(key, value,seconds,TimeUnit.SECONDS);
		return seconds;
	}

	@Override
	public Boolean delete(String key) {
		redisTemplate.delete(key);
		return true;
	}


}
