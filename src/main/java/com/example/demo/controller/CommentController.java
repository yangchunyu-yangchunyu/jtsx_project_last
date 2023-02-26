package com.example.demo.controller;


import com.example.demo.entity.Book;
import com.example.demo.entity.Comment;
import com.example.demo.result.Result;
import com.example.demo.service.BookService;
import com.example.demo.service.CommentService;
import net.coobird.thumbnailator.Thumbnails;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class CommentController {
    @Resource(name = "commentService")
    private CommentService commentService;


    //根据书籍id返回所有的评论
    @CrossOrigin
    @PostMapping("api/CommentsByBookId")
    public List<Comment> searchCommentsByBookId(@RequestParam Map<String, Object> keywords, HttpSession session) {
        Long bookid=Long.parseLong(keywords.get("bookid").toString());
        return commentService.findAllByBookId(bookid);
    }






    //添加
    @CrossOrigin
    @PostMapping("api/insertComment")
    public Comment insertComment(@RequestParam Map<String, Object> keywords, HttpSession session) {
        Comment comment=new Comment();
        Long bookuserid=Long.parseLong(keywords.get("bookuserid").toString());
        Long fromuserid=Long.parseLong(keywords.get("fromuserid").toString());
        Long bookid=Long.parseLong(keywords.get("bookid").toString());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String text=keywords.get("text").toString();
        String fromusername=keywords.get("fromusername").toString();
        //
        comment.setBookid(bookid);
        comment.setTime(time);
        comment.setText(text);
        comment.setFromuserid(fromuserid);
        comment.setBookuserid(bookuserid);
        comment.setFromusername(fromusername);
        System.out.println(comment);
        if (commentService.insertComment(comment) == 0) {
            return null;
        } else {
            return comment;
        }
    }

}
