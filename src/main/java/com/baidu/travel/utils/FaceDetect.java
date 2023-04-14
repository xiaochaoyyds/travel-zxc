package com.baidu.travel.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class FaceDetect {
    /**
     * 将人脸数据发送到人脸库
     * @param s
     * @return
     */
    public static String faceDetect(String s) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", s);
            map.put("face_field", "faceshape,facetype");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result =HttpUtil.post(url, accessToken, "application/json", param);

            String s1 = checkOneFace(result);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测是不是人脸：
     *  1：是人脸
     *  0：不是人脸
     * @param result
     * @return
     */
    public static String checkOneFace(String result){

        try {
            //把返回结果转为hashmap
            HashMap baiduMap = JSON.parseObject(result, HashMap.class);
            //如果没有result说明没有检测到人
            if (baiduMap.get("result") == null) {
                return "0";
            } else {
                // 3. 获取到result
                HashMap resultMap = JSON.parseObject(String.valueOf(baiduMap.get("result")), HashMap.class);

                // 4. 从result中获取score  BigDecimal 类,用于高精度计算
                Integer face_num =(Integer) resultMap.get("face_num");

                // 5. 定义对比分值界限为85,如果score大于界限值lineScore则认为是同一人,否则不是同一人

                // 6. BigDecimal提供的比较方法 ,原理就是 score - lineScore
               // int flag = score.compareTo(lineScore);
                if (face_num > 0) {
                    return "1";//1代表是个人
                } else {
                    return "0";//0代表不是人
                }
            }
        }catch (Exception e){

        }

        return "0";
    }

}
