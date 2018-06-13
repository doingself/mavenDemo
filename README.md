# ssmProject

Idea + maven (多个 module)

+ Spring + Spring MVC + MyBatis
+ WebSocket
+ Mybatis Generator
+ redis
+ shiro
+ 二维码

+ ExtJS 集成失败

## 项目搭建

### 创建 Maven 项目

![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-0.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-1.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-2.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-3.png)

### 添加 module

#### 使用方式一 添加 ssmArtifact-core

![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-4.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-5.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-6.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-7.png)

#### 使用方式二 添加 ssmArtifact-web

只有 web 勾选 `Create from Archetype`, 并选择 `maven-archetype-webapp`

![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-8.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-9.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-10.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-11.png)

### 关联 module

![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-12.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-15.png)

### 其他设置

+ 删除 `ssmProject` 的 `src`
+ 给 `web` 添加 `Java`

![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-13.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180419-14.png)

### 集成 ExtJS (集成失败😂😂😂)

1. 样式比较适合做后端前台，风格统一，组件完备，功能强大
2. 几乎不用写样式，直接调用组件就可以了

ExtJS的版本繁多，本文收集了ExtJS各个版本的下载链接，包括官网和非官网的，以及各种汉化版api，欢迎大家下载分享。

+ ExtJS最新版下载链接：http://www.sencha.com/products/ExtJS/download/
+ ExtJS 4.2.1 下载链接：http://cdn.sencha.com/ext/gpl/ext-4.2.1-gpl.zip
+ ExtJS 4.0.7 下载链接：http://cdn.sencha.io/ext-4.0.7-gpl.zip
+ ExtJS 3.4.0 下载链接：http://cdn.sencha.com/ext/gpl/ext-3.4.1.1-gpl.zip
+ ExtJS 2.3.0 下载链接：http://dev.sencha.com/deploy/ext-2.3.0.zip

一般历史项目很多用的是2.2.x的版本，而且没有版权问题。（2.2.3相对稳定，但其实bug也不少）  
3.x比起2.x改进很多，包括稳定性上的问题，但需要注意版权  
4.x版本是一个飞跃式的版本，整个架构都更完善了，MVC，按需加载，plugin机制，components架构等都很不错。  
4.0的时候侧重于底层架构，性能不行，后面的4.1和4.2的changelog都说大幅改进性能

#### 准备工作

+ [DOWNLOAD GPL VERSION OF SENCHA EXT JS](https://www.sencha.com/legal/gpl/)
+ [Download Sencha Cmd](https://www.sencha.com/products/ExtJS/cmd-download/)

#### 创建 ExtJS 项目

+ 解压 `ext-6.2.0-gpl.zip`
+ 解压 `SenchaCmd-6.2.2-osx-no_jre.app.zip` 并安装
+ 创建 `ssmProject` 工程 `sencha -sdk /Users/syc/Documents/ext-6.2.0 generate app ssmProject /Users/syc/Documents/workspace/ssmProject`

`export PATH=${PATH}:/Users/syc/bin/Sencha/Cmd/6.2.2.36`

```
bogon:IdeaWorkspace syc$ sencha -sdk ext-6.2.0 generate app ssmProject ssmProject
Sencha Cmd v6.2.2.36
[WRN] the following remote package repository directories did not initialize properly :
[WRN] 	 - /Users/syc/bin/Sencha/Cmd/repo/.sencha
[WRN] 	 - /Users/syc/bin/Sencha/Cmd/repo/pkgs
[ERR] javax/xml/bind/DatatypeConverter
[ERR]
The application was last modified by an older version of Sencha Cmd (6.2.0.103).
Running "sencha package upgrade" may resolve the error described above.
```

#### TODO

在网上找前端框架的资料, 发现现在已经基本不用 ExtJS 了
我个人还是挺习惯 ExtJS 的, 但是此次未能成功集成😂😂😂

## ssm 搭建

+ 完善目录结构(controller / service / dao / model)
+ 使用 maven 引入 ssm/数据库等各种 jar 包

## ssm 配置

+ `log4j.properties`
+ `jdbc.properties`
+ `mapping/*.xml`
+ `spring-mvc.xml`
+ `spring-mybatis.xml`
+ `web.xml`

## 部署 Tomcat

+ `war` 将WEB工程以包的形式上传到服务器
	发布模式，这是先打成war包，再发布
+ `war exploded` 将WEB工程以当前文件夹的位置关系上传到服务器
	直接把文件夹、jsp页面 、classes等等移到Tomcat 部署文件夹里面，进行加载部署。因此这种方式支持热部署，一般在开发的时候也是用这种方式。

![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180420-0.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180420-1.png)
![image](https://github.com/doingself/mavenDemo/blob/master/images/ssmProject/QQ20180420-2.png)

## ssmProject 集成 WebSocket

spring4.0以后加入了对websocket技术的支持
参考 `package com.syc.websocket`

+ 添加 jar 包
+ 实现 `HandshakeInterceptor`
+ 实现 `WebSocketHandler`
+ 继承 `WebMvcConfigurerAdapter` 实现 `WebSocketConfigurer`
+ 添加 spring 配置

## ssmProject 集成 MyBatis Generator

`MyBatis Generator` (MBG) 是一个Mybatis的代码生成器，它可以帮助我们根据数据库中表的设计生成对应的 `实体类`，`xml Mapper`文件，接口以及帮助类(也就是我们可以借助该类来进行简单的CRUD操作)，这样就避免了我们每使用到一张表的数据就需要手动去创建对应的类和xml文件，这就帮我们节约了大量的时间去开发和业务逻辑有关的功能，但是如果对联合查询和存储过程您仍然需要手写SQL和对象。

1. maven 配置
```xml
    <dependencies>
		<dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.5</version>
        </dependency>

        ...
    </dependencies>
    <build>
        <finalName>ssmArtifact-web</finalName>

        <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
            <plugins>

                <plugin>
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-maven-plugin</artifactId>
                    <version>1.3.2</version>
                    <configuration>
                        <verbose>true</verbose>
                        <overwrite>true</overwrite>
                    </configuration>
                </plugin>

                ...
            </plugins>
        </pluginManagement>
    </build>
```
2. 配置 generatorConfig.xml

	参考 [ssmProject/ssmArtifact-web/generatorConfig.xml](https://github.com/doingself/mavenDemo/blob/master/ssmProject/ssmArtifact-web/src/main/resources/generatorConfig.xml) 配置

3. 运行
```java
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        //指定 逆向工程配置文件
        File configFile = new File(MybatisGenerator.class.getResource("/generatorConfig.xml").getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            myBatisGenerator.generate(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```


## ssmProject 集成 Redis

1. 添加 maven 依赖
    + `spring-data-redis` spring-redis实现
    + `jedis` redis客户端
    + `mybatis-ehcache` Ehcache实现,用于参考
2. 添加 `redis.properties` 文件, 配置 Redis 参数
3. 添加 `spring-redis.xml` 配置文件
4. 在 `web.xml` 中 `contextConfigLocation` 进行配置

## ssmProject 集成 shiro

1. 配置 maven 依赖
2. 配置 `spring-shiro.xml`
3. 实现类
4. 在 `web.xml` 配置 `DelegatingFilterProxy`

## ssmProject 集成 二维码

1. 配置 maven 依赖 (com.google.zxing)
2. 参考 `QRCodeController` 类

# sycProject

Idea + Maven 多 module 工程, module 间相互依赖的简单 Java Web Demo

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-0%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-1%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-2%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-3%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-4%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-5%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-6%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-7%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-8%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-9%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-10%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-11%402x.png)

![image](https://github.com/doingself/mavenDemo/blob/master/images/sycProject/QQ20171218-12%402x.png)

## 鸣谢

+ 基于Maven+SSM整合shiro+Redis实现后台管理项目 https://www.cnblogs.com/maixianyu8888/p/8302151.html
+ ssm整合Redis https://cloud.tencent.com/developer/article/1019826
+ Mybatis Generator自动生成 https://blog.csdn.net/niqinge/article/details/79280204
+ 打包时mapper.xml文件打不进war包 https://www.cnblogs.com/ANCAN-RAY/p/7009258.html
+ 二维码 https://blog.csdn.net/zgliang88/article/details/54617795