package cn.xl.examination.common.lang;

import redis.clients.jedis.Jedis;


public class Config {
    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);
    public static Jedis getJedis() {
        return jedis;
    }

    public static void close() {
        jedis.close();
    }
}
