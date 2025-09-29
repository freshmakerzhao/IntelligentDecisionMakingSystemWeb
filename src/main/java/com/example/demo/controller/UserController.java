package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.IRoleService;
import com.example.demo.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IRoleService roleService;
    @Resource
    private IUserService userService;

    // RequestBody将前端的json数据转化成java对象
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
//        StrUtil是hutool提供的工具类，isBlank校验字符串是否为空
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        userDTO.setRid(1);
        userDTO.setRname("level1");
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
//        返回注册用户的user对象
        return  Result.success(userService.register(userDTO));
    }

    // 查询所有权限
    @GetMapping("/role")
    public Result findAllRole(){
        return Result.success(roleService.list());
    }

    // 根据用户id查询数据
    @GetMapping("/findOneById/{id}")
    public Result findOneById(@PathVariable Integer id){
        return Result.success(userService.getById(id));
    }

    // 新增和修改
    @PostMapping("/post")
    public Result save(@RequestBody User user) {
        // 在service中使用mybatis新增或更新
        return Result.success(userService.saveOrUpdate(user));
    }

    // 查询所有用户数据
    @GetMapping("/")
    public Result findAll(){
        return Result.success(userService.list());
    }

    // 分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String nickname) {

//        IPage是mybatis-plus的一种结构，存储页码和每页容量
        IPage<User> page = new Page<>(pageNum, pageSize);
//        封装好的操作类
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid",2); // 只要学生用户
//        不为空就搜索
        if (!"".equals(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
//        倒叙，这样新增的信息就在前面了
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(page, queryWrapper));
    }

    // ======================== 微信小程序 ===================================

    /**
     * @Description 微信小程序获取用户信息
     * @param: nickname 昵称
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/8 12:19
     */
    @GetMapping("/wx/findOneUser")
    public Result findOneUser(@RequestParam String nickname) {
        // 根据nickname获取用户实体
        User user = userService.getOne(new QueryWrapper<User>().eq("nickname",nickname));
        if(user == null){
            // 不存在则新增
            User newOne = new User();
            newOne.setNickname(nickname);
            newOne.setRid(3);
            newOne.setRname("level3");
            userService.saveOrUpdate(newOne);
            return Result.success(newOne);
        }else{
            // 存在则直接返回
            return Result.success(user);
        }
    }

    /**
     * @Description 养殖户修改和注册
     * @param: id 修改时传id，否则为空
     * @param: nickname 昵称
     * @param: name 真实姓名
     * @param: idcard 身份证号
     * @param: age 年龄
     * @param: phone 手机号
     * @return: com.example.demo.common.Result
     * @Author author
     * @Date 2023/4/17 14:47
     */
    @PostMapping("/wx/postUser")
    public Result postUser(@RequestParam(defaultValue = "-1") Integer id,
                           @RequestParam(defaultValue = "") String nickname,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String idcard,
                           @RequestParam(defaultValue = "") String sex,
                           @RequestParam(defaultValue = "-1") Integer age,
                           @RequestParam(defaultValue = "") String phone) {
        if (id == -1){
            return Result.error("500","用户id为空");
        }
        User user = new User();
        user.setId(id);
        user.setNickname(nickname);
        user.setName(name);
        user.setSex(sex);
        user.setIdcard(idcard);
        if (age != -1){
            user.setAge(age);
        }
        user.setPhone(phone);
        user.setRid(3);
        // 更新
        userService.saveOrUpdate(user);
        // 返回更新后的数据
        return Result.success(user);
    }
}
