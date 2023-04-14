package com.baidu.travel.service;

import com.baidu.travel.domain.User;

/**
 * @author pfk
 * @creatTime 2021/07/12下午 01:59
 * @describe
 */
public interface UserService {
    boolean regist(User user);

    User login(User user);
}
