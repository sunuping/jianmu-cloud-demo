package com.jianmu.dubbo.api.impl;

import com.jianmu.dubbo.api.AuthDubboApi;
import com.jianmu.dubbo.api.VoteDubboApi;
import com.jianmu.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class VoteDubboApiImpl implements VoteDubboApi {
    @DubboReference(check = false)
    private AuthDubboApi authDubboApi;


    @Override
    public String print(String name) {
        log.debug(name);
        throw new ErrorException("aaa");
//        return name;
    }
}
