package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author pfk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component

public class Weather implements Serializable {
//    /**
//     * 城市
//     */
//    private String area;
//    /**
//     * 空气质量
//     */
//    private String quality;
//    /**
//     * 气温
//     */
//    private String temperature;
//    /**
//     * 湿度
//     */
//    private String sd;
//    /**
//     * 天气
//     */
//    private String weather;
//    /**
//     * 天气图片
//     */
//    private String weather_pic;
//    /**
//     * 未来几天的数据，应该为天气图标
//     */
//    private List<String> futureWeather;
//    /**
//     * 风向
//     */
//    private String wind_direction;

    /**
     * 城市:
     */
    private String city;
    /**
     * 天气图片
     */
    private String img;
    /**
     * 湿度
     */
    private String humidity;
    /**
     * 温度
     */
    private String temp;
    /**
     * 风向
     */
    private String winddirect;

    /**
     * 空气质量指数
     */
    private String aqi;
    /**
     * 天气状况
     */
    private String weather;


    /**
     * 未来七天天气图标
     */
    private List<String> futureWeather;


}
