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
    public void testFreemarker() throws Exception {
        //1、创建配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_20);


        //2、获取模板路径
        configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
        //3、创建数据模型
        Map<String, String> map = new HashMap<>();
        map.put("classPath", "com.example.demo");
        map.put("className", "AutoCodeDemo");
        map.put("message", "hell world");

        //4、加载模板文件
        Template template = configuration.getTemplate("hello.ftl");

        //5、生成数据
        File file = new File(CLASS_PATH + "\\" + "AutoCodeDemo.java");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        //6、输出文件
        template.process(map, out);

    }
}
