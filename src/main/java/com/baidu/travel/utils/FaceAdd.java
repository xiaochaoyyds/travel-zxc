package com.baidu.travel.utils;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 将人脸发送到人脸库
 */
public class FaceAdd {
    public static String add(String s,String user) throws JSONException {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        JSONObject jsonObject = new JSONObject(user);
        String username =(String) jsonObject.get("username");
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", s);
            map.put("group_id", "kk");
            map.put("user_id", username);
            map.put("user_info", user);
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            String success = isSuccess(result);
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String isSuccess(String result){
        HashMap hashMap = JSON.parseObject(result, HashMap.class);
        Object error_msg = hashMap.get("error_msg");
        if ("success".equalsIgnoreCase((String) error_msg)){
            return "1";
        }else {
            return "0";
        }

    }

}
