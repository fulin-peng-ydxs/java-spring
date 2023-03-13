package plus.use;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class GeneratorDemo {


    public static void main(String[] args) {

        FastAutoGenerator.create(
                        "jdbc:mysql://127.0.0.1:3307/mybatis_plus? characterEncoding=utf-8&userSSL=false",
                        "root", "root")
                .globalConfig(builder -> {
                    builder.author("pengfulin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\java_git\\java-spring\\spring-integration\\spring-mybatis-plus\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("plus.generator") // 设置父包名
                            .moduleName("code") // 设置父包模块名
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.mapperXml, "D:\\java_git\\java-spring\\spring-integration\\spring-mybatis-plus\\src\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user"); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker
                //引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
