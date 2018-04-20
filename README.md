# ssmProject

Idea + maven (多个 module)

+ Spring + Spring MVC + MyBatis
+ WebSocket

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
