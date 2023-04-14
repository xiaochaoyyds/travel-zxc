package com.baidu.travel.service.impl;

import com.baidu.travel.dao.CategoryDao;
import com.baidu.travel.domain.Category;
import com.baidu.travel.service.CategoryService;
import com.baidu.travel.utils.JedisUtil;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:19
 * @describe    分类操作持久层
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
//        System.out.println("首页加载的category:"+category);
        List<Category> all;
        if (category==null||category.size()==0){
            all = categoryDao.findAll();
            for (int i = 0; i <all.size(); i++) {
                jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());

            }
        }else {
            all=new ArrayList<>();
            for (Tuple tuple:category){
                Category category1=new Category();
                category1.setCname(tuple.getElement());
                category1.setCid((int)tuple.getScore());
                all.add(category1);
            }
        }
        jedis.close();
        return all;
    }
}
