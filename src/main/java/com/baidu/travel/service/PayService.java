package com.baidu.travel.service;

import com.baidu.travel.domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:57
 * @describe
 */
public interface PayService {
    void addOrder(Order order);
//
//    void updateById(String out_trade_no);

    void aliPay(HttpServletResponse response, HttpServletRequest request) throws IOException;
    //修改订单状态
    void updateById(String out_trade_no);

}
