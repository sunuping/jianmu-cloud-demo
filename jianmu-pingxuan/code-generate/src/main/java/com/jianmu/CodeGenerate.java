package com.jianmu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerate {
    public static void main(String[] args) {
        System.out.println(NamingStrategy.underlineToCamel("s_access_log"));
        generate();

    }

    private static void generate() {
        String[] tables = new String[]{
                "s_access_log",
//                "s_login_log",
//                "s_permission",
//                "s_role",
//                "s_role_permission",
//                "s_user",
//                "s_user_role",
//                "s_config"
        };
        //分组
        final String packageGroup = ".system";
        final String xmlGroup = "\\system";

        //输出位置
        final String userDir = System.getProperty("user.dir");
        final String outputDir = userDir + "\\jianmu-pingxuan\\code-generate\\src\\main\\java\\";
        AutoGenerator generator = new AutoGenerator(new DataSourceConfig
                .Builder("jdbc:mysql://127.0.0.1:3306/db_jianmu_pingxuan", "root", "root")
                .schema("db_jianmu_pingxuan")
                .build())
                //包名配置
                .packageInfo(new PackageConfig.Builder().parent("com.jianmu")
                        .entity("model" + packageGroup)
                        .service("service.i" + packageGroup)
                        .serviceImpl("service.impl" + packageGroup)
                        .mapper("mapper" + packageGroup)
                        .xml("mapper" + packageGroup)
                        .controller("api" + packageGroup)
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, userDir + "\\jianmu-pingxuan\\code-generate\\src\\main\\resources\\mapper" + xmlGroup)).build())
                //策略配置
                .strategy(new StrategyConfig.Builder()
                        // 设置需要生成的表名
                        .addInclude(tables)
                        //增加过滤表前缀
                        .addTablePrefix("s_", "t_")
                        .entityBuilder().enableLombok().enableRemoveIsPrefix().enableActiveRecord().idType(IdType.AUTO)
                        .mapperBuilder().build()
                        .serviceBuilder().formatServiceFileName("%sService").build()
                        //控制器配置
                        .controllerBuilder().formatFileName("%sApi").enableRestStyle().enableHyphenStyle().build())
                //全局配置
                .global(new GlobalConfig.Builder().disableOpenDir().outputDir(outputDir).author("kong").fileOverride().enableSwagger().build())
                .template(new TemplateConfig.Builder().disable(TemplateType.ENTITY)
                        .entity("/templates/entity.java")
                        .service("/templates/service.java")
                        .serviceImpl("/templates/serviceImpl.java")
                        .mapper("/templates/mapper.java")
                        .mapperXml("/templates/mapper.xml")
                        .controller("/templates/controller.java").build())
                .injection(new InjectionConfig.Builder().beforeOutputFile((t, m) -> {
                    System.out.println(t.getEntityName());
                    System.out.println(m);
                }).build());


        generator.execute(new FreemarkerTemplateEngine());
    }
}
