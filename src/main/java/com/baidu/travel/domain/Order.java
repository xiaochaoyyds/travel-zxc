package com.baidu.travel.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 订单实体类
 * @author pfk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Order {
    private int id;
    /**
     * 路线
     */
    private Route route;
    /**
     * 时间
     */
    private String time;
    /**
     * 是否支付
     */
    private String ispay;

    private String out_trade_no;
    /**
     * 所属用户
     */
    private User user;

}
