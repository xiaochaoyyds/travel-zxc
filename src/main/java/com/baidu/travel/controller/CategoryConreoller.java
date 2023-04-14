package com.baidu.travel.controller;

import com.baidu.travel.domain.Category;
import com.baidu.travel.service.CategoryService;
import com.baidu.travel.utils.GsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:16
 * @describe    类别操作
 */
@RestController
@RequestMapping("/category")
public class CategoryConreoller {
    @Autowired
    private CategoryService categoryService;
    /**
     * 查询所有类别
     */
    @RequestMapping("/findAll1")
    public String findAll1() throws JsonProcessingException {
        List<Category> all = categoryService.findAll();

        ObjectMapper objectMapper=new ObjectMapper();
        String s = GsonUtils.toJson(all);
        return s;
    }

}
