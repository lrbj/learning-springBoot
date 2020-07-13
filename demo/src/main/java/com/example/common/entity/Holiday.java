package com.example.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "wo_holiday", indexes = {@Index(name = "IDX_HOLIDAY_NAME_TENANT", columnList = "name,tenantId", unique = true)})
public class Holiday extends BaseEntity<Holiday> {


    String name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    Timestamp startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    Timestamp endTime;

    private Integer type = 0;  // 类型，0：节假日，1：调休

    private String tenantId;//多租户对应的Holiday
}
