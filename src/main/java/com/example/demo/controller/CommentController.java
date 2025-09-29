package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Comment;
import com.example.demo.entity.SchoolKeeping;
import com.example.demo.service.ICommentService;
import com.example.demo.service.ISchoolKeepingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @Author author
 * @Date 2023-05-03
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String userNickname,
                           @RequestParam(defaultValue = "") String content) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<Comment> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
//        不为空就搜索
        if (!"".equals(userNickname)) {
            queryWrapper.like("user_nickname", userNickname);
        }
        if (!"".equals(content)) {
            queryWrapper.like("content", content);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(commentService.page(page, queryWrapper));
    }

    /**
     * @Description 使帖子失效
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/17 22:21
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {

        // 删除该条评论
        commentService.removeById(id);
        // 删除该条评论下的评论
        commentService.remove(new QueryWrapper<Comment>().eq("parent_comment_id",id));

        return Result.success();
    }


    /**
     * @Description 获取评论区
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 13:28
     */
    @GetMapping("/wx/getCommentListById")
    public Result getCommentListById(@RequestParam Integer id) {
        return Result.success(commentService.list(new QueryWrapper<Comment>().eq("article_id",id)));
    }


    /**
     * @Description 新增评论
     * @param: one
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 16:16
     */
    @PostMapping("/wx/addComment")
    public Result postVaccinePlan(@RequestBody Comment one) {
        // 返回更新后的数据
        return Result.success(commentService.saveOrUpdate(one));
    }
}

