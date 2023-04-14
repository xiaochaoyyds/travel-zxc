package com.baidu.travel.service;

import com.baidu.travel.domain.Weather;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:47
 * @describe
 */
public interface WeatherService {
    Weather getWeatherByCite(String city);

}
