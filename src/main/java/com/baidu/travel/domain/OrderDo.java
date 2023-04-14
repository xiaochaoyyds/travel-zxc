package com.baidu.travel.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 数据库对象订单表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@TableName("tab_order")
public class OrderDo {

    private Integer id;
    private Integer rid;
    private Date time;
    private String ispay;
    @TableField(value = "out_trade_no")
    private String outTradeNo;
    private Integer uid;

}
