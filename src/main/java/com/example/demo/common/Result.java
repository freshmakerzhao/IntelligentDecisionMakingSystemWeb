package com.example.demo.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 * 将方法放回值类型进行统一，方便前端处理返回的数据，并能够在后端写好前端msg信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

//    无数据成功
    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

//    有数据成功
    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

//    有错误，自定义状态码code，和报错信息msg
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

//    仅报错“系统错误”
    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }

}
