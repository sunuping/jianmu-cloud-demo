package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.NoArgsConstructor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

@NoArgsConstructor
public class EnhanceBatchDelete extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>delete from " + tableInfo.getTableName() + " where id in(<foreach collection=\"list\" item=\"i\" separator=\",\">#{i}</foreach>)</script>";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        final String id = "enhanceBatchDelete";
        return this.addDeleteMappedStatement(mapperClass, id, sqlSource);
    }

}
