package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.LostAndFound;
import com.example.demo.entity.SecondHand;
import com.example.demo.entity.Travel;
import com.example.demo.service.ILostAndFoundService;
import com.example.demo.service.ISecondHandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @Author author
 * @Date 2023-05-03
 */
@RestController
@RequestMapping("/secondHand")
public class SecondHandController {

    @Resource
    private ISecondHandService secondHandService;

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String userNickname,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String content) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<SecondHand> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<SecondHand> queryWrapper = new QueryWrapper<>();
//        不为空就搜索
        if (!"".equals(userNickname)) {
            queryWrapper.like("user_nickname", userNickname);
        }
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(content)) {
            queryWrapper.like("content", content);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(secondHandService.page(page, queryWrapper));
    }

    /**
     * @Description 使帖子失效
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/17 23:25
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        secondHandService.update(
                new UpdateWrapper<SecondHand>()
                        .eq("id",id)
                        .set("is_expire",1)
        );
        return Result.success();
    }


//    ============================= 小程序 =============================

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/wx/getSecondHandPage")
    public Result getSecondHandPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<SecondHand> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<SecondHand> queryWrapper = new QueryWrapper<>();
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(secondHandService.page(page, queryWrapper));
    }



    /**
     * @Description 根据id找到帖子详情信息
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 13:13
     */
    @GetMapping("/wx/getSecondHandInfoById")
    public Result getSecondHandInfoById(@RequestParam Integer id) {
        return Result.success(secondHandService.getById(id));
    }


    /**
     * @Description 增加帖子浏览量
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 13:22
     */
    @GetMapping("/wx/addBrowseNums")
    public Result addBrowseNums(@RequestParam Integer id,
                                @RequestParam Integer browseNums) {
        // 在原基础上加1
        return Result.success(secondHandService.update(new UpdateWrapper<SecondHand>().set("browse_nums",browseNums+1).eq("id",id)));
    }

    /**
     * @Description 增加点赞数
     * @param: id
     * @param: likeNums
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 16:33
     */
    @GetMapping("/wx/addLikeNums")
    public Result addLikeNums(@RequestParam Integer id,
                              @RequestParam Integer likeNums) {
        // 在原基础上加1
        return Result.success(secondHandService.update(new UpdateWrapper<SecondHand>().set("like_nums",likeNums+1).eq("id",id)));
    }

    /**
     * @Description 新增帖子
     * @param: one
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 17:06
     */
    @PostMapping("/wx/postSecondHand")
    public Result postSecondHand(@RequestBody SecondHand one) {
        System.out.println(one);
        // 返回更新后的数据
        return Result.success(secondHandService.saveOrUpdate(one));
    }
}

