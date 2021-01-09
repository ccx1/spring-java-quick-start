package com.ccx.enums;

/**
 * 错误枚举类
 */


public enum HttpStatus {

    // 成功
    OK(200, "SUCCESS"),
    // 没有登录
    NO_LOGIN(0, "NO_LOGIN"),

    // --------  用户相关  -----------
    // 用户已存在错误,
    USER_AUTH_TOKEN_FAIL(4000, "用户token校验失败"),
    USER_AUTH_TOKEN_EXPIRED_FAIL(4001, "用户token已过期"),
    USER_EXIST_FAIL(4002, "用户已存在"),
    USER_NOT_EXIST_FAIL(4003, "用户不存在"),


    // 未知异常
    UN_KNOW_ERROR(400, "出现系统未知错误");


    private int code;

    private String msg;


    HttpStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
