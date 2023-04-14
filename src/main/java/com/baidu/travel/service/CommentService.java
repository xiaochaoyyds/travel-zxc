package com.baidu.travel.service;

import com.baidu.travel.domain.Comment;
import com.baidu.travel.domain.CommentVO;

import java.util.List;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 04:30
 * @describe
 */
public interface CommentService {
    void addCommit(String rid, int uid, String content);

    List<CommentVO> getCommit(String rid);
}
