package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
@NoArgsConstructor
public class EnhanceBatchInsert extends AbstractMethod {

    @Setter
    @Accessors(chain = true)
    private Predicate<TableFieldInfo> predicate;

    /**
     * <script>
     * INSERT INTO s_user
     * (<if test="list.get(0).id!=null">id,</if>username,nickname)
     * VALUES
     * <foreach collection="list" item="et" separator=",">
     * (<if test="list.get(0).id!=null">#{et.id},</if>#{et.username},#{et.nickname})
     * </foreach>
     * </script>
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        final String id = "enhanceBatchInsert";
        if (tableInfo.havePK()) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(id, tableInfo, builderAssistant);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, this.generateSql(tableInfo.getFieldList(), tableInfo), modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, id, sqlSource, keyGenerator, keyProperty, keyColumn);

    }


    private String generateSql(List<TableFieldInfo> fields, TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>insert into ").append(tableInfo.getTableName()).append("(<trim  suffixOverrides=\",\"><if test=\"list.get(0).id!=null\">id,</if>");
        fields.forEach(i -> {
            sb.append("<if test=\"list.get(0).").append(i.getProperty()).append("!=null\">").append(i.getColumn()).append(",</if>");
        });
        sb.append("</trim>) values <foreach collection=\"list\" item=\"et\" separator=\",\">(<trim  suffixOverrides=\",\"><if test=\"list.get(0).id!=null\">#{et.id},</if>");
        fields.forEach(i -> {
            sb.append("<if test=\"list.get(0).").append(i.getProperty()).append("!=null\">#{et.").append(i.getProperty()).append("},</if>");
        });
        sb.append("</trim>) </foreach></script>");
        return sb.toString();
    }

}
