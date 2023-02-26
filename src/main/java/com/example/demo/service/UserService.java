package com.example.demo.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.result.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserService {

    //根据账号和密码查找用户
    @Resource(name = "userMapper")
    private UserMapper userMapper;
    public User login(Map<String,Object> requestMap) {
        // 对 html 标签进行转义，防止 XSS 攻击

        String name = requestMap.get("name").toString();
        String password = requestMap.get("password").toString();
        //uid = HtmlUtils.htmlEscape(uid);
        return userMapper.findByUsernameAndPassword(name, password);
    }

    //根据账号和密码查找管理员
//    public String adminLogin(@RequestParam Map<String,Object>requestMap) {
//        // 对 html 标签进行转义，防止 XSS 攻击
//        String uid = requestMap.get("uid").toString();
//        uid = HtmlUtils.htmlEscape(uid);
//        User findUser = userMapper.findByAdminnameAndPassword(uid, requestMap.get("password").toString());
//        if (findUser == null) {
//            return "";
//        } else {
//            return findUser.getUid();
//        }
//    }

    //  注册账号密码
    public Result insertUser(Map<String,Object>requestMap){
        System.out.println(requestMap);
        try{
            String name=requestMap.get("name").toString();
            String password=requestMap.get("password").toString();
            String phone=requestMap.get("phone").toString();
            int age=Integer.parseInt(requestMap.get("age").toString());
            String email=requestMap.get("email").toString();
            int row=userMapper.insertUser(name,password,phone,age,email);
            if (row==1){
                return new Result(200);
            } else {
                return new Result(400);
            }
        }catch(Exception e){
            System.out.println("++++");
            return new Result(400);
        }

    }

    //修改密码
    public Result changePassword(Map<String, Object> requestMap) {
        String name=requestMap.get("name").toString();
        String oldPass = requestMap.get("oldPass").toString();
        String newPass = requestMap.get("newPass").toString();
        if (userMapper.changePassword(newPass, name, oldPass)) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    //查找账户是否存在
    public User selectUser(Map<String, Object> requestMap) {
        String name=requestMap.get("name").toString();
        //Long id=Long.parseLong(idstr);
        return userMapper.findByUserName(name);
    }

    //查找管理员账户是否存在
//    public boolean selectAdmin(Map<String, Object> requestMap) {
//        String id = requestMap.get("uid").toString();
//        return userMapper.findByAdminName(id) != null;
//    }


    //查找所有的账号
    public List<User> selectAllUser(Map<String,Object>requestMap){
        return userMapper.findAllUser();
    }

    //查找所有的账号
    public User selectUserByid(Long id){
        return userMapper.findByUserID(id);
    }

    //根据部分uid模糊查询所以账户
//    public List<User> selectUserByUid(Map<String,Object>requestMap){
//        String uid=requestMap.get("uid").toString();
//        //判断是不是nanfang管理员查找的，防止恶意人士
//        if(uid.equals("")){
//            return null;
//        }else{
//            //返回所有账户信息
//            return userMapper.findUserByUid(uid);
//        }
//    }

    //根据部分name模糊查询所以账户
    public List<User> selectUserByName(Map<String,Object>requestMap){
        String name=requestMap.get("name").toString();
        if(name.equals("")){
            return null;
        }else{
            //返回所有账户信息
            return userMapper.findUserByUid(name);
        }
    }

    //根据uid删除该账户
    public boolean deleteUserByUid(Map<String,Object>requestMap){
        String idstr=requestMap.get("uid").toString();
        if(idstr.equals("")){
            return false;
        }else{
            Long id=Long.parseLong(idstr);
            //返回删除后信息
            return userMapper.deleteUserByUid(id);
        }
    }
}
