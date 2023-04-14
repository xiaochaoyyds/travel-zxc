package com.baidu.travel.dao;

import com.baidu.travel.domain.RouteImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:05
 * @describe
 */
@Mapper
public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
