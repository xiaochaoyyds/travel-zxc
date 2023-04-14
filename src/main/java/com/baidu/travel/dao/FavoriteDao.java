package com.baidu.travel.dao;

import com.baidu.travel.domain.Favorite;
import com.baidu.travel.domain.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 09:33
 * @describe
 */
@Mapper
public interface FavoriteDao {
    void addFavorite(@Param("rid") int rid, @Param("uid") int uid);

    int findCountByUid(int uid);

    List<Route> myFavorite(@Param("start") int start, @Param("pageSize") int pageSize,@Param("uid") int uid);

    int findCountByRid(int rid);

    Favorite findByRidAndUid(@Param("rid") int i,@Param("uid") int uid);
}
