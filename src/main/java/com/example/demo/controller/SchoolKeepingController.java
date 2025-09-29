package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.SchoolKeeping;
import com.example.demo.entity.Travel;
import com.example.demo.service.ISchoolKeepingService;
import com.example.demo.service.ITravelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @Author author
 * @Date 2023-05-03
 */
@RestController
@RequestMapping("/schoolKeeping")
public class SchoolKeepingController {

    @Resource
    private ISchoolKeepingService schoolKeepingService;

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String userNickname,
                           @RequestParam(defaultValue = "") String taskContent) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<SchoolKeeping> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<SchoolKeeping> queryWrapper = new QueryWrapper<>();
//        不为空就搜索
        if (!"".equals(userNickname)) {
            queryWrapper.like("user_nickname", userNickname);
        }
        if (!"".equals(taskContent)) {
            queryWrapper.like("task_content", taskContent);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(schoolKeepingService.page(page, queryWrapper));
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
//        schoolKeepingService.update(
//                new UpdateWrapper<SchoolKeeping>()
//                        .eq("id",id)
//                        .set("is_expire",1)
//        );
        schoolKeepingService.removeById(id);
        return Result.success();
    }


//    ============================= 小程序 =============================

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/wx/getSchoolKeepingPage")
    public Result getSchoolKeepingPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "-1") Integer userId) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<SchoolKeeping> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<SchoolKeeping> queryWrapper = new QueryWrapper<>();
        // 属于个人的帖子
        if (userId != -1){
            queryWrapper.eq("user_id",userId);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(schoolKeepingService.page(page, queryWrapper));
    }



    /**
     * @Description 根据id找到帖子详情信息
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 13:13
     */
    @GetMapping("/wx/getSchoolKeepingInfoById")
    public Result getSchoolKeepingInfoById(@RequestParam Integer id) {
        return Result.success(schoolKeepingService.getById(id));
    }


    /**
     * @Description 根据帖子id删除
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/6/4 17:31
     */
    @GetMapping("/wx/delSchoolKeepingInfoById")
    public Result delSchoolKeepingInfoById(@RequestParam Integer id) {
        return Result.success(schoolKeepingService.removeById(id));
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
        return Result.success(schoolKeepingService.update(new UpdateWrapper<SchoolKeeping>().set("browse_nums",browseNums+1).eq("id",id)));
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
        return Result.success(schoolKeepingService.update(new UpdateWrapper<SchoolKeeping>().set("like_nums",likeNums+1).eq("id",id)));
    }

    /**
     * @Description 新增帖子
     * @param: one
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 17:06
     */
    @PostMapping("/wx/postSchoolKeeping")
    public Result postSchoolKeeping(@RequestBody SchoolKeeping one) {
        // 返回更新后的数据
        return Result.success(schoolKeepingService.saveOrUpdate(one));
    }

}

