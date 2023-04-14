package com.baidu.travel.dao;

import com.baidu.travel.domain.Route;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 08:54
 * @describe
 */
@Mapper
public interface RouteDao extends BaseMapper<Route> {
    List<Route> findIndex();

    Route findOne(Integer rid);

    int findTotalCount(@Param("cid") int cid, @Param("rname") String rname);

    List<Route> findByPage(@Param("cid") int cid, @Param("start") int start,@Param("pageSize") int pageSize,@Param("rname") String rname);

    Route favoritePageQuery(@Param("rname") String rname, @Param("sprice") String sprice, @Param("eprice") String eprice, @Param("start") int start, @Param("pageSize") int pageSize);

    int findTotalCount1(String rname);

    List<Route> findByPage1(@Param("rname") String rname, @Param("sprice") String sprice, @Param("eprice") String eprice, @Param("start") int start, @Param("pageSize") int pageSize);
}
