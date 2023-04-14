package com.baidu.travel.dao;

import com.baidu.travel.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:32
 * @describe
 */
@Mapper
public interface CommentDao {
    void addComment(Comment comment);

    List<Comment> getCommentByRid(int parseInt);

}
