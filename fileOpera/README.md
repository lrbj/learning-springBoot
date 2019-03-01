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



## 3、拦截器

### 3.1 自定义拦截器



```
public class PageInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
    //在控制器执行前调用: 只拦截GET 以及 含有注解@JpaPage
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!"GET".equals(request.getMethod())){
           return true;
        }

        HandlerMethod method;
        //此处专门捕获异常，以防止程序在启动过程产生异常而停止
        try {
            method = (HandlerMethod) handler;
        } catch (Exception e) {
            logger.error("Page拦截出现异常");
            return true;
        }


        if(null == method.getMethodAnnotation(JpaPage.class)){
            return true;
        }

        //拦截提取请求参数
        PageSearch pageSearch = new PageSearch();
        //当参数无时，默认为分页查询
        pageSearch.setOpsType(request.getParameter("opsType")== null ? PageOperator.LIST:PageOperator.valueOf(request.getParameter("opsType")));

        // 分页操作
        if (PageOperator.LIST.equals(pageSearch.getOpsType())) {

            if (request.getParameter("ps") != null) {
                pageSearch.setPs(Integer.valueOf(request.getParameter("ps")));
            }
            if (request.getParameter("pi") != null) {
                Integer pi = Integer.valueOf(request.getParameter("pi")) - 1;
                pageSearch.setPi(pi < 0 ? 0 : pi);
            }
        }

        HashMap<String, Object> pageParameter = new HashMap<>();
        HashMap<String, Object> exportParameter = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            // 封装分页参数
            if (StringUtils.isNotEmpty(parameterName) && parameterName.startsWith("page_")) {
                pageParameter.put(parameterName.substring(5), request.getParameter(parameterName));
            }

            // 封装导出参数
            if (StringUtils.isNotEmpty(parameterName) && parameterName.startsWith("expo_")) {
                exportParameter.put(parameterName.substring(5), request.getParameter(parameterName));
            }
        }
        pageSearch.setPageParameter(pageParameter);
        pageSearch.setExportParameter(exportParameter);
        PageHolder.setHolder(pageSearch);
        return true;

    }


    //控制器执行完成后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    //整个请求结束后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

```

### 3.2 添加拦截器

```
@Configuration
public class webconfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
        registry.addInterceptor(new PageInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
```





## 4、封装JPA 分页查询

### 4.1 自定义一个注解

```
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaPage {
}

```

### 4.2 在拦截器中过滤GET请求以及含有@JpaPage的注解，具体如3、拦截器部分

### 4.3 自定义页面信息内容实体——用于整合输入参数

```
@Data
public class PageSearch {
    @ApiModelProperty(value = "搜索关键字")
    private String keyword;                            //关键字

    @ApiModelProperty(value = "页码，下标")
    private Integer pi = 0;                            //页码

    @ApiModelProperty(value = "一页显示的项目数")
    private Integer ps = 20;                           //一页显示的项目数

    @ApiModelProperty(value = "排序类型，默认降序")
    private OrderType orderType = OrderType.DESC;
    @ApiModelProperty(value = "排序字段")
    private String orderColumn = "id";

    @ApiModelProperty(value = "操作类型，分页or导出")
    private PageOperator opsType = PageOperator.LIST;

    @ApiModelProperty(value = "分页参数")
    private Map<String, Object> pageParameter;
    @ApiModelProperty(value = "导出参数")
    private Map<String, Object> exportParameter;
}
```



### 4.4 定义线程安全类存储Page信息——即全局变量

此类在拦截器中提取请求信息，保存在该类中，方便后续提取信息。——相当于信息存储的中转站

```
public class PageHolder {

    private static final ThreadLocal<PageSearch> HOLDER = new ThreadLocal<>();

    public static PageSearch getHolder() {
        return HOLDER.get();
    }

    public static void setHolder(PageSearch ps) {
        HOLDER.set(ps);
    }
}
```

### 4.5 获取具体参数以及封装查询条件——从PageHolder中提取

```
public class PageUtil {

    /**
     * 获取PageRequest请求
     *
     * @return
     */
    public static Pageable getPageRequest() {
        PageSearch pageSearch = PageHolder.getHolder();
        PageRequest pageRequest = PageRequest.of(pageSearch.getPi(), pageSearch.getPs(),
                Sort.Direction.valueOf(pageSearch.getOrderType().toString()), pageSearch.getOrderColumn());
        return pageRequest;
    }


    /**
     * 封装查询条件
     *
     * @return
     */
    public static Specification getSpecification() {
        Specification specification = (Specification) (root, query, criteriaBuilder) -> {
            Map<String, Object> pageParameter = PageHolder.getHolder().getPageParameter();
            if (pageParameter != null) {
                /**
                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
                 */
                for (Map.Entry<String, Object> entry : pageParameter.entrySet()) {
                    String[] key = entry.getKey().split("\\.");
                    Path p = null;
                    for (String k : key) {
                        if (p == null) {
                            p = root.get(k);
//                        Path path = root.get(k);
                        } else {
                            p = p.get(k);
                        }
                    }
                    if (p.getJavaType() == String.class) {
                        query = query.where(criteriaBuilder.like(p, "%" + entry.getValue() + "%"));
                    } else {
                        query = query.where(criteriaBuilder.equal(p, entry.getValue()));
                    }
                }
            }
            return null;
        };
        return specification;
    }



    //获取导出的excel数据
    public static ExcelData getexcelData(){
        PageSearch pageSearch = PageHolder.getHolder();
        Map<String, Object> pageParameters = pageSearch.getExportParameter();
        String where = "";
        ExcelData data = new ExcelData();
        for (Map.Entry<String, Object> entry : pageParameters.entrySet()) {
            if(entry.getKey().equals("title")){
                data.setTitle((List<String>)entry.getValue());
            }

            if(entry.getKey().equals("rows")){
                data.setRows(( List<List<Object>> )entry.getValue());
            }
            if(entry.getKey().equals("sheetName")){
                data.setSheetName((String)entry.getValue());
            }
            if(entry.getKey().equals("fileName")){
                data.setFileName((String)entry.getValue());
            }

        }

        return data;

    }
}
```

###4.6 Repository 增加继承 JpaSpecificationExecutor

```
public interface PeopleRepository extends JpaRepository<People, Long>,JpaSpecificationExecutor<People> {
}

```

### 4.7 封装输出信息

```
public class Pagination<T> {

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * <strong>属性名不要更改</strong>
     */
    private int totalCount;

    /**
     * <strong>属性名不要更改</strong>
     */
    private List<T> dataList;

    public Pagination() {

    }

    public Pagination(int totalCount, List<T> dataList) {
        this.totalCount = totalCount;
        this.dataList = dataList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}

```

### 4.8 调用实例

Controller

```
  @GetMapping
    @JpaPage
    @ApiOperation(value = "分页查询")
    public ResponseObject<Pagination<People>> findPage() {
        Pagination<People> pagination  = peopleService.findPage(PageUtil.getSpecification());
        return ResponseObject.success(pagination);
    }
```

PeopleService

```
    public Pagination<People> findPage(Specification<People> specification) {
        Page<People> page =  peopleRepository.findAll(specification, PageUtil.getPageRequest());
        return new Pagination<People>((int)page.getTotalElements(), page.getContent());
    }
```

![1551429899860](C:\Users\H299633\AppData\Roaming\Typora\typora-user-images\1551429899860.png)