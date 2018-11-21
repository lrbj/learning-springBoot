# activiti 6.0 + springboot
ativiti工作流框架，用于流程部署
环境：idea，并且安装插件actiBPM
## 1、配置环境依赖
pom.xml 中添加activiti依赖以及对应的数据库，实例中所用的数据库为postgre
```xml

<dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-spring-boot-starter-basic</artifactId>
            <version>6.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>

```

## 2、新建流程图bpmn
## 3、配置
``` yml
server:
  port: 8001
spring:
  datasource:
    url: jdbc:postgresql://127.0.0.1:5432/test
    username: postgres
    password: 123456
    driver-class-name: org.postgresql.Driver
  activiti:
    check-process-definitions: false
    process-definition-location-prefix: classpath:/processes/
    database-schema-update: true
    #不生成流程图png
    createDiagramOnDeploy: false
    #异步启动
    async-executor-activate: true

  jpa:
    properties:
      hibernate:
        temp:
          use_jdbc_metadata_defaults: false
```

## 4、生成数据库表格说明
*  Activiti的后台是有数据库的支持，所有的表都以ACT_开头。 第二部分是表示表的用途的两个字母标识。 用途也和服务的API对应。
*  ACT_RE_*: 'RE'表示repository。 这个前缀的表包含了流程定义和流程静态资源 （图片，规则，等等）。
*  ACT_RU_*: 'RU'表示runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。 Activiti只在流程实例执行过程中保存这些数据， 在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。
*  ACT_ID_*: 'ID'表示identity。 这些表包含身份信息，比如用户，组等等。
*  ACT_HI_*: 'HI'表示history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。
*  ACT_GE_*: 通用数据， 用于不同场景下，如存放资源文件。
## 5、备注
### 5.1关于版本问题
如果使用的是activiti6.0 ，而springboot为2.0及以上，则application文件上需要添加
```Java
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
```
完整文件如下
```Java
package com.example.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
}

```
### 5.2 关于springboot + activiti下启动过程
当程序启动Spring boot会基于自动配置原理给我们隐式的创建一个工作流引擎对象ProcessEngine，并把RuntimeService（控制流程运行时数据流转）
等核心服务注册到 Spring 容器中，我们只需要 依赖注入使用即可。在以下代码中体现
```java
 @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;
```
