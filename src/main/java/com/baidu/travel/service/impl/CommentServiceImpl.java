package com.baidu.travel.service.impl;

import com.baidu.travel.dao.CommentDao;
import com.baidu.travel.domain.Comment;
import com.baidu.travel.domain.CommentVO;
import com.baidu.travel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:30
 * @describe
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public void addCommit(String rid, int uid, String content) {

        Comment c = new Comment();
        c.setRid(Integer.parseInt(rid));
        c.setUid(uid);
        c.setTime(new Date());
        c.setContent(content);
//        System.out.println("comm:"+c);
        commentDao.addComment(c);
    }
    /**
     *数据库查询评论
     */
    @Override
    public List<CommentVO> getCommit(String rid) {

        //根据线路id获取线路的评论
        List<Comment> commentByRid = commentDao.getCommentByRid(Integer.parseInt(rid));
        List<CommentVO> resuleList = new ArrayList<>();
        //遍历查询到的评论集合封装到pl的VO集合里面
        for (Comment comment : commentByRid) {
            try {
                CommentVO clone = comment.clone(CommentVO.class);
                clone.setText(comment.getContent());
                resuleList.add(clone);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resuleList;
    }
}
