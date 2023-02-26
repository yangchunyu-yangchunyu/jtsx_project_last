package com.example.demo.mapper;


import com.example.demo.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {

    ////根据书的id找到所有的评论
    @Select("Select * from comment where comment.bookid=#{param1}")
    List<Comment> findAllByBookId(Long bookid);


}
