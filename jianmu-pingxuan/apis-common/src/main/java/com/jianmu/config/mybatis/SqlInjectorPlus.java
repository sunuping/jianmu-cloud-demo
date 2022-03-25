package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

public class SqlInjectorPlus extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        //继承原来的通用方法
        List<AbstractMethod> methods = super.getMethodList(mapperClass, tableInfo);
        methods.add(new EnhanceBatchInsert());
        methods.add(new EnhanceBatchDelete());
        methods.add(new EnhanceBatchUpdate());
        return methods;
    }
}
