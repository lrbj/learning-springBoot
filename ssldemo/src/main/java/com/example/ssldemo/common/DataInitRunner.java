package com.example.ssldemo.common;

import com.example.ssldemo.dto.JwtTokenDto;
import com.example.ssldemo.server.SSLServer;
import com.example.ssldemo.util.JwtTokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class DataInitRunner implements ApplicationRunner {

  public  static String jwtToken = new String();
  @Autowired
  SSLServer sslServer;

  @Override
  public void run(ApplicationArguments args) {
    // 初始化缓存
    //todo 获取jwtToken
   // workorderService.deploy();
      long expTime  = 1912383621000l;
     List<String> scope = new ArrayList<>();
     scope.add("demo");
      JwtTokenDto jwtTokenDto = new JwtTokenDto("",scope,"",expTime,"ess-polling");
      Map<String,Object> map = BeanMap.create(jwtTokenDto);
      try {
          this.jwtToken = sslServer.createJWT(UUID.randomUUID().toString(), "kayla",SignatureAlgorithm.RS256,60*60*1000,map);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

}
