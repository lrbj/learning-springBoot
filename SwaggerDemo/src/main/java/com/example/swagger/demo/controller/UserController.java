package com.example.swagger.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.swagger.demo.SwaggerdemoApplication;
import com.example.swagger.demo.entity.User;
import com.example.swagger.demo.repository.UserRepository;
import com.example.swagger.demo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 2:32 PM 1/15/2019
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PostMapping("/add")
    @ApiOperation(value = "addUser", notes = "添加数据")
    public int addUser(@RequestBody List<User> userList) {
        int nRet = -2;
        nRet = userService.saveListUser(userList);
        System.out.println("nRet = " + nRet);

        return nRet;
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "findAll", notes = "查找全部数据")
    public List<User> findAll() {
        return userService.findAll();
    }


    @PostMapping("/test")
    @ApiOperation(value = "test", notes = "测试")
    public String test() {

        String str = "{\"applyUserId\":\"393E7C3D-0A31-40CD-A567-EE5DFE8AB491\",\"applyUserName\":\"admin\",\"applyUserPhone\":13818181818,\"applyUserEmail\":\"cfis-xfqc@mail.foxconn.com\",\"unitParkId\":\"1\",\"unitParkName\":\"觀瀾\",\"unitBusinessGroupId\":\"2\",\"unitBusinessGroupName\":\"CAA-iPEBG\",\"unitBusinessDepartmentId\":\"12\",\"unitBusinessDepartmentName\":\"iPEG製造工程處11\",\"locationParkId\":\"418\",\"locationParkName\":\"龙华园区\",\"locationRegionId\":\"465\",\"locationRegionName\":\"B区\",\"locationBuildingId\":\"466\",\"locationBuildingName\":\"B0\",\"locationContent\":\"11\",\"locationFloor\":[{\"name\":\"2F\",\"id\":\"468\"},{\"name\":\"3F\",\"id\":\"469\"},{\"name\":\"1F\",\"id\":\"467\"}],\"locationQita\":null,\"interruptReasons\":[{\"reasonId\":1,\"reason\":\"现场改造\"}],\"interruptProjects\":[{\"projectId\":1,\"project\":\"火灾自动报警系统\",\"projectDescription\":\"\",\"locationBuildingId\":\"466\",\"locationBuildingName\":\"B0\",\"locationFloor\":[{\"name\":\"2F\",\"id\":\"468\"},{\"name\":\"3F\",\"id\":\"469\"},{\"name\":\"1F\",\"id\":\"467\"}]},{\"projectId\":5,\"project\":\"探测器\",\"projectDescription\":\"\",\"locationBuildingId\":\"466\",\"locationBuildingName\":\"B0\",\"locationFloor\":[{\"name\":\"2F\",\"id\":\"468\"},{\"name\":\"3F\",\"id\":\"469\"},{\"name\":\"1F\",\"id\":\"467\"}]},{\"projectId\":3,\"project\":\"警铃\",\"projectDescription\":\"\",\"locationBuildingId\":\"466\",\"locationBuildingName\":\"B0\"},{\"projectId\":8,\"project\":\"消火栓給水系統\",\"projectDescription\":\"\",\"locationBuildingId\":\"466\",\"locationBuildingName\":\"B0\",\"locationFloor\":[{\"name\":\"2F\",\"id\":\"468\"},{\"name\":\"3F\",\"id\":\"469\"},{\"name\":\"1F\",\"id\":\"467\"}]},{\"projectId\":4,\"project\":\"气体灭火系统\",\"projectDescription\":\"\",\"locationBuildingId\":\"466\",\"locationBuildingName\":\"B0\",\"locationFloor\":[{ \n" +
                "\"name\":\"2F\",\"id\":\"468\"},{\"name\":\"3F\",\"id\":\"469\"},{\"name\":\"1F\",\"id\":\"467\"}]}],\"approvers\":{\"interruptApplyAdd\":\"111\",\"interruptRadio\":\"A\",\"constructionDepartment\":{\"departmentName\":\"333333\",\"person\":{\"id\":\"ea7d1959-aea0-4ffc-8de6-ddf0e0db3b7d\",\"userName\":\"wly\"},\"cellPhone\":\"123122312313\",\"email\":\"test123@163.com\"},\"projectorganize\":{\"departmentName\":\"333333\",\"person\":{\"id\":\"ea7d1959-aea0-4ffc-8de6-ddf0e0db3b7d\",\"userName\":\"wly\"},\"cellPhone\":\"123122312313\",\"email\":\"test123@163.com\"},\"applicantDepart\":{\"person\":{\"id\":\"ea7d1959-aea0-4ffc-8de6-ddf0e0db3b7d\",\"userName\":\"wly\"},\"cellPhone\":\"123122312313\",\"email\":\"test123@163.com\"},\"fireAdmin\":{\"person\":{\"id\":\"ea7d1959-aea0-4ffc-8de6-ddf0e0db3b7d\",\"userName\":\"wly\"},\"cellPhone\":\"123122312313\",\"email\":\"test123@163.com\"}},\"interruptStartTime\":1570585734946,\"interruptEndTime\":1571536137800,\"interruptApplyAdd\":\"111\",\"constructionUnit\":\"333333\",\"interruptRadio\":\"A\"}";
        JSONObject jsonObject = JSON.parseObject(str);
        if (jsonObject.containsKey("interruptProjects") && CollectionUtils.isNotEmpty((Collection<?>) jsonObject.get("interruptProjects"))) {
            List<JSONObject> data = (List<JSONObject>) jsonObject.get("interruptProjects");
            for (JSONObject project : data) {
                String floorString = "";
                System.out.println("project： {}" + project);
                if (project.containsKey("locationFloor") && project.get("locationFloor") != null) {
                    System.out.println("11 -locationFloor: {}" + project.get("locationFloor"));
                    // TODO 存入数据库为空

                    floorString = project.get("locationFloor").toString();
                    System.out.println("33-locationFloor: {}" + floorString);
                    List<Map> data1 = (List<Map>) project.get("locationFloor");
                    System.out.println("22-locationFloor: {}" + data1);
                }
            }
        }
        return "hello world!";

    }


    boolean isPalindromic(String s, int i, int j) {

        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        String[] myArray = { "Apple", "Banana", "Orange" };
        List<String> myList = Arrays.asList(myArray);

        return true;
    }

    public String longestPalindrome(String s) {
        String result = null;
        if (s == null || s.length() <= 0) {
            return result;
        }
        int max = 0;
        int n = s.length();
        int i = 0;
        int j = 0;
        int startIndex = 0;
        int endIndex = n - 1;
        for (i = 0; i < n; i++) {
            for (j = n - 1; j > i; j--) {
                if (s.charAt(j) == s.charAt(i)) {
                    //判断是否是回文
                    if (isPalindromic(s, i, j)) {

                        if (max < (j - i + 1)) {
                            max = j - i + 1;
                            startIndex = i;
                            endIndex = j;
                            System.out.println("i" + i + "j" + j);
                        }
                        break;
                    }
                }
            }
        }

        if (max > 0) {
            result = s.substring(startIndex, endIndex);
        }
        return result;

    }

    public double myPow(double x, int n) {
       double inf = Double.NEGATIVE_INFINITY;


        if(n == 0){
            return 1;
        }

        if(n < 0){
            x = 1/x;
            n = -n;
        }
        double t = x;
        for(int i = 1; i < n; i++){
            t = t*x;
        }

        return t;
    }

}
