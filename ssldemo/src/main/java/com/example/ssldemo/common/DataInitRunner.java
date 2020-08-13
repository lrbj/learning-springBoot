package com.example.ssldemo.common;

import com.example.ssldemo.dto.JwtTokenDto;
import com.example.ssldemo.util.JwtTokenUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class DataInitRunner implements ApplicationRunner {

  public  static String jwtToken = new String();

  @Override
  public void run(ApplicationArguments args) {
    // 初始化缓存
    //todo 获取jwtToken
   // workorderService.deploy();
      long expTime  = 1912383621000l;
      String[] scope = {"demo"};
      JwtTokenDto jwtTokenDto = new JwtTokenDto("",scope,"",expTime,"test");
      Map<String,Object> map = BeanMap.create(jwtTokenDto);
      try {
          this.jwtToken = JwtTokenUtil.createJWT(UUID.randomUUID().toString(), "kayla","test",60*60*1000,map);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

}
