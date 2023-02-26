package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    //登录功能根据账号密码查询
    @Select("Select * from user where name=#{param1} and password=#{param2}")
    User findByUsernameAndPassword(String name, String password);
    //管理员登录功能根据账号密码查询
//    @Select("Select * from admin where uid=#{param1} and password=#{param2}")
//    User findByAdminnameAndPassword(String uid, String password);

    //插入一个user
    @Insert("Insert into user(name,password,phone,age,email) values('${param1}', '${param2}','${param3}',${param4},'${param5}')")
    int insertUser(String name,String password,String phone,int age,String email);

    //修改密码
    @Update("update user set password=#{param1} where name=#{param2} and password=#{param3}")
    Boolean changePassword(String newPass, String name, String password);

    //查询单个uid
    @Select("Select * from user where name=#{param1}")
    User findByUserName(String name);

    //查询单个user
    @Select("Select * from user where id=#{param1}")
    User findByUserID(Long id);

    //查询管理员单个uid
//    @Select("Select * from admin where uid=#{param1}")
//    User findByAdminName(String id);

    //查询所有user
    @Select("Select * from user")
    List<User> findAllUser();

    //根据uid查询所有账户
    @Select("Select * from user where name like '%${_parameter}%' ")
    List<User> findUserByUid(String name);

    //根据uid删除账户
    @Delete("Delete from user where id = #{param1} ")
    boolean deleteUserByUid(Long uid);

}
