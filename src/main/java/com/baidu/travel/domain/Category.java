package com.baidu.travel.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 分类实体类
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Category implements Serializable {

    /**
     * 分类id
     */
    private int cid;

    /**
     * 分类名称
     */
    private String cname;

}
