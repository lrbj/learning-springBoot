package com.example.common.config;


import com.example.common.account.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanProvider implements ApplicationContextAware {

  private static ApplicationContext applicationContext;


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    if (BeanProvider.applicationContext == null) {
      BeanProvider.applicationContext = applicationContext;
    }
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static UserService getUserService() {
    UserService userService = null;
    userService = getApplicationContext().getBean(
        UserService.class);
    return userService;
  }

  public static <T> T getBean(Class<T> beanClass) {
    T bean = getApplicationContext().getBean(beanClass);
    return bean;
  }
}
