package com.gotway.gotway.redisTest;

import org.junit.Test;
import redis.clients.jedis.JedisPool;

public class RedisTest {

    @Test
    public void test01(){
        JedisPool jedisPool = new JedisPool();

       // JedisClientPool jedisClientPool = new JedisClientPool(jedisPool);
        //jedisClientPool.set("aa","aa1");
      // System.out.println(jedisClientPool.get("aa"));
    }
}
