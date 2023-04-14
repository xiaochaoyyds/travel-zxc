package com.baidu.travel.service.impl;

import com.baidu.travel.dao.*;
import com.baidu.travel.domain.PageBean;
import com.baidu.travel.domain.Route;
import com.baidu.travel.domain.RouteImg;
import com.baidu.travel.domain.Seller;
import com.baidu.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 08:50
 * @describe
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private OrderDao orderDao;


    @Override
    public PageBean<Route> findIndex() {
        PageBean<Route> bean = new PageBean<>();
        List<Route> result = routeDao.findIndex();
        bean.setList(result);
        return bean;
    }

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //创建返回值对象
        PageBean<Route> pageBean=new PageBean<>();
        //设置当前页
        pageBean.setCurrentPage(currentPage);
        //设置每页显示数量
        pageBean.setPageSize(pageSize);
        //查询总条数（类别id、路程名称）
        int totalCount = routeDao.findTotalCount(cid,rname);
        System.out.println("搜索到总条数："+totalCount);
        //设置总数量
        pageBean.setTotalCount(totalCount);

        int start=(currentPage-1)*pageSize;
        //进行分页查询
        List<Route> byPage = routeDao.findByPage(cid, start, pageSize,rname);
        //设置分页结果
        pageBean.setList(byPage);
        //设置总页数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        //根据rid 查询路线信息
        Route one = routeDao.findOne(Integer.parseInt(rid));
        //根据rid 查询路线的图片  rid == one.getRid()
        List<RouteImg> byRid = routeImgDao.findByRid(one.getRid());
        //给route 设置图片
        one.setRouteImgList(byRid);
        //根据sid  查询路线的所属商家
        Seller byId = sellerDao.findById(one.getSid());
        //给路线设置 商家
        one.setSeller(byId);
        //根据rid 查询路线被收藏的次数
        int countByRid = favoriteDao.findCountByRid(one.getRid());
        //设置收藏次数
        one.setCount(countByRid);
        return one;
    }

    @Override
    public Route findByOut_trade_no(String out_trade_no) {
        //根据订单号查询线路id
        int rid = orderDao.findRidByOutTradeNo(out_trade_no);
        //根据线路id查询线路
        Route one = routeDao.findOne(rid);
        return one;
    }

    @Override
    public PageBean<Route> favoritePageQuery(String rname, String sprice, String eprice, int currentPageT, int pageSize) {
        //创建返回值对象
        PageBean<Route> pageBean=new PageBean<>();
        //设置当前页
        pageBean.setCurrentPage(currentPageT);
        //设置每页显示数量
        pageBean.setPageSize(pageSize);
        //查询总条数（类别id、路程名称）
        int totalCount = routeDao.findTotalCount1(rname);
        System.out.println("搜索到总条数："+totalCount);
        //设置总数量
        pageBean.setTotalCount(totalCount);

        int start=(currentPageT-1)*pageSize;
        //进行分页查询
        List<Route> byPage = routeDao.findByPage1(rname,sprice,eprice, start, pageSize);
        //设置分页结果
        pageBean.setList(byPage);
        //设置总页数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }
}
