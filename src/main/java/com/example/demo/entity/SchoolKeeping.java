package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 
 * @Author author
 * @Date 2023-05-03
 */
@Data
@TableName("school_keeping")
public class SchoolKeeping implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 任务描述
    private String taskContent;

    // 出发地
    private String fromAddress;
    // 目标地
    private String toAddress;
    // 跑腿费
    private Double taskValue;

     // 抵达时间（如何时送到等）
    private Date taskTime;

     // 联系方式
    private String contact;

     // 点赞数
    private Integer likeNums;

     // 浏览数
    private Integer browseNums;

     // 发布类别
    private String publishCategory;

     // 发布人id
    private Integer userId;

     // 发布人名称
    private String userNickname;

     // 发布时间
    private Date createTime;

     // 是否过期(10分钟过期置为1)
    private Integer isExpire;


}
