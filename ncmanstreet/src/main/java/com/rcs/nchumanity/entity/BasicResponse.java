package com.rcs.nchumanity.entity;


import com.google.gson.Gson;

/**
 * 基本的响应模块的实现
 * <p>
 * 定义了基本的响应码和消息内容，
 * <p>
 * <p>
 * 请不要给该类修改枚举类型，在使用部分解析json库时，不能正常的序列化数据
 */
public class BasicResponse {


    public static final int ERROR = 0;

    /**
     * 没有这个用户
     * 也就是用户未注册
     */
    public static final int NOT_REGISTER = 1;

    /**
     * 用户被注册
     */
    public static final int REGISTED = 2;

    /**
     * 注册成功
     */
    public static final int REGISTED_SUCCESS = 3;

    /**
     * 登录成功
     */
    public static final int LOGIN_SUCCESS = 4;

    public static final int RESPONSE_SUCCESS = 200;
    public static final int CHANGE_PASSWORD_SUCCESS = 3;
    public static final int NOT_LOGIN = 401;
    public static final int PASSWORD_ERROR = 5;

    public static final String MESSAGE_OTHER = "用户未登入";


    public static final String NOT_REQUIRED_MESSAGE = "暂未达到要求";

    /**
     * 用户非登录除外的其他错误
     */
    public static final int OTHER = 401;


    public static final int NOT_REQUIRED = 202;

    /**
     * 验证码错误
     */
    public static final int VALIDATE_CODE_ERROR = 6;

    /**
     * 不能达到要求
     */
    public static final int NOT_REQUIRED_201 = 201;

    /**
     * 操作不允许
     */
    public static final int NOT_CANCEL = 501;

    /**
     * 重复报名
     */
    public static final int REPEAT_SIGN_UP = 207;

    /**
     * 不能报名
     */
    public static final int NOT_SIGNIN = 207;
    /**
     * 修改密码成功
     */
    public static final int CHANGE_PASSWORD_SUCCESS_11 = 11;

    public static final int NOT_REQUIRED_204 = 204;
    public static final int NOT_TRANSPORT = 301;
    public static final int TRANSPORT = 302;
    /**
     * 报名成功
     */
    public static int SignUpSuccess = 4;

    /**
     * 取消操作失败
     */
    public static int CANCEL_FAIL = 500;


    /**
     * code 代表的是响应码
     */
    public int code;

    /***
     * 消息提示
     */
    public String message;

    /**
     * 响应失败
     */
    public static final int RESPONSE_FAIL = 500;

    /**
     * 传输的数据对象
     */
    public Object object;

    public BasicResponse(int code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    @Override
    public String toString() {
        return "BasicResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
