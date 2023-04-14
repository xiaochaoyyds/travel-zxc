package com.baidu.travel.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.travel.domain.IndentVO;
import com.baidu.travel.domain.ResultInfo;
import com.baidu.travel.domain.User;
import com.baidu.travel.service.IndentService;
import com.baidu.travel.service.UserService;
import com.baidu.travel.service.impl.IndentServiceImpl;
import com.baidu.travel.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpRequest;
import org.apache.ibatis.annotations.Param;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author pfk
 * @creatTime 2021/07/12下午 01:54
 * @describe    用户相关操作
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Jedis jedis = JedisUtil.getJedis();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IndentService indentService;



    /**
     * 用户注册
     * @param
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/regist")
    @ResponseBody
    public ResultInfo regist(@RequestParam(name="check") String check,User user,HttpServletRequest request){
        System.out.println("用户注册----");
        System.out.println(request.getSession());
        //从redis获取验证码
        String tel = (String) request.getSession().getAttribute("registTelephone");
        System.out.println(user.getTelephone());
        String registerYzm = jedis.get(user.getTelephone()+"registerYzm");
        System.out.println("redis验证码："+registerYzm);
        ResultInfo resultInfo=new ResultInfo();

        /**
         * 如果redis的验证码为空，或者与前端发来的验证码不匹配 ==>false
         */
        if (registerYzm==null || !registerYzm.equalsIgnoreCase(check)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            return resultInfo;
        }
        /**
         * 注册到数据库，封装返回域对象
         */
        boolean flag=userService.regist(user);
        if (flag){
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        System.out.println(resultInfo);
        return resultInfo;
    }

    /**
     * 注册发送验证码
     * @param request   C6B1EFE1FD730ED0020DA45EE5BABD71
     * @return          C6B1EFE1FD730ED0020DA45EE5BABD71
     */
    @RequestMapping("/checkWord1")
    @ResponseBody
    public ResultInfo checkWord1(String telephone, HttpServletRequest request){
        //生成验证码
        String yzm = CheckCodeController.getCheckCode();
        System.out.println("生成验证码："+yzm);
        //从redis查询手机号
        String s = jedis.get(telephone);
        /**
         * 判断验证码是否发过
         */
        if (s==null){

            MessageUtil.sendMessage(telephone,yzm);

            jedis.set(telephone+"registerYzm",yzm);
            //把查询到的手机号保存到session
            System.out.println(request.getSession());
            request.getSession().setAttribute("registTelephone",telephone);
            jedis.expire(telephone+"registerYzm",60);
            jedis.set(telephone,"1");
            jedis.expire(telephone,3600);
            return new ResultInfo(true,null,"success");
        }
        /**
         * 检查手机号发送次数是否符合
         */
        //获取手机号发送次数
        int i = Integer.parseInt(s);
        //限制手机号验证码发送次数为10次
        if (i<=10){
            MessageUtil.sendMessage(telephone,yzm);
            jedis.set(telephone+"registerYzm",yzm);
            jedis.expire(telephone+"registerYzm",60);
            jedis.set(telephone,(i+1)+"");
        }else {
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setErrorMsg("超过最大获取次数，每个小时只能发十条");
            return resultInfo;

        }
        return new ResultInfo(true,null,"sussess");
    }

    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo login(@RequestParam(name="check",required = false) String check,User user, HttpServletRequest request){
        System.out.println("用户登录---");
        ResultInfo resultInfo=new ResultInfo();
        User login=userService.login(user);
        if (login==null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }else {
            String id = request.getSession().getId();
            JedisUtil.addRedisObject(id,login);
            resultInfo.setFlag(true);
        }

        return resultInfo;
    }

    /**
     *  （登录成功后）回显用户信息
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/find")
    @ResponseBody
    public Object find(HttpServletRequest request) throws ServletException, IOException {
        Object login = JedisUtil.getRedisObject(request.getSession().getId());
        return login;
    }

    /**
     * （用户退出），清除数据redis和session域数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getSession().invalidate();
        JedisUtil.removeRedisObject(request.getSession().getId());
        //跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 人脸注册
     * @param img
     * @param request
     * @return
     * @throws JsonProcessingException
     * @throws JSONException
     */
    @RequestMapping("/faceRegister")
    @ResponseBody
    public ResultInfo faceRegister(@RequestParam String img,HttpServletRequest request) throws JsonProcessingException, JSONException {
        System.out.println("前台获取img数据："+img);
        String s1 = img.split(",")[1];
        //人脸检测
        String s = FaceDetect.faceDetect(s1);
        //人脸搜索
        String[] strings = FaceSearch.faceSearch(s1);
        int i = Integer.parseInt(strings[0]);
        /**
         * s为人脸检测结果：
         *      1：检测到人脸
         *      0：未检测到人脸
         */
        if (s.equals("0")){
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setErrorMsg("未检测到人脸");
            return resultInfo;
        }
        if (i>80){
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setErrorMsg("人脸已注册");
            return resultInfo;
        }
        User login = (User) JedisUtil.getRedisObject(request.getSession().getId());
        ObjectMapper objectMapper=new ObjectMapper();
        String user = objectMapper.writeValueAsString(login);
        String add = FaceAdd.add(s1, user);
        ResultInfo resultInfo=new ResultInfo();
        if (add.equals("1")){
            resultInfo.setErrorMsg("注册成功");
        }else {
            resultInfo.setErrorMsg("注册失败");
        }

        return resultInfo;
    }

    /**
     * 人脸登录
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/faceLogin")
    @ResponseBody
    public ResultInfo faceLogin(@RequestParam String img, HttpServletRequest request){
        String s = img.split(",")[1];
        String[] strings = FaceSearch.faceSearch(s);
        ResultInfo resultInfo=new ResultInfo();
        if (strings[0].equals("1")){
            resultInfo.setFlag(true);
            String string = strings[1];
            JSONObject jsonObject = JSONObject.parseObject(string);
            User user = JSONObject.toJavaObject(jsonObject, User.class);
            JedisUtil.addRedisObject(request.getSession().getId(),user);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("人脸识别失败");
        }
        return resultInfo;
    }

    /**
     * 快递查询
     * @param kddh
     * @return
     */
    @RequestMapping("/select")
    @ResponseBody
    public String expressInquiry(@RequestParam String kddh){
        String getexpress = Express.Getexpress(kddh);
        System.out.println(getexpress);
        return getexpress;
    }

    /**
     * 显示订单
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/indent")
    @ResponseBody
    public String indent(HttpServletRequest request) throws JsonProcessingException {

        //从redis获取用户id，验证用户是否登录
        User redisObject = (User) JedisUtil.getRedisObject(request.getSession().getId());
        if(redisObject==null){
            return "";
        }
        //调用service层获取订单的详细信息
        List<IndentVO> indent = indentService.getIndentVOByUid(redisObject.getUid()+"");
        String string = objectMapper.writeValueAsString(indent);
        System.out.println("订单："+string);
        return string;
    }
}
