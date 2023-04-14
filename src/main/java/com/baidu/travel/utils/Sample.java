package com.baidu.travel.utils;


import com.baidu.travel.speech.AipSpeech;
import org.json.JSONException;
import org.json.JSONObject;

public class Sample {
    public static final String APP_ID = "24543973";
    public static final String API_KEY = "NbAhW7nwAdGNDkCT1t26W0bI";
    public static final String SECRET_KEY = "qGQVYew8FBffpkbwP8ekgGmqEPxGZCgc";

    public static void main(String[] args) throws JSONException {
//        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//      //  client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//       // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
//
//        // 调用接口
//        JSONObject res = client.asr("C:\\Users\\pfk\\Desktop\\test.wav", "wav", 16000, null);
//        System.out.println(res.toString(2));

        long startTime = System.currentTimeMillis(); //获取开始时间
        // 文件读取方式,aipSpeech.asr()方法为多态方法，实际上都是调用的二进制方法,可自行查看api
        JSONObject asrRes = client.asr("C:\\Users\\pfk\\Desktop\\test.wav", "pcm", 16000, null);
        System.out.println(asrRes);
        long endTime = System.currentTimeMillis(); //获取结束时间

        System.out.println(asrRes.get("result").toString() + "程序运行时间：" + (endTime - startTime) + "ms");

    }
        public static String getR(byte[] bytes) throws JSONException {
            AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
            //  client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
            // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

            // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
            // 也可以直接通过jvm启动参数设置此环境变量
            System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

            // 调用接口
            JSONObject res = client.asr(bytes, "wav", 16000, null);
        return res.toString(2);
        }
}
