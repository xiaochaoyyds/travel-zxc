package com.baidu.travel.controller;

import com.baidu.travel.dao.RouteDao;
import com.baidu.travel.domain.PageBean;
import com.baidu.travel.domain.ResultInfo;
import com.baidu.travel.domain.Route;
import com.baidu.travel.domain.User;
import com.baidu.travel.service.FavoriteService;
import com.baidu.travel.service.RouteService;
import com.baidu.travel.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pfk
 * @creatTime 2021/07/13上午 08:44
 * @describe   路线相关操作
 */
@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;
    @Autowired
    private FavoriteService favoriteService;
    /**
     * 分页显示搜索结果
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean<Route> pageQuery(@RequestParam(name="currentPage") String currentPageStr,@RequestParam(name = "pageSize",required = false) String pageSizeStr,
                                     @RequestParam(name = "cid") String cidStr,@RequestParam(name = "rname") String rname){

        if("null".equals(rname)){
            rname = "";
        }
        int cid=0;
        if ( (cidStr!=null||cidStr.length()>0) && !("null".equals(cidStr))&&!("".equals(cidStr))){
            cid=Integer.parseInt(cidStr);
        }else {
            cid=5;
        }
        int currentPage=0;
        if (currentPageStr==null||currentPageStr.length()==0){
            currentPage=1;
        }else {
            currentPage=Integer.parseInt(currentPageStr);
        }
        int pageSize=0;
        if (pageSizeStr==null||pageSizeStr.length()==0){
            pageSize=5;
        }else {
            pageSize=Integer.parseInt(pageSizeStr);
        }

        PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize,rname);

        return pageBean;
    }

    /**
     * 显示首页图片
     * @return
     */
    @RequestMapping("/indexRoute")
    public PageBean<Route> indexRoute(){
        return routeService.findIndex();
    }

    /**
     * 添加收藏
     */
    @RequestMapping("/addFavorite")
    public void addFavorite(String rid, HttpServletRequest request){
        //从session域中获取用户信息,从用户信息中获取uid
        User user = (User) JedisUtil.getRedisObject(request.getSession().getId());

        int uid;
        if (user==null){
            return;
        }else {
            uid = user.getUid();
        }

        //添加收藏
        favoriteService.addFavorite(rid,uid);

    }

    /**
     * 我的收藏
     */
    @RequestMapping("/MyFavorite")
    @ResponseBody
    public PageBean<Route> myFavorite(@RequestParam(value = "currentPage",required = false) String currentPage,HttpServletRequest request){
        int currentPageT=0;
        if (currentPage==null||currentPage.length()==0){
            currentPageT=1;
        }else {
            currentPageT=Integer.parseInt(currentPage);
        }
        int pageSize=12;
        User login =(User)JedisUtil.getRedisObject(request.getSession().getId());
//        System.out.println("收藏的账户"+login);
//        System.out.println(currentPageT);
//        System.out.println(pageSize);
        PageBean<Route> myFavorite = favoriteService.findMyFavorite(currentPageT, pageSize, login.getUid());
        return myFavorite;
    }

    /**
     *  查询路线的所有信息（route对象：数据库的route数据 + 收藏次数 商家信息 路线图片 ）
     */
    @RequestMapping("/findOne")
    @ResponseBody
    public Route findOne(@RequestParam String rid){
        Route route=routeService.findOne(rid);
        return route;
    }

    /**
     *  是否被收藏
     */
    @RequestMapping("/isFavorite")
    @ResponseBody
    public boolean isFavorite(String rid,HttpServletRequest request){
        User user =(User) JedisUtil.getRedisObject(request.getSession().getId());
        int uid;

        if (user==null){
            uid=0;
        }else {
            uid=user.getUid();
        }
        boolean favorite = favoriteService.isFavorite(rid, uid);
        return favorite;
    }


    /**
     * 收藏排行榜
     */
    @RequestMapping("/FavoriteQuery")
    public PageBean<Route> FavoriteQuery(@RequestParam(name="currentPage") String currentPage,
                                         @RequestParam(name = "rname",required = false) String rname,
                                         @RequestParam(name = "Sprice",required = false) String sprice,
                                         @RequestParam(name = "Eprice",required = false) String eprice){
        int currentPageT=0;
        if (currentPage==null||currentPage.length()==0){
            currentPageT=1;
        }else {
            currentPageT=Integer.parseInt(currentPage);
        }
        int pageSize=20;


       PageBean<Route> bean  = routeService.favoritePageQuery(rname,sprice,eprice,currentPageT,pageSize);

        for (Route route : bean.getList()) {
            route.setCount(route.getFav_count());
        }
        return bean;
    }

}
