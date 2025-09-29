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
@TableName("lost_and_found")
public class LostAndFound implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 物品名称
    private String name;

     // 物品类别id
    private Integer typeId;

     // 物品类别名称
    private String typeName;

     // 物品描述
    private String content;

     // 价值
    private Double value;

     // 悬赏
    private Double reward;

     // 联系方式
    private String contact;

     // 点赞数
    private Integer likeNums;

     // 浏览数
    private Integer browseNums;

     // 发布类别
    private String publishCategory;
    private String address;

     // 发布人id
    private Integer userId;

     // 发布人名称
    private String userNickname;

     // 发布时间
    private Date createTime;

     // 是否失效(默认0，失效为1)
    private Integer isExpire;


}
