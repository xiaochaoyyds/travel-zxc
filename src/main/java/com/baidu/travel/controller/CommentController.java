package com.baidu.travel.controller;

import com.baidu.travel.domain.*;
import com.baidu.travel.service.CommentService;
import com.baidu.travel.service.WeatherService;
import com.baidu.travel.service.impl.CommentServiceImpl;
import com.baidu.travel.service.impl.WeatherServiceImpl;
import com.baidu.travel.utils.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:24
 * @describe
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private WeatherService weatherService;

    /**
     * 发送评论
     * @param rid
     * @param content
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/sendComment")
    @ResponseBody
    public ResultInfo sendComment(String rid, String content, HttpServletRequest request) throws JsonProcessingException {
        //获取登录用户
        User redisObject = (User) JedisUtil.getRedisObject(request.getSession().getId());
        if(redisObject==null){
            //用户没有登录
            ResultInfo r = new ResultInfo();
            r.setFlag(false);
            r.setErrorMsg("您还没有登录");
            return r;
        }

        //调用service的方法处理评论的逻辑
        commentService.addCommit(rid,redisObject.getUid(),content);
        ResultInfo r = new ResultInfo();
        r.setFlag(true);
        r.setErrorMsg("评论成功！！");
        return r;
    }

    /**
     * 展示评论到前台
     * @return
     */
    @RequestMapping("/showComment")
    @ResponseBody
    public List<CommentVO> showComment(String rid) throws JsonProcessingException {
        //获取线路id
        //调用service得到vo对象
        List<CommentVO> commit = commentService.getCommit(rid);

        return commit;
    }

    /**
     * 获取天气情况
     * @param city
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/getWeather")
    @ResponseBody
    public String getWeather(String city) throws JsonProcessingException {
        //获取需要查询的城市
        //调用service层获得天气对象
        Weather weather = weatherService.getWeatherByCite(city);
        String string = objectMapper.writeValueAsString(weather);
        return string;
    }
}
