package com.example.demo.common;

public interface Constants {

    // 成功
    String CODE_200 = "200";

    // 客户端/认证/授权问题
    String CODE_400 = "400"; // 参数错误 (Bad Request)
    String CODE_401 = "401"; // 认证失败 (Unauthorized): Token 无效/过期/缺失
    String CODE_403 = "403"; // 授权失败 (Forbidden): 已登录但无权限访问
    String CODE_404 = "404"; // 资源未找到 (Not Found)

    // 服务器问题（对应 HTTP 5xx）
    String CODE_500 = "500"; // 系统错误 (Internal Server Error): 服务器异常、代码 Bug 等

    // 业务逻辑错误（细分业务问题）
    String CODE_601 = "601"; // 登录失败：用户名或密码错误
    String CODE_602 = "602"; // 业务冲突：数据已存在、重复操作 (如重复注册)
    String CODE_603 = "603"; // 业务校验失败：验证码错误、账号被禁用、年龄超出范围等

}
