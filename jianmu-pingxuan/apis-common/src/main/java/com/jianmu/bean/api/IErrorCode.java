package com.jianmu.bean.api;

/**
 * 封装api的错误码
 *
 * @author lime
 */
public interface IErrorCode {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 获取消息
     *
     * @return 消息
     */
    String getInfo();
}
