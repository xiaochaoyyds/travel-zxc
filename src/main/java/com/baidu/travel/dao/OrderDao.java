package com.baidu.travel.dao;

import com.baidu.travel.domain.Order;
import com.baidu.travel.domain.OrderDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 03:22
 * @describe    订单
 */
@Mapper
public interface OrderDao {




    List<OrderDo> getOrderDaoByUid(String uid);

    //修改订单状态
    void updateById(String out_trade_no);

    int findRidByOutTradeNo(String out_trade_no);


    void addOrder(@Param("rid") int rid, @Param("time") String time, @Param("ispay") String ispay, @Param("out_trade_no") String out_trade_no, @Param("uid") int uid);
}
