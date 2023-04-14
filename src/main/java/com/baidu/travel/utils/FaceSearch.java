package com.baidu.travel.utils;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FaceSearch {
    /**
     * 将人脸信息发送到人脸库进行对比
     * @param s
     * @return
     */
    public static String[] faceSearch(String s) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", s);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "kk");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            //
            String[] s1 = checkIsOnePerson(result);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String[] checkIsOnePerson(String result) throws JSONException {

            // 1. 将百度接口返回的result 转化成HashMap
            HashMap baiduMap = JSON.parseObject(result, HashMap.class);
        /**
         *  如果从baiduMap中获取不到result则代表失败不是同一人,返回 0；反之返回 1。
         *      strings[0]:代表是不是人脸
         *      strings[1]:人脸信息
         */
            String[] strings=new String[2];
        if (baiduMap.get("result") == null) {
            strings[0]="0";
            return strings;
        } else {
       // 3. 获取到result
       // HashMap resultMap = JSON.parseObject(String.valueOf(baiduMap.get("result")), HashMap.class);
      // 4. 从result中获取score  BigDecimal 类,用于高精度计算
            JSONObject jsonObject = new JSONObject(result);
            JSONObject result1 = (JSONObject) jsonObject.getJSONObject("result").getJSONArray("user_list").get(0);//
            Double score = (Double) result1.get("score");
            String user_info = (String) result1.get("user_info");
            // 5. 定义对比分值界限为85,如果score大于界限值lineScore则认为是同一人,否则不是同一人
               // BigDecimal lineScore = new BigDecimal(85);
                // 6. BigDecimal提供的比较方法 ,原理就是 score - lineScore
            int i = score.intValue();

            System.out.println("人脸匹配值:"+i+"%");

            if ( i> 85) {
                strings[0]="1";
                strings[1]=user_info;
                //1代表是同一个人
                return strings;
            } else {
                    strings[0]="0";
                    //0代表不是同一人
                    return strings;
                }
        }

    }

}
