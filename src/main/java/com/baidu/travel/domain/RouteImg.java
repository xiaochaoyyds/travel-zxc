package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 旅游线路图片实体类
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RouteImg implements Serializable {
    /**
     * 商品图片id
     */
    private int rgid;
    /**
     * 旅游商品id
     */
    private int rid;
    /**
     * 详情商品大图
     */
    private String bigPic;
    /**
     * 详情商品小图
     */
    private String smallPic;
}
