package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

public interface EnhanceService<T> extends IService<T> {

    Boolean add(T t);

    Boolean modify(T t);

    Boolean del(Serializable id);

    T get(Serializable id);

}
