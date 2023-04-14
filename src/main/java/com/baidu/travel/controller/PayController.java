package com.baidu.travel.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baidu.travel.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author pfk
 * 支付宝接口
 */
@Controller
public class PayController {
    private final String APP_ID = "2021000117688152";

    private final String APP_PRIVATE_KEY = "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCPbNNxztSQrUGfSGsjXmyuxDgoy7iolpfI4VkU4i3FZ+0cuAAlEQzrA3nSqXXoNIJKmD/FCS80zuMySgTAmEQ+UAuBkLaJ30TW/Ge8ICEwTKvldpHr/8gSf4ASqgALUGtZZjyhS4YMoC8kBPoQE92dAqnsI6cB0EYMJ0SLKLaWKlZZIB8fqwvkPThylOW7jCInzoeKr/RgtxuGrFuHRLjT8qiKCJLyggPODnT3RYUKGcCOTQrnnEo6GiV2C55P1Ks1oREB3Yls9DUDzTvUPvOduovZE4AKfQqSol04HtLjhSsjQ7gsVzMhsmjheVZdngtVeiGJk7rmhGwvuYpiqXTBAgMBAAECgf8pdUupmh/Xsj1FzakqkCDaY93X7QtzsBQv/PPDFZ4lceUYaBein0sco+STAGkUthjzdw735a+/lA4Ye0zkXx9a48hvq2UYdXFws4VI3AC79W265otHrKgosLJUss9Qde6R6PudpDcbbzIxNz8RZqyothxoJeYgoBOfb7pgf2RIA4YOrTwadUH4pACm4h1oMerKdhiYaCBIRi/2G96DXg8ik2JanYy6p2Hgb0vy9DAgKSBAnlhHdINVuXrpG2nafG1wwXZwPPLEV5kAisDe+o3BARYBgAwHYOQ7Q8IzLdV/mw/xu3U5wrRfBpG75d6qMyx40KrFpGlWyYl2snKrQqkCgYEAyGB3DPuyQFVfuFHXp1nzQR3F2RulZ1l/WXmsVMBtWzS+G4F833gL2ZS6EfwMnSQ9u0yiZK1RHKMOSfB67tavJ5y9fAAF8IzJ6G+wW4gjzEOqctzB5gONg6S5vUiLJzyOv8GR2dr5YrajoUWtgPeYokS0u4baCyQ1z9z8KtSX0fcCgYEAtz0oD3EkhOI+dLzPWQ1/ipoFnPDC3avjGy6R6PL+5kMQKPPKTfZMRBBy7Agxg0xv6Cbf7zr8VF7fhkwusxG11uDnYHGUh4+XvYy18CpwK7x0+dDHClvzQxbhvXa2JdlVWC6DgQ7hg9qxlIxXlS5twK/x4/q8gtnV9Wav116lQQcCgYBJoHB+O8nuq7AlWSVFn96rMZZuRSNMy2zo4rV42tAT8FheYQESV9j61RRd4WB9KUDpnqoZFjo0rJFmh8+UDRyFEKnrc1K7ZfAW0Vta9rY4qzBpTkaNDwcjtRZuv8Yp362QNj8ybqyuIfAUJTcwqUnTbi1+rnlWGLMRMuERl9ZFVwKBgDNexM1Xddp/Ju1JRYP8wcXUmkA+SxMkLh4fty4seYOKR4yDlDK5GvZ1+hHr1h2uHy1hQKRrz/PDSTz4PJtdGrJsCNHD5dT/T5YHTbZP33STd+PVQQfJcXBciNq53iTMQh3fpGRZDKYPeHthwOgExSYg7Vpxn8m7Uj1jFWb2lLOfAoGAHuM5fC4jlRc1DQ7Ue1YcxXOd3ovOcWRZbF+GKQageGuefrqArtK21hulwBSEgkf4+q+v9ZADcxyzgQNxni89mFdZVpMdZFrZTymXqoSfWgoAfihDPhKO9CsKAE8b3QD2IY1JTBy+n1KogLZI4hV5xIvT3Lqq4GTNe+LFBC7MubM=";


    private final String CHARSET = "UTF-8";

    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApmNwP6AVNWw9lPspZemNFbreYS+4GT5fuOCdC6peJ6u3uuxki2iXwRrw6KCOQXXNa0OTopxvaii6QTUobPOFGk9xSUcewxrwpj3S0WbkTU5bNINuVpTpOqvga6C4C7fOCd1o/7czx/HPGoHH38wJN6qj+zP+45GRqPieg2sMAG45bm78W64bZP0yE7X3InOKAcFDXoZjEuU+CmJFnrMYmTFFliqhxpI9gqlyHprxzdKT+sN9olKDNf5BCOPEeBvMQK8yi7US/cJn3zt6/yfJps0lqVpTx/wDWRci16bjyyO5j59BlI6507VKoSLlK7tbt4lfxelAVNWJu6qTFQmXlQIDAQAB";
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的s页面,可以不是公网地址
    private final String RETURN_URL = "http://localhost:80/money/returnPly";




    @Autowired
    private PayService payService;
    /**
     * 支付方法
     * @param httpResponse
     * @throws IOException
     */
    public String alipay(HttpServletRequest httpRequest,HttpServletResponse httpResponse,String out_trade_no,String total_amount, String subject,String body) throws IOException {

        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
//        request.setReturnUrl(httpRequest.getServerName()+httpRequest.getServerPort()+"/money/returnPly");
//        request.setNotifyUrl(httpRequest.getServerName()+httpRequest.getServerPort()+"/indent.html");
        request.setReturnUrl("http://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+"/money/returnPly");
        request.setNotifyUrl("http://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+"/indent.html");

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {// 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("生成表单："+form);
        httpResponse.setContentType("text/html;charset=" + CHARSET);
//        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
//        httpResponse.getWriter().flush();
//        httpResponse.getWriter().close();
        return form;
    }

    /**
     * 订单创建以后支付的方法
     * @param httpResponse
     * @param out_trade_no
     * @param total_amount
     * @param subject
     * @param body
     * @throws IOException
     * @return
     */
    public String alipay2(HttpServletResponse httpResponse, String out_trade_no, double total_amount , String subject, String body) throws IOException {
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                            + "\"total_amount\":\""+ total_amount +"\","
                            + "\"subject\":\""+ subject +"\","
                            + "\"body\":\""+ body +"\","
                            + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        /**
         * 调用SDK生成表单
         */
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        httpResponse.setContentType("text/html;charset=" + CHARSET);
//        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
//        httpResponse.getWriter().flush();
//        httpResponse.getWriter().close();
        System.out.println(form);
        return form;
    }

    /**
     * 验证支付是否成功(回调)
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws AlipayApiException
     */
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }
        //通过RSA2的方式
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");


            //支付成功，修复支付状态
            payService.updateById(out_trade_no);
            return "ok";//跳转付款成功页面
        }else{
            return "no";//跳转付款失败页面
        }

    }

}

