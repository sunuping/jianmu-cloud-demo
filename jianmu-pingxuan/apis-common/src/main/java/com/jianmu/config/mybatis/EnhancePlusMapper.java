package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface EnhancePlusMapper<T> extends BaseMapper<T> {

    /**
     * 增强批量新增
     */
    Integer enhanceBatchInsert(@Param("list") Collection<T> rows);

    /**
     * 增强批量更新
     */
    Integer enhanceBatchUpdate(@Param("list") Collection<T> rows);


    /**
     * 增强批量删除
     */
    Integer enhanceBatchDelete(@Param("list") Collection<String> rows);
}
