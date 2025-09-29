package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@TableName(value = "sys_user")
@ToString
public class User {

//    mybatis-plus标注主键,AUIO自增
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    // 小程序昵称
    private String nickname;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 姓名
    private String name;
    // 年龄
    private Integer age;
    // 手机号
    private String phone;
    private String sex;
    // 身份证号
    private String idcard;
    // 权限
    private int rid;
    // 权限名称(admin/doctor)
    private String rname;

}
