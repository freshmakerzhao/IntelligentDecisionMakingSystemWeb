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
 * @Author 作者
 * @Date 2023-04-12
 */
@Data
@TableName("sys_announcement")
public class Announcement implements Serializable {

     // 主键
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 公告标题
    private String title;

     // 公告内容
    private String content;

     // 编写公告的用户id
    private Integer userId;

     // 编写公告的用户名称
    private String userName;

     // 创建时间
    private Date createtime;


}
