package com.example.demo.controller;



import com.example.demo.result.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    //Mybatis操作数据库
    @Resource(name = "userService")
    private UserService userService;

    //登录账号
    //注解@CrossOrigin解决跨域问题
    //@CrossOrigin
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public User login(@RequestParam Map<String,Object> requestMap) {
        return userService.login(requestMap);
    }

    //管理员账号
//    @CrossOrigin
//    @PostMapping(value = "api/adminLogin")
//    @ResponseBody
//    public String adminLogin(@RequestParam Map<String,Object>requestMap) {
//        return userService.adminLogin(requestMap);
//    }

    //注册账号
    @CrossOrigin
    @PostMapping(value="api/insertUser")
    @ResponseBody
    //注意这里是RequestParam insertUser?
    public Result insertUser(@RequestParam Map<String,Object>requestMap){
        return userService.insertUser(requestMap);
    }

    //修改密码
    @PostMapping(value = "api/changePassword")
    @ResponseBody
    public Result ChangePassword(@RequestParam Map<String, Object> requestMap) {
        return userService.changePassword(requestMap);
    }

    //查找账户是否存在
    @PostMapping(value = "api/selectUserByName")
    @ResponseBody
    public User selectUser(@RequestParam Map<String, Object> requestMap) {
        return userService.selectUser(requestMap);
    }

    //查找管理员账户是否存在
//    @PostMapping(value = "api/selectAdmin")
//    @ResponseBody
//    public Boolean selectAdmin(@RequestParam Map<String, Object> requestMap) {
//        return userService.selectAdmin(requestMap);
//    }

    //查询所有的账户信息
    @PostMapping(value = "api/selectAllUser")
    @ResponseBody
    public List<User> selectAllUser(@RequestParam Map<String,Object>requestMap){
        System.out.println(requestMap);
        return userService.selectAllUser(requestMap);
    }

    //根据id查询账户信息
    @CrossOrigin
    @PostMapping(value = "api/selectUserByid")
    @ResponseBody
    public User selectUserByid(@RequestParam Map<String,Object>requestMap){
        Long id=Long.parseLong(requestMap.get("id").toString());
        System.out.println(id);
        User user=userService.selectUserByid(id);
        user.setPassword("");
        return user;
    }

    //根据种类查询图书
//    @PostMapping("api/selectUserByUid")
//    @ResponseBody
//    public List<User> listByCategory(@RequestParam Map<String,Object>requestMap)  {
//        return userService.selectUserByUid(requestMap);
//    }

    //根据uid删除账户
    @PostMapping("api/deleteUserByUid")
    @ResponseBody
    public boolean deleteUserByUid(@RequestParam Map<String,Object>requestMap){
        return userService.deleteUserByUid(requestMap);
    }
}

