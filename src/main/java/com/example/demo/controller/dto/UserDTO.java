package com.example.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @Description 用户登录信息
 * @Author author
 * @Date 2023/4/8 13:22
 */
@Data
public class UserDTO {
    private Integer id;
    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private Integer age; // 年龄
    private String sex; // 性别
    private String phone; // 手机号
    private String idcard; // 身份证号
    private int rid; // 权限id
    private String rname; // 权限名称
    private String token; // token
}