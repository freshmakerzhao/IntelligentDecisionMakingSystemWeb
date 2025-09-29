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
@TableName("second_hand")
public class SecondHand implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 物品名称
    private String name;

     // 购买时价格
    private String buyValue;

     // 当前价格
    private String curValue;

     // 物品描述
    private String content;

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

     // 是否失效(默认0，失效为1)
    private Integer isExpire;


}
