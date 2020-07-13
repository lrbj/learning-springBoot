package com.example.common.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.common.exception.BusinessException;
import com.example.common.utils.GoServiceResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class UserService {

  @Autowired
  UserApiProperties userApiProperties;

  @Autowired
  RestTemplate restTemplate;

  public User getCurrentUser() {
    User user = null;
    try {
      ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(userApiProperties.getGetProfile(), HttpMethod.GET, null, JSONObject.class);
      JSONObject responseBody = responseEntity.getBody();
      GoServiceResponseObject msgDTO = GoServiceResponseObject.build(responseBody);
      if (msgDTO.getCode().intValue() != 10010000) {
        log.error("query current user error: {}", msgDTO.getMsg());
        return null;
      }

      // 解析userInfo
      JSONObject jsonUserInfo = (JSONObject) JSONObject.toJSON(responseBody.get("userInfo"));
      user = jsonUserInfo.toJavaObject(User.class);

      // 解析role
      if (responseBody.get("roles") != null) {
        JSONObject jsonRoles = (JSONObject) JSONObject.toJSON(responseBody.get("roles"));
        HashMap<String, String> roleMap = new HashMap<>();
        for (Map.Entry<String, Object> roleEntry : jsonRoles.entrySet()) {
          roleMap.put(roleEntry.getKey(), roleEntry.getValue().toString());
        }
        user.setRoleInfo(roleMap);
      }

    } catch (Exception e) {
      log.error("unexpected query current user error: {}", e.getMessage());
      e.printStackTrace();
    } finally {
      return user;
    }
  }


  public User findByEid(String eid) {
    return findByCondition(UserSearchDTO.builder().eid(eid).build());
  }

  public User findByCondition(UserSearchDTO searchDTO) {
    User user = null;
    try {
      HttpEntity requestEntity = new HttpEntity(searchDTO);
      ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(userApiProperties.getGetDetail(), HttpMethod.POST, requestEntity, JSONObject.class);
      JSONObject responseBody = responseEntity.getBody();
      GoServiceResponseObject msgDTO = GoServiceResponseObject.build(responseBody);

      if (msgDTO.getCode().intValue() != 10010000) {
        log.error("query user detail error: {}", msgDTO.getMsg());
        return null;
      }

      // 解析userInfos
      JSONArray jsonUserInfos = (JSONArray) JSON.toJSON(responseBody.get("userInfos"));
      List<User> userList = jsonUserInfos.toJavaList(User.class);
      if (!userList.isEmpty()) {
        user = userList.get(0);
      }

    } catch (Exception e) {
      log.error("unexpected query user detail error: {}", e.getMessage());
      e.printStackTrace();
    } finally {
      return user;
    }
  }

}
