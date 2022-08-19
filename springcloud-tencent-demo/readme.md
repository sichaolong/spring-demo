### springcloud-tentcent-demo 练习

官网：https://github.com/Tencent/spring-cloud-tencent

1、安装北极星，下载地址：https://github.com/polarismesh/polaris/releases/tag/v1.9.0; 

我选择在windows安装，下载polaris-standalone-release_v1.9.0.windows.amd64.zip的zip解压按照操作提示执行install.bat文件即可

2、新建父maven项目，在pom文件指定springboot、springcloud、springcloud-tentcent版本


```$xslt
 <dependencies>
           <!--spring boot -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-dependencies</artifactId>
               <version>2.3.12.RELEASE</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
           <!--spring cloud Hoxton.SR1-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>Hoxton.SR12</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
           <dependency>
               <groupId>com.tencent.cloud</groupId>
               <artifactId>spring-cloud-tencent-dependencies</artifactId>
               <version>1.6.0-Hoxton.SR12</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
```

3、新建maven子模块discovery，作为注册中心，导入相应的依赖

```$xslt
 <dependencies>
        <dependency>
            <groupId>com.tencent.cloud</groupId>
            <artifactId>spring-cloud-starter-tencent-polaris-discovery</artifactId>
        </dependency>
    </dependencies>
```
