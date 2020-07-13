package com.example.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  SystemTypeSelect {
    /**
     * 系统类型字段名
     * @return
     */
    String columnAlias() default "system_type";
}
