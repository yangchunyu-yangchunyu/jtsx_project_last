package com.example.demo.service;


import com.example.demo.entity.Comment;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service(value = "commentService")
public class CommentService {
    @Resource(name = "commentMapper")
    private CommentMapper commentMapper;



    //根据书籍id返回所有的评论
    public List<Comment> findAllByBookId(Long bookid) {
        return commentMapper.findAllByBookId(bookid);
    }


    //添加一本书
    public int insertComment(Comment comment) {
        return commentMapper.insert(comment);
    }
}
