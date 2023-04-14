package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 商家实体类
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Seller implements Serializable {
    /**
     * 商家id
     */
    private int sid;
    /**
     * 商家名称
     */
    private String sname;
    /**
     * 商家电话
     */
    private String consphone;
    /**
     * 商家地址
     */
    private String address;


}
