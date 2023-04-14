package com.baidu.travel.service;

import com.baidu.travel.domain.IndentVO;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 03:17
 * @describe
 */
public interface IndentService {
    List<IndentVO> getIndentVOByUid(String s);
}
