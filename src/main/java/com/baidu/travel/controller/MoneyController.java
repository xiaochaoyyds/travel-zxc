package com.baidu.travel.controller;

import com.alipay.api.AlipayApiException;
import com.baidu.travel.domain.Order;
import com.baidu.travel.domain.Route;
import com.baidu.travel.domain.User;
import com.baidu.travel.service.PayService;
import com.baidu.travel.service.RouteService;
import com.baidu.travel.utils.DateUtils;

import com.baidu.travel.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author pfk
 * @creatTime 2021/07/14下午 03:43
 * @describe    订单操作
 */
@Controller
@RequestMapping("/money")
public class MoneyController {
    @Autowired
    private RouteService routeService;
    @Autowired
    private PayService payService;
    @Autowired
    private PayController payController;

    /**
     * 创建订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/ply")
    @ResponseBody
    public String  ply(HttpServletRequest request, HttpServletResponse response,@RequestParam String rid) throws ServletException, IOException {
//        System.out.println("域名："+request.getServerName());
//        System.out.println("端口号："+request.getServerPort());
        //生成订单号
        String out_trade_no = UUID.randomUUID().toString();

        Jedis jedis = JedisUtil.getJedis();
        //将rid支付信息存入redis， 并设置过期时间
        jedis.set("payRouteRid",rid);
        jedis.expire("payRouteRid",60);
        //从redis中获取用户
        User user =(User) JedisUtil.getRedisObject(request.getSession().getId());
        //获取 该路线的所有信息
        Route one = routeService.findOne(rid);
        //路线信息的付款金额，必填
        String total_amount =Integer.toString((int)one.getPrice());
        //路线信息的订单名称，必填
        String subject =one.getRname();
        //商品描述，可空
        String body = one.getRouteIntroduce();
        //创建订单
        Order order=new Order();
        //设置订单所属用户
        order.setUser(user);
        //设置是否支付
        order.setIspay("否");
        //设置订单时间
        order.setTime(DateUtils.getCurrentTime());
        //设置订单号
        order.setOut_trade_no(out_trade_no);
        //设置订单的路线
        order.setRoute(one);
        //设置订单
        payService.addOrder(order);
        System.out.println("生成订单："+order.getOut_trade_no());
       return payController.alipay(request,response,out_trade_no,total_amount,subject,body);
    }

    /**
     * 验证支付是否成功(回调)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping("/returnPly")
    public void returnPly(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AlipayApiException {
        System.out.println("支付完成，正在回调..。");
        String s = payController.returnUrl(request, response);
        if(s.equals("no")){
            //支付失败
            System.out.println("支付失败--------------------------------------------------------------");
            response.sendRedirect(request.getContextPath()+"/route_detail.html?rid="+JedisUtil.getJedis().get("payRouteRid"));
        }else {
            //支付成功
            System.out.println("支付成功==============================================================");

            response.sendRedirect(request.getContextPath()+"/route_detail.html?rid="+JedisUtil.getJedis().get("payRouteRid"));
        }
    }


    /**
     * 创建订单后支付的方法
     * @param
     * @param response
     * @param out_trade_no
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws AlipayApiException
     */
     @RequestMapping("/ply2")
     @ResponseBody
    public String  ply2( HttpServletResponse response,@RequestParam String out_trade_no) throws IOException {
         System.out.println("支付订单："+out_trade_no);
         Route route = routeService.findByOut_trade_no(out_trade_no);
        return payController.alipay2(response,out_trade_no,route.getPrice(),route.getRname(),route.getRouteIntroduce());
    }



}
