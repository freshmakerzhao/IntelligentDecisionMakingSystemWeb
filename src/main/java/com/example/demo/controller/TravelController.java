package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Travel;
import com.example.demo.entity.User;
import com.example.demo.service.ITravelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 
 * @Author author
 * @Date 2023-05-03
 */
@RestController
@RequestMapping("/travel")
public class TravelController {

    @Resource
    private ITravelService travelService;

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String userNickname,
                           @RequestParam(defaultValue = "") String fromAddress,
                           @RequestParam(defaultValue = "") String toAddress) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<Travel> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<Travel> queryWrapper = new QueryWrapper<>();
//        不为空就搜索
        if (!"".equals(userNickname)) {
            queryWrapper.like("user_nickname", userNickname);
        }
        if (!"".equals(fromAddress)) {
            queryWrapper.like("from_address", fromAddress);
        }
        if (!"".equals(toAddress)) {
            queryWrapper.like("to_address", toAddress);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(travelService.page(page, queryWrapper));
    }

    /**
     * @Description 使帖子失效
     * @param: id 帖子id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/16 23:19
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
//        travelService.update(
//                new UpdateWrapper<Travel>()
//                        .eq("id",id)
//                        .set("is_expire",1)
//        );
        travelService.removeById(id);
        return Result.success();
    }

//    ============================= 小程序 =============================

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/wx/getTravelPage")
    public Result getTravelPage(
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String searchWord,
                           @RequestParam(defaultValue = "-1") Integer userId) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<Travel> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<Travel> queryWrapper = new QueryWrapper<>();
        // 搜索

        // 属于个人的帖子
        if (userId != -1){
            queryWrapper.eq("user_id",userId);
            if (!"".equals(searchWord)){
                queryWrapper.and(queryWrapper1 -> queryWrapper1.
                        or().like("from_address",searchWord).
                        or().like("to_address",searchWord));
            }
        } else {
            if (!"".equals(searchWord)){
                queryWrapper.or().like("from_address",searchWord);
                queryWrapper.or().like("to_address",searchWord);
            }
        }
        // 倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(travelService.page(page, queryWrapper));
    }


    /**
     * @Description 根据id找到帖子详情信息
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 13:13
     */
    @GetMapping("/wx/getTravelInfoById")
    public Result getTravelInfoById(@RequestParam Integer id) {
        return Result.success(travelService.getById(id));
    }


    /**
     * @Description 根据帖子id删除
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/6/4 17:10
     */
    @GetMapping("/wx/delTravelInfoById")
    public Result delTravelInfoById(@RequestParam Integer id) {
        return Result.success(travelService.removeById(id));
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
        return Result.success(travelService.update(new UpdateWrapper<Travel>().set("browse_nums",browseNums+1).eq("id",id)));
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
        return Result.success(travelService.update(new UpdateWrapper<Travel>().set("like_nums",likeNums+1).eq("id",id)));
    }

    /**
     * @Description 新增帖子
     * @param: one
     * @return: com.example.demo.common.Result
     * @Author ZhaoShuai
     * @Date 2023/5/27 17:06
     */
    @PostMapping("/wx/postTravel")
    public Result postTravel(@RequestBody Travel one) {
        // 返回更新后的数据
        return Result.success(travelService.saveOrUpdate(one));
    }
}

