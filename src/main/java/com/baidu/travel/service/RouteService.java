package com.baidu.travel.service;

import com.baidu.travel.domain.PageBean;
import com.baidu.travel.domain.Route;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 08:50
 * @describe
 */
public interface RouteService {
    PageBean<Route> findIndex();

    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    Route findOne(String rid);

    Route findByOut_trade_no(String out_trade_no);

    PageBean<Route> favoritePageQuery(String rname, String sprice, String eprice, int currentPageT, int pageSize);

}
