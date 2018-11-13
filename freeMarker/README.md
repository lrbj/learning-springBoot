# springboot学习（一）————整合FreeMarker
freeMarker是一款模板引擎，基于模板文件生成其他文件
在spring boot中使用过程如下：

## 1、新建一个maven工程，导入FreeMarker jar包

在pom.xml中加入
```xml
<dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
        </dependency>
```
## 2、创建目录templates， 并且创建模板文件hello.ftl
```ftl
package ${classPath};

public class ${className} {

    public static void main(String[] args) {
        System.out.println("${message}");
    }

}
```

## 3、创建运行FreeMarker模板引擎的测试文件 FreeMarkerTest.java文件
文件内容如下
```Java
package com.example.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 4:01 PM 11/13/2018
 */
public class FreeMarkerTest {
    //模板路径
    private static final String TEMPLATE_PATH = "D:\\learn\\javaweb\\freeMarker\\demo\\src\\main\\resources\\templates";
    //要生成的类路径
    private static final String CLASS_PATH = "D:\\learn\\javaweb\\freeMarker\\demo\\src\\main\\java\\com\\example\\demo";

    @Test
    public  void testFreemarker() throws Exception {
        //1、创建配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_20);


        //2、获取模板路径
        configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
        //3、创建数据模型
        Map<String,String> map = new HashMap<>();
        map.put("classPath","com.example.demo");
        map.put("className","AutoCodeDemo" );
        map.put("message","hell world");

        //4、加载模板文件
        Template template = configuration.getTemplate("hello.ftl");

        //5、生成数据
        File file = new File(CLASS_PATH+"\\"+"AutoCodeDemo.java");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        //6、输出文件
        template.process(map, out);

    }
}

```

## 4、刷新项目，生成指定文件
```java
package com.example.demo;

public class AutoCodeDemo {

    public static void main(String[] args) {
        System.out.println("hell world");
    }

}

```
