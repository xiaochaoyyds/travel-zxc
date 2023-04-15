package com.baidu.travel.dao;

import com.baidu.travel.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pfk
 * @creatTime 2021/07/12下午 02:01
 * @describe    用户层操作数据库
 */
@Mapper
public interface UserDao {
    User FindByUsername(String username);

    void save(User user);

    User FindByCode(String code);

    void updateStatus(User user);

    User FindUsernameAndPassword(String username, String password);
    int updatePassWord(String newPassword,String username);
}
