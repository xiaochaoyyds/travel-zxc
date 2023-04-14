package com.baidu.travel.dao;

import com.baidu.travel.domain.Seller;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:07
 * @describe
 */
@Mapper
public interface SellerDao {
    Seller findById(int sid);
}
