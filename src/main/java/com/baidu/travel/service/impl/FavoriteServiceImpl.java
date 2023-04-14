package com.baidu.travel.service.impl;

import com.baidu.travel.dao.FavoriteDao;
import com.baidu.travel.domain.Favorite;
import com.baidu.travel.domain.PageBean;
import com.baidu.travel.domain.Route;
import com.baidu.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 09:31
 * @describe
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;
    @Override
    public void addFavorite(String rid, int uid) {
//        System.out.println(rid);
//        System.out.println(uid);
        favoriteDao.addFavorite(Integer.parseInt(rid),uid);
    }

    @Override
    public PageBean<Route> findMyFavorite(int currentPage, int pageSize, int uid) {
        int  totalCount=favoriteDao.findCountByUid(uid);
        PageBean<Route> routePageBean=new PageBean<>();
        routePageBean.setCurrentPage(currentPage);
        routePageBean.setTotalCount(totalCount);
        routePageBean.setPageSize(pageSize);
        int start=(currentPage-1)*pageSize;
        List<Route> routes = favoriteDao.myFavorite(start, pageSize, uid);
        routePageBean.setList(routes);
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;

        routePageBean.setTotalPage(totalPage);

        return routePageBean;
    }

    @Override
    public boolean isFavorite(String rid, int uid) {
        int i = Integer.parseInt(rid);
        Favorite byRidAndUid = favoriteDao.findByRidAndUid(i, uid);
        return byRidAndUid!=null;
    }
}
