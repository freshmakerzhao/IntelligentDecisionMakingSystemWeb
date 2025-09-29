package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.IRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;


    /**
     * @Description 获取权限列表
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/12 14:54
     */
    @GetMapping("/list")
    public Result findAllRole(){
        return Result.success(roleService.list());
    }


}
