package com.jianmu.config.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lime
 * 统一接口结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private int code;
    private String info;
    private T data;

    public static Result<String> ok() {
        return new Result<>(ResultCode.OK.getCode(), ResultCode.OK.getInfo(), null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.OK.getCode(), ResultCode.OK.getInfo(), data);
    }

    public static Result<String> fail() {
        return new Result<>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getInfo(), null);
    }

    public static Result<String> fail(String info) {
        return new Result<>(ResultCode.FAIL.getCode(), info, null);
    }

    /**
     * 参数验证失败
     */
    public static Result<String> paramValidationFail() {
        return new Result<>(ResultCode.PARAM_VALIDATION_FAIL.getCode(), ResultCode.FAIL.getInfo(), null);
    }

    /**
     * 未授权
     */
    public static Result<String> unauthorized() {
        return new Result<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getInfo(), null);
    }

    /**
     * 无权限
     */
    public static Result<String> forbidden() {
        return new Result<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getInfo(), null);
    }

    /**
     * 无权限
     */
    public static Result<String> signFail() {
        return new Result<>(ResultCode.SIGN_FAIL.getCode(), ResultCode.SIGN_FAIL.getInfo(), null);
    }


    @Override
    public String toString() {
        return "{\"code\": " + this.getCode() + ", \"info\": \"" + this.getInfo() + "\", \"data\": " + this.getData() + "}";
    }
}
