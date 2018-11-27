# SpringBoot 下 用excel 的解析和创建
## 1、添加依赖
pom.xml
```xml 
<dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>4.0.0</version>
        </dependency>

```
为了支持.xls 以及.xlsx两种版本的excel文件

## 2、
文件的解析和生成具体在代码中实现
