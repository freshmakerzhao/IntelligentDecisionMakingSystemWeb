package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Constants;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.example.demo.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 * UserServiceImpl实现了IUserService中我们自己定义的login方法
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

//    根据传入的账号密码判断用户是否唯一存在
    private User getUserInfo(UserDTO userDTO) {
        // 使用QueryWrapper操作类搜索数据库，判断用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        // 处理异常情况
        try {
//            getOne获取数据，如果超过一条就抛异常
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_601, "用户名或密码错误");
        }
        return one;
    }

// 登录业务
    @Override
    public UserDTO login(UserDTO userDTO) {
//        获取对应用户名和密码的账号信息
        User one = getUserInfo(userDTO);
//        如果获取到
        if(one != null){
//                将获取到的用户信息中所需要的数据存放到userDTO里，返回到前台
            BeanUtil.copyProperties(one,userDTO,true);
//            设置Token
            String token = TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else{
//        如果没获取到
//                抛出异常时，由GlobalExceptionHandler捕获，并包装成Result返回给前台
            throw new ServiceException(Constants.CODE_601, "用户名或密码错误");
        }
    }

//    注册业务
    @Override
    public User register(UserDTO userDTO) {
//        判断用户是否存在
        User one = getUserInfo(userDTO);
//        不存在就可以注册
        if(one == null){
//            将注册信息存入user对象，通过save写入数据库
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);
        }else{
//            用户已存在时
            throw new ServiceException(Constants.CODE_602, "用户已存在");
        }
        return one;
    }
}
