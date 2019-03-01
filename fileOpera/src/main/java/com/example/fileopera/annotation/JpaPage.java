package com.example.fileopera.annotation;

import java.lang.annotation.*;

/**
 * @Author: Kayla, Ye
 * @Description: 自定义注解（用于后期拦截标志）
 * @Date:Created in 3:40 PM 3/1/2019
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaPage {
}
