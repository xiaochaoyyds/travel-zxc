package com.baidu.travel.utils;

import com.alibaba.fastjson.JSON;


import com.baidu.travel.domain.Weather;

import com.google.gson.JsonObject;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.WatchEvent;
import java.util.*;

public class WeatherUtil {
    public static Weather getWeather1(String s){
        String host = "https://ali-weather.showapi.com";
        String path = "";
        String method = "GET";
        String appcode = "83359fd73fe94948385f570e3c139105";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("area", s);
        querys.put("areaid", "0");
        querys.put("need3HourForcast", "0");
        querys.put("needAlarm", "0");
        querys.put("needHourData", "0");
        querys.put("needIndex", "0");
        querys.put("needMoreDay", "1");

        Weather weather=new Weather();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            //获取response的body
            String s1 = EntityUtils.toString(response.getEntity());

            JSONObject jsonObject = new JSONObject(s1);
            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
            JSONObject now = showapi_res_body.getJSONObject("now");
            //System.out.println(s1);
            List<String> arr = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                arr.add((String)showapi_res_body.getJSONObject("f"+i).get("night_weather_pic"));
            }
//            weather.setFutureWeather(arr);
            JSONObject aqiDetail = now.getJSONObject("aqiDetail");

            String wind_direction = (String) now.get("wind_direction");
            String area =(String) aqiDetail.get("area");
            String quality = (String)aqiDetail.get("quality");
            String sd = (String)now.get("sd");
            String weather_pic = (String)now.get("weather_pic");
            String weathers = (String)now.get("weather");
            String temperature = (String)now.get("temperature");

//            weather.setArea(area);
//            weather.setQuality(quality);
//            weather.setSd(sd);
//            weather.setTemperature(temperature);
//            weather.setWeather(weathers);
//            weather.setWeather_pic(weather_pic);
//            weather.setWind_direction(wind_direction);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }


    public static Weather getWeather(String city){
        String host = "https://jisutqybmf.market.alicloudapi.com";
        String path = "/weather/query";
        String method = "GET";//GET/POST 任意
        String appcode = "c11e878aeae648e3abe4f995b145aede";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", city);
//        querys.put("citycode", "citycode");
//        querys.put("cityid", "cityid");
//        querys.put("ip", "ip");
//        querys.put("location", "location");

        Weather weather = new Weather();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

//            System.out.println(response.toString());
            //获取response的body
            String s = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("源数据："+s);
            JSONObject resultJson = new JSONObject(s);
            JSONObject result = resultJson.getJSONObject("result");

            String city1 = (result.getString("city"));


            String humidity = (result.getString("humidity"));
            String temp = (result.getString("temp"));
            String winddirect = (result.getString("winddirect"));
            String aqi = (result.getJSONObject("aqi").getString("aqi"));
//            String weather1 = (result.getString("weather"));


            JSONArray daily = result.getJSONArray("daily");
            //            String img = (result.getString("img"));

            //获取当天天气图标
            String img = ((JSONObject)daily.get(0)).getJSONObject("day").getString("img");
            //获取当天天气状况
            String weather1 = ((JSONObject)daily.get(0)).getJSONObject("day").getString("weather");

            //天气图标收集
            LinkedList<String> list = new LinkedList<>();
            for (int i = 0; i < daily.length(); i++) {
                list.add(((JSONObject)daily.get(i)).getJSONObject("day").getString("img"));
            }


            weather.setCity(city1);
            weather.setImg(img);
            weather.setHumidity(humidity);
            weather.setTemp(temp);
            weather.setWinddirect(winddirect);
            weather.setAqi(aqi);
            weather.setWeather(weather1);
            weather.setFutureWeather(list);

            System.out.println("城市为："+city1);
            System.out.println("城市图片为："+img);
            System.out.println("湿度为："+humidity);
            System.out.println("气温为："+temp);
            System.out.println("风向为："+winddirect);
            System.out.println("空气质量为："+aqi);
            System.out.println("天气状况为："+weather1);
            System.out.println("未来天气为："+list);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }

    public static void main(String[] args) {

        Weather city =  getWeather("成都");

        System.out.println("城市："+city);
    }
}
