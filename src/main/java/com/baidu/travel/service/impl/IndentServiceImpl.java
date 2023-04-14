package com.baidu.travel.service.impl;

import com.baidu.travel.dao.OrderDao;
import com.baidu.travel.dao.RouteDao;
import com.baidu.travel.domain.IndentVO;
import com.baidu.travel.domain.OrderDo;
import com.baidu.travel.domain.Route;
import com.baidu.travel.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 03:17
 * @describe    订单操作
 */
@Service
public class IndentServiceImpl implements IndentService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RouteDao routeDao;

    @Override
    public List<IndentVO> getIndentVOByUid(String uid) {

        List<IndentVO> arr = new ArrayList<>();

        //根据订单号查询订单信息
        List<OrderDo> odb = orderDao.getOrderDaoByUid(uid);
        for (OrderDo orderDo : odb) {
            IndentVO indentVO = new IndentVO();
            //设置支付状态
            indentVO.setIspay(orderDo.getIspay());
//            System.out.println("orderDo："+orderDo);
            //设置订单编号
            indentVO.setOut_trade_no(orderDo.getOutTradeNo());


            //根据订单的路线id查询路线信息
            Route one = routeDao.findOne(orderDo.getRid());
            //设置线路名称
            indentVO.setName(one.getRname());
            //设置价格
            indentVO.setMoney(one.getPrice()+"");

            arr.add(indentVO);

        }
//        arr.forEach(System.out::println);
        return arr;
    }
}
