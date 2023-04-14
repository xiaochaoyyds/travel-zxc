package com.baidu.travel.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 用于显示订单列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class IndentVO {
    private String out_trade_no;
    private String name;
    private String money;
    private String ispay;


}
