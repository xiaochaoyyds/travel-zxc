package com.baidu.travel.utils;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jedis工具类
 */
public final class JedisUtil {

    private static JedisPool jedisPool;
    static {
        //读取配置文件
        InputStream is = JedisPool.class.getClassLoader().getResourceAsStream("application.properties");
        //创建Properties对象
        Properties pro = new Properties();
        //关联文件
        try {
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        //初始化JedisPool
        jedisPool = new JedisPool(config, pro.getProperty("host"), Integer.parseInt(pro.getProperty("port")),3000,"adminroot");


    }


    /**
     * 获取连接方法
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 关闭Jedis
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 把对象存入redis里面
     * @param str
     * @param obj
     */
    public static void addRedisObject(String str,Object obj){
        //获取连接
        Jedis jedis = getJedis();
        //把对象序列化为数组存入redis里面

        jedis.set(str.getBytes(), SerializeUtil.serialize(obj));
        jedis.expire(str.getBytes(),1800);
        //释放资源
        jedis.close();
    }

    /**
     * 从redis里面获取对对象
     * @param str
     * @return
     */
    public static Object getRedisObject(String str){
        Jedis jedis = getJedis();
        //获取存入redis里面的对象数组
        byte[] bytes = jedis.get(str.getBytes());
        //把数组反序列化为对象
        Object result = SerializeUtil.unserialize(bytes);
        jedis.close();
        return result;
    }

    public static void removeRedisObject(String str){
        Jedis jedis = getJedis();
        jedis.del(str.getBytes());
        jedis.close();
    }
}
