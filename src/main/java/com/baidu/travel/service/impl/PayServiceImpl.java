package com.baidu.travel.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baidu.travel.AlipayConfig;
import com.baidu.travel.dao.OrderDao;
import com.baidu.travel.domain.Order;
import com.baidu.travel.service.PayService;
import com.baidu.travel.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:57
 * @describe
 */
@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private OrderDao orderDao;
    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order.getRoute().getRid(),order.getTime(),order.getIspay(),order.getOut_trade_no(),order.getUser().getUid());
    }

//    @Override
//    public void updateById(String out_trade_no) {
//        orderDao.updateById(out_trade_no);
//    }

    @Override
    public void aliPay(HttpServletResponse response, HttpServletRequest request) throws IOException {

    }

    @Override
    public void updateById(String out_trade_no) {
        orderDao.updateById(out_trade_no);
    }
}
