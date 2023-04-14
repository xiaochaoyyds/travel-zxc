package com.baidu.travel.service.impl;

import com.baidu.travel.domain.Weather;
import com.baidu.travel.service.WeatherService;
import com.baidu.travel.utils.JedisUtil;
import com.baidu.travel.utils.WeatherUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:48
 * @describe
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    @Override
    public Weather getWeatherByCite(String city) {
        //从redis里面获取天气对象
        Weather redisObject = (Weather) JedisUtil.getRedisObject(city);
        if(redisObject==null){
            System.out.println("调用接口查询天气");
            //redis里面没有此城市天气,需要调用接口查询
            Weather weather = WeatherUtil.getWeather(city);
            System.out.println("天气："+weather);
            //把天气存入redis
            JedisUtil.addRedisObject(city,weather);
            Jedis jedis = JedisUtil.getJedis();
            jedis.expire(city,1800);
            return weather;
        }
        System.out.println("从redis里面查询天气");
        return redisObject;
    }
}
