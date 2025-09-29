package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.LostAndFound;
import com.example.demo.entity.SchoolKeeping;
import com.example.demo.entity.LostAndFound;
import com.example.demo.service.ILostAndFoundService;
import com.example.demo.service.ISchoolKeepingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @Author author
 * @Date 2023-05-03
 */
@RestController
@RequestMapping("/lostAndFound")
public class LostAndFoundController {

    @Resource
    private ILostAndFoundService lostAndFoundService;

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String userNickname,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String content) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<LostAndFound> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<LostAndFound> queryWrapper = new QueryWrapper<>();
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
        return Result.success(lostAndFoundService.page(page, queryWrapper));
    }

    /**
     * @Description 使帖子失效
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/17 22:41
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        lostAndFoundService.update(
                new UpdateWrapper<LostAndFound>()
                        .eq("id",id)
                        .set("is_expire",1)
        );
        return Result.success();
    }


//    ============================= 小程序 =============================

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/wx/getLostAndFoundPage")
    public Result getLostAndFoundPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String currentCategory,
            @RequestParam(defaultValue = "-1") Integer userId) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<LostAndFound> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<LostAndFound> queryWrapper = new QueryWrapper<>();

        // 属于个人的帖子
        if (userId != -1){
            queryWrapper.eq("user_id",userId);
        }
        if (!"".equals(currentCategory)){
            // 看哪个类别的帖子
            queryWrapper.like("publish_category",currentCategory);
        }

//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(lostAndFoundService.page(page, queryWrapper));
    }



    /**
     * @Description 根据id找到帖子详情信息
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 13:13
     */
    @GetMapping("/wx/getLostAndFoundInfoById")
    public Result getLostAndFoundInfoById(@RequestParam Integer id) {
        return Result.success(lostAndFoundService.getById(id));
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
        return Result.success(lostAndFoundService.update(new UpdateWrapper<LostAndFound>().set("browse_nums",browseNums+1).eq("id",id)));
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
        return Result.success(lostAndFoundService.update(new UpdateWrapper<LostAndFound>().set("like_nums",likeNums+1).eq("id",id)));
    }

    /**
     * @Description 新增帖子
     * @param: one
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 17:06
     */
    @PostMapping("/wx/postLostAndFound")
    public Result postLostAndFound(@RequestBody LostAndFound one) {
        // 返回更新后的数据
        return Result.success(lostAndFoundService.saveOrUpdate(one));
    }
}

