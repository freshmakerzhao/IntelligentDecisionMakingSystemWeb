package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;


@Data
@TableName(value = "sys_role")
@ToString
public class Role {

//    mybatis-plus标注主键,AUIO自增
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; // 主键，自增
    private String name; // 权限名称
}
