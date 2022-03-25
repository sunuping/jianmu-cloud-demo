package com.jianmu.config.response;

/**
 * 枚举了一些常用API操作码
 *
 * @author lime
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作成功
     */
    OK(1, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(0, "操作失败"),
    /**
     * 参数检验失败
     */
    PARAM_VALIDATION_FAIL(-1001, "参数检验失败"),
    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(-1002, "暂未登录或token已经过期"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(-1003, "没有相关权限"),
    /**
     * 签名失败
     */
    SIGN_FAIL(-1004, "签名失败"),
    /**
     * 请不要频繁操作
     */
    REPEAT_SUBMIT(-1005, "请不要频繁操作"),
    /**
     * 异常提示消息
     */
    MSG_EXCEPTION(-1006, "异常提示消息"),

    /**
     * 请求超时
     */
    REQUEST_TIME_OUT(-1007, "请求超时");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 消息
     */
    private final String info;

    ResultCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getInfo() {
        return info;
    }
}
