package com.syc.redis;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class JedisUtils {

    private static final Logger logger = LogManager.getLogger(JedisUtils.class);

    private static JedisPool jedisPool = new JedisPool("localhost");

    private JedisUtils() {

    }

    /**
     * 务必使用过之后要jedis.close()关闭连接
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 向key指向的set集合中插入若干条数据
     */
    public static void sadd(String key, String... members) {
        //redis操作发生异常时要把异常捕获，不要响应正常的业务逻辑
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.sadd(key, members);
            logger.debug("sadd：向缓存中添加数据，key：{} value：{}", key, members);
        } catch (Exception e) {
            logger.error("sadd：向缓存中添加数据时出现异常，key：{} value：{}", key, members);
        } finally {
            //保证jedis被关闭（归还给连接池）
            closeQuietly(jedis);
        }
    }

    /**
     * 从key指向的set集合中取出所有数据并删除此key指向的set集合
     */
    @SuppressWarnings("unchecked")
    public static Set<String> smembersAndDel(String key) {
        Jedis jedis = null;
        Set<String> set = null;
        try {
            jedis = getJedis();
            Transaction tx = jedis.multi();
            tx.smembers(key);
            tx.del(key);

            List<Object> result = tx.exec();
            if (result == null || result.isEmpty()) {
                logger.error("smembersAndDel：从缓存中取出数据以及删除数据的事务失败");
            } else {
                set = (Set<String>) result.get(0);
                logger.debug("smembersAndDel：从缓存中取出数据同时删除了key，key：{}，value：{}", key, set);
            }

        } catch (Exception e) {
            logger.error("smembersAndDel：从缓存中取出数据或者删除数据是出现异常", e);
        } finally {
            closeQuietly(jedis);
        }
        return set;
    }

    /**
     * 添加一对key:value，并设置过期时间
     */
    public static void setex(String key, int expire, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(key, expire, value);
            logger.debug("setex：向缓存中添加数据，key：{}，value：{}，过期时间：{}秒", key, value, expire);
        } catch (Exception e) {
            logger.error("setex：向缓存中添加数据时出现异常，key：{}，value：{}，过期时间：{}秒", key, value, expire, e);
        } finally {
            //保证jedis被关闭（归还给连接池）
            closeQuietly(jedis);
        }
    }

    /**
     * 获得key指向的value
     */
    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
            logger.debug("get：从缓存中获取数据，key：{}，value：{}", key, value);
        } catch (Exception e) {
            logger.error("get：从缓存中获取数据时出现异常，key：{}，value：{}", key, value, e);
        } finally {
            //保证jedis被关闭（归还给连接池）
            closeQuietly(jedis);
        }
        return value;
    }

    /**
     * 设置值，如果key存在就覆盖原有的值
     */
    public static void set(String key, String value){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            logger.debug("set：向缓存中添加数据，key：{}，value：{}", key, value);
        } catch (Exception e) {
            logger.error("set：向缓存中添加数据时出现异常，key：{}，value：{}", key, value, e);
        } finally {
            //保证jedis被关闭（归还给连接池）
            closeQuietly(jedis);
        }
    }

    /**
     * 查看某个key的剩余生存时间,单位【秒】.
     */
    public static Long ttl(String key){
        Jedis jedis = getJedis();
        Long expire = jedis.ttl(key);
        closeQuietly(jedis);
        return expire;
    }

    /**
     * 根据key，判断缓存中是否存在对应的value
     */
    public static boolean isExist(String key){
        Jedis jedis = null;
        Boolean exist = null;
        try {
            jedis = getJedis();
            exist = jedis.exists(key);
            if(exist){
                logger.debug("根据此key:{}，可以在缓存中找到对应的value",key);
                return true;
            } else {
                logger.debug("根据此key:{}，并没有在缓存中找到对应的value",key);
                return false;
            }
        } catch (Exception e) {
            logger.error("get：从缓存中获取数据时出现异常，key：{}", key,e);
            throw new RuntimeException(e);
        } finally {
            //保证jedis被关闭（归还给连接池）
            closeQuietly(jedis);
        }
    }

    /**
     * 根据key的前缀删除所有相关的key
     */
    public static void del(String keyPattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> keys = jedis.keys(keyPattern);
            logger.debug("要删除的key为：{}", keys);

            if (keys != null && keys.size() > 0) {
                String[] arr = new String[keys.size()];
                jedis.del(keys.toArray(arr));
            }
            logger.debug("del：从缓存中删除数据，keyPattern：{}", keyPattern);
        } catch (Exception e) {
            logger.error("del：从缓存中删除数据时出现异常，keyPattern：{}", keyPattern);
        } finally {
            //保证jedis被关闭（归还给连接池）
            closeQuietly(jedis);
        }
    }

    public static void closeQuietly(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("关闭jedis连接时发生异常", e);
            }
        }
    }
}
