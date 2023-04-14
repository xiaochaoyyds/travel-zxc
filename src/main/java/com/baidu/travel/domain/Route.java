package com.baidu.travel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 旅游线路商品实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@TableName("tab_route")
public class Route implements Serializable {
    /**
     * 线路id，必输
     */
    private int rid;
    /**
     * 线路名称，必输
     */
    private String rname;
    /**
     * 价格，必输
     */
    private double price;
    /**
     * 线路介绍
     */
    @TableField("routeIntroduce")
    private String routeIntroduce;
    /**
     * 是否上架，必输，0代表没有上架，1代表是上架
     */
    private String rflag;
    /**
     * 上架时间
     */
    private String rdate;
    /**
     * 是否主题旅游，必输，0代表不是，1代表是
     */
    @TableField(exist = false)
    private String isThemeTour;
    /**
     * 收藏数量
     */
    private int count;
    /**
     * 所属分类，必输
     */
    private int cid;
    /**
     * 缩略图
     */
    private String rimage;
    /**
     * 所属商家id
     */
    private int sid;
    /**
     * 抓取数据的来源id
     */
    @TableField("sourceId")
    private String sourceId;
    /**
     * 所属分类
     */
    @TableField(exist = false)
    private Category category;
    /**
     * 所属商家
     */
    @TableField(exist = false)
    private Seller seller;
    /**
     * 商品详情图片列表
     */
    @TableField(exist = false)
    private List<RouteImg> routeImgList;
    @TableField(exist = false)
    Integer fav_count=0;
}
