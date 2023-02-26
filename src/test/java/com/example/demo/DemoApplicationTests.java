package com.example.demo;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    private CommentMapper commentMapper;

    @Test
    void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        for(User user:userList) {
            System.out.println(user);
        }
    }

    @Test
    void testSelect2() {
        System.out.println(("----- insertuser ------"));
        Comment comment=new Comment();
        comment.setBookuserid(1L);
        comment.setFromuserid(6L);
        comment.setText("非常好");
        comment.setTime(new Timestamp(12222222));
        comment.setBookid(40L);
        int a = commentMapper.insert(comment);
        System.out.println("+++++++++++++++++++++++"+a);
    }

}
