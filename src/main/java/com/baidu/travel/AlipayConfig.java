package com.baidu.travel;

/**
 * @author pfk
 * @creatTime 2021/07/14下午 02:50
 * @describe
 */

public class AlipayConfig {


    /**
     * 作为身份标识的应用ID
      */
    public static String app_id = "2021000117688152";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
      */
    public static String merchant_private_key  = "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";

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
      */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnfQCYhs1IN72yseK0jVaKlwDgv3mbXT4Q/ov6cyatGKupxua3QTsG8fAgK2A308EMr5NE+3nderYgNPDizVudlgpQeoosos/EfnJxAekMY/2vQDiPKsbRREcE+jj7IMREZn3T2FMPBnapY72YVErJ4u83BBF8fnnJn7dpX8TAVsO52OllXCoxxjxIOEUXZdHVBMeBd7bgY2Pev/SfRVZ6bIe2z07sbW7nrYtedgNiE1agrmrr9ru5Y//7k6rtk2FCAxXYRanUn8R8wCPosBMb7o9Fgi9XeRtdhUTsoAr3fd8JgiTsFM4vXP/RUIViPwKk9NpNFLz23ZRivvtDqQWYQIDAQAB";

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
      */
    public static String notify_url = "http://www.baidu.com";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
      */
    public static String return_url = "http://www.baidu.com";

    /**
     *  签名方式
      */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
      */
    public static String charset = "utf-8";

    /**
     * 支付宝网关
      */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}