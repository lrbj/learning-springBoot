# SpringBoot 下文件的解析和创建
## 1、excel文件
### 1.1、使用的是PoI 框架，添加以下依赖
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

## 2、csv 文件的读写
### 2.1 csv 文件编码格式为utf-8， 可以用excel打开，可视为特殊格式的文本文件，每行中的列以逗号隔开
不需要添加任何依赖
文件的写可以视为普通文本写入。
