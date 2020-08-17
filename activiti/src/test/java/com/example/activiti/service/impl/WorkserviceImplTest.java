package com.example.activiti.service.impl;

import com.example.activiti.service.Workservice;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:36 PM 11/19/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkserviceImplTest {

    @Autowired
    Workservice workservice;


    @Test
    public void deploy() {
    }

    @Test
    public void runProcess() {
    }

    @Test
    public void queryTask() {
    }

    @Test
    public void complieTask() {
    }

    @Test
    public void queryProcessDefination() {
    }

    @Test
    public void queryProcessInstanceState() {
    }

    @Test
    public void deleteProcessDefi() {
    }

    @Test
    public void queryHistoryProcInst() {
        workservice.queryHistoryProcInst();
    }

    @Test
    public void queryHistoryTask() throws JSONException {
//        String id = "10003";
//        workservice.queryHistoryTask(id);
        String str = "{\"comments\":\"123\",\"photos\":[],\"operator\":\" 作业人：1231 工号：123 邮箱：123 备注：123\"}";
        JSONObject jsonObject = new JSONObject(str);
        if (!jsonObject.isNull("comments")) {
            System.out.println("comments" + jsonObject.getString("comments"));
        }
    }
}