package com.yzx.source.redis;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * 实现类，实现对redis的读取操作
 */
public class RedisContainer implements RedisCommandsContainer,Cloneable{

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(RedisContainer.class);
    private final JedisPool jedisPool;
    private final JedisSentinelPool jedisSentinelPool;

    public RedisContainer(JedisPool jedisPool) {
        Preconditions.checkNotNull(jedisPool, "Jedis Pool can not be null");
        this.jedisPool = jedisPool;
        this.jedisSentinelPool = null;
    }

    public RedisContainer(JedisSentinelPool sentinelPool) {
        Preconditions.checkNotNull(sentinelPool, "Jedis Sentinel Pool can not be null");
        this.jedisPool = null;
        this.jedisSentinelPool = sentinelPool;
    }

    /**
     * 获取存储在哈希表中指定字段的值。
     * @param key
     * @return
     */
    @Override
    public Map<String,String> hget(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            Map<String,String> map = new HashMap<String,String>();
            Set<String> fieldSet = jedis.hkeys(key);
            for(String s : fieldSet){
                map.put(s,jedis.hget(key,s));
            }
            return  map;
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    /**
     * 获取列表所有值
     * @param key
     * @return
     */
    @Override
    public List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            return jedis.lrange( key, 0, -1 );
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    /**
     * 获取指定 key 的值。
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            return jedis.get(key);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    /**
     * 以列表形式返回哈希表的字段及字段值。
     * @param key
     * @return
     */
    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    /**
     * 集合中的所有成员
     * @param key
     * @return
     */
    @Override
    public List<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            Set<String> smembers = jedis.smembers(key);
            List<String> list = new ArrayList<>(smembers);
            return list;
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    /**
     * 指定区间内，带有分数值(可选)的有序集成员的列表
     * @param key
     * @return
     */
    @Override
    public List<String> zrange(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            Set<String> zrange = jedis.zrange(key, 0, -1);
            List<String> list = new ArrayList<>(zrange);
            return list;
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    @Override
    public List<Tuple2> zrangeWithScores(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            Set<Tuple> tuples = jedis.zrangeWithScores(key, 0, -1);
            List<Tuple2> list = new ArrayList<>();
            for(Tuple t:tuples){
                Tuple2 tuple2 = new Tuple2(t.getElement(),t.getScore());
                list.add(tuple2);
            }
            return list;
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    /**
     * 返回给定 HyperLogLog 的基数估算值
     * @param key
     * @return
     */
    @Override
    public long pfcount(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getInstance();
            return jedis.pfcount(key);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot get Redis message with command HGET to key {} error message {}", new Object[]{key, e.getMessage()});
            }
            throw e;
        } finally {
            this.releaseInstance(jedis);
        }
    }

    private Jedis getInstance() {
        return this.jedisSentinelPool != null ? this.jedisSentinelPool.getResource() : this.jedisPool.getResource();
    }

    private void releaseInstance(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception var3) {
                LOG.error("Failed to close (return) instance to pool", var3);
            }

        }
    }

    public void close() {
        if (this.jedisPool != null) {
            this.jedisPool.close();
        }

        if (this.jedisSentinelPool != null) {
            this.jedisSentinelPool.close();
        }

    }

    @Override
    public RedisContainer clone() {
        try {
            RedisContainer clone = (RedisContainer) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}