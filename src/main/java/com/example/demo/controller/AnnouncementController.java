package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Announcement;
import com.example.demo.service.IAnnouncementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Resource
    private IAnnouncementService announcementService;

    // ============================== announcement ============================

    /**
     * @Description 根据id删除公告
     * @param: id
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/13 18:22
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(announcementService.removeById(id));
    }

    /**
     * @Description 批量删除公告
     * @param: ids
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/13 18:23
     */
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) { // [1,2,3]
        return Result.success(announcementService.removeByIds(ids));
    }

    /**
     * @Description 公告分页查询
     * @param: pageNum
     * @param: pageSize
     * @param: uid
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/13 18:23
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "-1") Integer uid) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<Announcement> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
//        不为空就搜索
        if (uid != -1) {
            queryWrapper.eq("uid", uid);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(announcementService.page(page, queryWrapper));
    }

    /**
     * @Description 公告新增和修改
     * @param: announcement
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/13 18:23
     */
    @PostMapping("/post")
    public Result save(@RequestBody Announcement announcement) {
        // 在service中使用mybatis新增或更新
        return Result.success(announcementService.saveOrUpdate(announcement));
    }

    /**
     * @Description 获取最新公告
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/13 18:24
     */
    @GetMapping("/one")
    public Result getOne() {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(announcementService.getOne(queryWrapper,false));
    }

    // ==================================== 微信小程序 =======================================

    /**
     * @Description 获取历史公告
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/13 18:24
     */
    @GetMapping("/wx/getList")
    public Result getList() {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(announcementService.list(queryWrapper));
    }

}
