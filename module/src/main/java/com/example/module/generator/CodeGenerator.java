package com.example.module.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @author XRS
 * @date 2024-08-02 下午 1:35
 */
public class CodeGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true" +
                                "&characterEncoding=utf8&useSSL=false" +
                                "&serverTimezone=UTC&rewriteBatchedStatements=true&useAffectedRows=true",
                        "root",
                        "123456")
                .globalConfig(builder -> {
                    builder.author("野狗") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("C:\\Programming\\Java\\Game\\module\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.example.module") // 设置父包名
                                .moduleName("") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Programming\\Java\\Game\\module\\src\\main\\resources\\mapper")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("category") // 设置需要生成的表名
                                //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                                .serviceBuilder().formatServiceFileName("%sService") //去掉名字前的I
                                .entityBuilder().enableLombok().enableFileOverride()
                                //.controllerBuilder().enableFileOverride()
                                .serviceBuilder().enableFileOverride()
                                .mapperBuilder().enableFileOverride()
                )
                //模板的位置
                .templateConfig(
                        builder -> {
                            //在最前面加个/，以及删掉.ftl
                            builder.entity("/templates/entity.java")
                                    .mapper("/templates/mapper.java")
                                    //.controller("/templates/controller.java")
                                    //.service("/templates/service.java")
                                    .disable(TemplateType.CONTROLLER)
                                    .disable(TemplateType.SERVICE_IMPL)
                                    .disable(TemplateType.SERVICE)
                                    .xml("/templates/mapper.xml");
                        }
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
