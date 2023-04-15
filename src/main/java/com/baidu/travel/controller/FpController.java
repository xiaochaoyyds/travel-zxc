package com.baidu.travel.controller;

import com.baidu.travel.domain.ResultInfo;
import com.baidu.travel.service.UserService;
import com.baidu.travel.utils.JedisUtil;
import com.baidu.travel.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
@RestController
public class FpController {
    @Autowired
    private UserService userService;
    private Jedis jedis = JedisUtil.getJedis();
    @RequestMapping("/findCode")
    public ResultInfo findCode(String telephone, HttpServletRequest request){
        // 生成验证码
        String yzm = CheckCodeController.getCheckCode();
        System.out.println("生成验证码："+yzm);
        MessageUtil.sendMessage(telephone,yzm);
        //存入redis
        jedis.set(telephone+"findCode",yzm);
        jedis.expire(telephone+"findCode",60);
        return new ResultInfo(true,null,"success");
    }

    @RequestMapping("/findMyWord")
    public ResultInfo findMyWord(String telephone,String yzm,String newpassword,String username){
        //取出验证码
        String s = jedis.get(telephone+"findCode");
        ResultInfo resultInfo = new ResultInfo();
        //正确则修改，不正确返回false
        if(s == null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码不存在或者已经过期，请重试");
            return resultInfo;
        }else {
            if(s.equals(yzm)){
                int i = userService.updatePassWord(newpassword,username);
                if(i>0){
                    return new ResultInfo(true,null,"找回成功");
                }else {
                    return new ResultInfo(false,null,"系统异常");
                }
            }else {
                return new ResultInfo(false,null,"验证码错误");
            }
        }

    }
}
