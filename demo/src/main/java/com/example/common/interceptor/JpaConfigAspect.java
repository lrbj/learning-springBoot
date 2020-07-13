package com.example.common.interceptor;

import com.example.common.annotation.SystemTypeSelect;
import com.example.common.annotation.TenantSelect;
import com.example.common.utils.EnvHolder;
import lombok.Getter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 拦截repository包中所有标注@TenantSelect以及@SystemTypeSelect的方法
 */
@Aspect
@Component
@Getter
public class JpaConfigAspect {

  private boolean tenantEnable;
  private String columnAlias;
  private boolean systemTypeEnable;
  private String systemTypeAlias;

  @Around("execution(* com.example.common.repository..*(..))")
  public Object tenantSelect(ProceedingJoinPoint joinPoint) throws Throwable {

    // 判定是否开启tenant条件过滤
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    TenantSelect tenantAnnotation = signature.getMethod().getAnnotation(TenantSelect.class);
    if (tenantAnnotation != null) {
      if (!EnvHolder.getHolder().getTenantId().equals("-1")) {
        this.tenantEnable = true;
        this.columnAlias = tenantAnnotation.columnAlias();
      }
    } else {
      this.tenantEnable = false;
    }

    SystemTypeSelect systemTypeAnnotation  = signature.getMethod().getAnnotation(SystemTypeSelect.class);
    if(systemTypeAnnotation != null){
      if(EnvHolder.getHolder().getEssSystem() != null){
        this.systemTypeEnable = true;
        this.systemTypeAlias = systemTypeAnnotation.columnAlias();
      }
    }else {
      this.systemTypeEnable = false;
    }
    // 执行
    Object result = joinPoint.proceed();

    this.tenantEnable = false;
    this.systemTypeEnable = false;
    return result;
  }

}
