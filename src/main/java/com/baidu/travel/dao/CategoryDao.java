package com.baidu.travel.dao;

import com.baidu.travel.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:21
 * @describe
 */
@Mapper
public interface CategoryDao {
    List<Category> findAll();

}
