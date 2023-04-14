package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用户实体类
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User implements Serializable {
    /**
     * 用户id
     */
    private int uid;
    /**
     * 用户名，账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 男或女
     */
    private String sex;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String head_pic;

}
