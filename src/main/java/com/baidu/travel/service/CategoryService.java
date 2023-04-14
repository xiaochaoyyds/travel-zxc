package com.baidu.travel.service;

import com.baidu.travel.domain.Category;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:19
 * @describe
 */
public interface CategoryService {

    List<Category> findAll();

}
