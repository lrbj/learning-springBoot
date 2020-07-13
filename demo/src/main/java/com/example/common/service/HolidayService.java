package com.example.common.service;


import com.example.common.entity.Holiday;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;

public interface HolidayService {


    Boolean addHoliday(Holiday holiday) throws SchedulerException;

    Boolean deleteHoliday(Long id) throws SchedulerException;

    Page<Holiday> findByCondition(Specification<Holiday> specification);


    Boolean update(Holiday holiday) throws SchedulerException;

    boolean isHoliday(Calendar instance);

    boolean isAllowOperate(Long id);
}
