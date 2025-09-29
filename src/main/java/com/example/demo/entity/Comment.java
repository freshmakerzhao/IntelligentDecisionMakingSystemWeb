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
public class Comment implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 评论内容
    private String content;

     // 创建时间
    private Date createTime;

     // 文章id
    private Integer articleId;

     // 父级id
    private Integer parentCommentId;

     // 评论人id
    private Integer userId;

     // 评论人名称
    private String userNickname;


}
