package com.baidu.travel;

import com.baidu.travel.dao.RouteDao;
import com.baidu.travel.dao.RouteImgDao;
import com.baidu.travel.domain.Route;
import com.baidu.travel.domain.RouteImg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class TravelApplicationTests {

    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;

    @Test
    void contextLoads() {
        //不使用新变量，交换a、b值
        int a=10;
        int b=20;
    }
    /**
     * 批量更换数据库rimage
     */
    @Test
    void replaceImg(){
        List<Route> routes = routeDao.selectList(null);
        System.out.println(routes.size());
        routes.forEach(e->{
            List<RouteImg> routeImgs = routeImgDao.findByRid(e.getRid());
            e.setRouteImgList(routeImgs);
            System.out.println(e.getRouteImgList());
        });
    }
   @Test
    void test(){
//       HashMap<Object, Object> index = routeDao.findIndex();
//       System.out.println(index.size());
//       for (Object o : index.keySet()) {
//           System.out.println(o);
//       }

   }



}
