package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Travel implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 出发地
    private String fromAddress;

     // 出发时间
    private Date fromTime;

     // 目标地
    private String toAddress;

     // 抵达时间
    private Date toTime;

     // 出行描述
    private String content;

     // 空闲位置
    private Integer freePosition;

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
