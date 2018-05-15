package com.syc.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //MybatisGenerator.class.getResource("") 当前MybatisGenerator的目录
        //MybatisGenerator.class.getResource("/") 当前项目的目录
        //MybatisGenerator.class.getClassLoader().getResource(""); 当前项目的目录

        //指定 逆向工程配置文件
        File configFile = new File(MybatisGenerator.class.getResource("/generatorConfig.xml").getFile());
//        File configFile = new File("src/main/resources/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) {
        System.out.println("mybatis generator class main start");
        try {

            MybatisGenerator generator = new MybatisGenerator();
            generator.generator();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("mybatis generator class main end");
    }
}
