package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.NoArgsConstructor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

@NoArgsConstructor
public class EnhanceBatchUpdate extends AbstractMethod {

    /**
     * update s_access_log
     * <trim prefix="set" suffixOverrides=",">
     * <if test="list.get(0).description!=null">
     * <trim prefix="description=case" suffix="end,">
     * <foreach collection="list" item="et">
     * <if test="et!=null"> when id=#{et.id} then #{et.description}</if>
     * </foreach>
     * </trim>
     * </if>
     * </trim>
     * where id in
     * <foreach collection="list" item="et" separator="," open="(" close=")">#{et.id}</foreach>
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, this.generateSql(tableInfo.getFieldList(),tableInfo), modelClass);
        final String id = "enhanceBatchUpdate";
        return this.addUpdateMappedStatement(mapperClass, modelClass, id, sqlSource);
    }

    private String generateSql(List<TableFieldInfo> fields, TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        fields.forEach(i -> sb.append("")
                .append("<if test=\"list.get(0).").append(i.getProperty()).append("!=null\">")
                .append("<trim prefix=\"")
                .append(i.getColumn())
                .append("=case\" suffix=\"end,\">")
                .append("<foreach collection=\"list\" item=\"et\">")
                .append("<if test=\"et!=null\">")
                .append(" when id=#{et.id} then #{et.").append(i.getProperty()).append("}")
                .append("</if>")
                .append("</foreach>")
                .append("</trim>")
                .append("</if>"));

        return "<script>update " + tableInfo.getTableName()
                + "<trim prefix=\"set\" suffixOverrides=\",\">"
                + sb
                + "</trim> where id in <foreach collection=\"list\" item=\"et\" separator=\",\" open=\"(\" close=\")\"> #{et.id} </foreach> </script>";
    }


}
