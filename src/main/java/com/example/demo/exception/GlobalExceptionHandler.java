package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法，返回一个Result的eroor对象给前台
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }

    // 针对所有未被捕获的 Exception 的处理器，防止程序崩溃
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleOtherException(Exception e) {
        // 打印堆栈信息，便于调试
        e.printStackTrace();
        return Result.error("500", "系统繁忙，请稍后再试");
    }

}
