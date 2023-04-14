package com.baidu.travel.service.impl;

import com.baidu.travel.TravelApplication;
import com.baidu.travel.service.SpeechService;
import com.baidu.travel.utils.CityUtil;
import com.baidu.travel.utils.JedisUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.apache.ibatis.io.Resources.getResourceAsStream;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 05:15
 * @describe
 */
@Service
public class SpeechServiceImpl implements SpeechService {
    @Override
    public String find(String result, HttpServletRequest request) throws IOException {

        Jedis jedis = JedisUtil.getJedis();
        List<String> allCity = jedis.lrange("allCity", 0, -1);
        //如果redis没有找到‘所有城市’，去city.txt里查询
        if (allCity==null||allCity.size()==0){

            InputStream in = TravelApplication.class.getClassLoader().getResourceAsStream("static/city.txt");
            Reader reader=new InputStreamReader(in);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String a;
            while ((a=bufferedReader.readLine())!=null){
                result+=a;
            }
            String[] split = result.split("、");
            List<String> list=new ArrayList<>(999);
            for (String s1:split){
                list.add(s1);

                jedis.lpush("allCity",s1);
            }
            allCity=list;
            String r = CityUtil.getR(result, allCity);
            return r;
        }else {
            String r= CityUtil.getR(result,allCity);
            return r;
        }

    }
}
