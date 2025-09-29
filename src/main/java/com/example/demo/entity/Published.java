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
public class Published implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 文章id
    private Integer articleId;

     // 发布类别(失、卖、车、跑)
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
