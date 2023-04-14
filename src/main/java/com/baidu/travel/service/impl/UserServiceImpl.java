package com.baidu.travel.service.impl;



import com.baidu.travel.dao.UserDao;
import com.baidu.travel.domain.User;
import com.baidu.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean regist(User user) {
        User user1 = userDao.FindByUsername(user.getUsername());
        if (user1!=null){//查询到已经注册的账号==>false
            return false;
        }else {
            userDao.save(user);
        }
        return true;
    }



//    @Override
//    public boolean active(String code) {
//        User   user=userDao.FindByCode(code);
//        if (user!=null){
//            userDao.updateStatus(user);
//            return true;
//        }else {
//            return false;
//        }
//
//    }
//
    @Override
    public User login(User user) {

        return userDao.FindUsernameAndPassword(user.getUsername(),user.getPassword());
    }
//
//    @Override
//    public User findUserByUserName(String username) {
//        return userDao.FindByUsername(username);
//    }
}
