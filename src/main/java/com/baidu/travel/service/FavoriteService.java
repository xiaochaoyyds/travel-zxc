package com.baidu.travel.service;

import com.baidu.travel.domain.PageBean;
import com.baidu.travel.domain.Route;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 09:30
 * @describe
 */
public interface FavoriteService {
    void addFavorite(String rid, int uid);

    PageBean<Route> findMyFavorite(int currentPage, int pageSize, int uid);

    boolean isFavorite(String rid, int uid);

}
