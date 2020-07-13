package com.example.common.service.impl;


import com.example.common.constant.Permission;
import com.example.common.entity.Holiday;
import com.example.common.repository.HolidayRepository;
import com.example.common.service.HolidayService;
import com.example.common.utils.EnvHolder;
import com.example.common.utils.JpaUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.calendar.AnnualCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class HolidayServiceImpl implements HolidayService {


    private static Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);

    public static final String DEFINE_HOLIDAY = "DEFINE_HOLIDAY";
    public static final String DEFINE_WORK = "DEFINE_WORK";

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    private Scheduler scheduler;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Boolean addHoliday(Holiday holiday) throws SchedulerException {
        logger.info("addHoliday: {}", EnvHolder.getHolder().getTenantId());
        holiday.setTenantId(EnvHolder.getHolder().getTenantId());
        List<Holiday>  holidayList = holidayRepository.findAllByNameAndTenantId(holiday.getName(),holiday.getTenantId());
        if(!holidayList.isEmpty()){
            return false;
        }
        holidayRepository.save(holiday);
        updateCalendar();
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Boolean deleteHoliday(Long id) throws SchedulerException {
        holidayRepository.deleteById(id);
        updateCalendar();
        return true;
    }

    @Override
    public Page<Holiday> findByCondition(Specification<Holiday> specification) {
        return holidayRepository.findAll(specification, JpaUtils.getPageRequest());
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public Boolean update(Holiday holiday) throws SchedulerException {
        List<Holiday>  holidayList = holidayRepository.findAllByNameAndTenantId(holiday.getName(),holiday.getTenantId());
        if(holidayList.size() > 1 ){
            return false;

        }
        if( 1 == holidayList.size() && !holidayList.get(0).getId().equals(holiday.getId())){
            return false;
        }
        holidayRepository.save(holiday);
        updateCalendar();
        return true;
    }


    private void updateCalendar() throws SchedulerException {
        List<Holiday> holidays = holidayRepository.findAll();
        addCalendar(holidays,""); //更新全局

        holidays = holidays.stream()
                    .filter(holiday -> holiday.getTenantId() != null && holiday.getTenantId().equals(EnvHolder.getHolder().getTenantId()))
                    .collect(Collectors.toList());

        Map<String,List<Holiday>> holidaysList = holidays.stream()
                .filter(holiday -> holiday.getTenantId() != null)
                .collect(Collectors.groupingBy(holiday -> holiday.getTenantId(), Collectors.toList()));
        for (Map.Entry<String, List<Holiday>> item : holidaysList.entrySet()){
            List<Holiday> holidayList = item.getValue();
            if(holidayList.isEmpty()){
                continue;
            }
            addCalendar(holidayList, item.getKey());
        }

    }

    private void addCalendar(List<Holiday> holidays, String tenanId) throws SchedulerException {
        Set<Calendar> excludeDaySet = new HashSet<>();
        Set<Calendar> includeDaySet = new HashSet<>();

        holidays.forEach(h -> {
            //定义一个接受时间的集合
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            // 设置初始时间和结束时间
            start.setTime(h.getStartTime());
            end.setTime(h.getEndTime());

            // 测试此日期是否在指定日期之后
            while (!start.getTime().after(end.getTime())) {
                Calendar add = Calendar.getInstance();
                add.setTime(start.getTime());
                if (h.getType() != null && h.getType() == 1) {
                    includeDaySet.add(add); //调休
                } else {
                    excludeDaySet.add(add);//节假日
                }

                start.add(Calendar.DAY_OF_YEAR, 1);
            }

            if (h.getType() != null && h.getType() == 1) {
                includeDaySet.add(end);
            } else {
                excludeDaySet.add(end);
            }
        });

        AnnualCalendar holidayCalendar = new AnnualCalendar();
        AnnualCalendar workCalendar = new AnnualCalendar();
        holidayCalendar.setDaysExcluded(new ArrayList<>(excludeDaySet));//节假日
        workCalendar.setDaysExcluded(new ArrayList<>(includeDaySet));//调休日

        //注册日历
        scheduler.addCalendar(DEFINE_HOLIDAY+tenanId, holidayCalendar, true, true);
        scheduler.addCalendar(DEFINE_WORK+tenanId, workCalendar, true, true);

    }
    @Override
    public boolean isHoliday(Calendar instance) {
        int day = instance.get(Calendar.DAY_OF_WEEK);
        AnnualCalendar holidayAnnualCalendar = new AnnualCalendar();
        AnnualCalendar workAnnualCalendar = new AnnualCalendar();
        String tenantId = EnvHolder.getHolder().getTenantId();
        try {
            if(tenantId.equals(Permission.PERMISSION_ALL)){
                holidayAnnualCalendar = (AnnualCalendar) scheduler.getCalendar(HolidayServiceImpl.DEFINE_HOLIDAY);
                workAnnualCalendar = (AnnualCalendar) scheduler.getCalendar(HolidayServiceImpl.DEFINE_WORK);
            }else {
                holidayAnnualCalendar = (AnnualCalendar) scheduler.getCalendar(HolidayServiceImpl.DEFINE_HOLIDAY+tenantId);
                workAnnualCalendar = (AnnualCalendar) scheduler.getCalendar(HolidayServiceImpl.DEFINE_WORK+tenantId);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        if (holidayAnnualCalendar == null) {
            holidayAnnualCalendar = new AnnualCalendar();
        }
        if (workAnnualCalendar == null) {
            workAnnualCalendar = new AnnualCalendar();
        }

        // 调休优先级高于节假日，先判断调休，若调休日历中包含当前时间，则认定为非工作日
        if (workAnnualCalendar.isDayExcluded(instance)) {
            return false;
        }

        // 如果holiday日历包含 && 调休日历中不包含该时间，认定为节假日，忽略任务生成
        if (holidayAnnualCalendar.isDayExcluded(instance) || day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isAllowOperate(Long id) {
        Holiday holiday = holidayRepository.findById(id).orElse(null);
        String tenantId = EnvHolder.getHolder().getTenantId();
        if(null == holiday || null == holiday.getTenantId() ){
            return  true;
        }
        if( !(tenantId.equals(Permission.PERMISSION_ALL)) && !(tenantId.equals(holiday.getTenantId())) ){
            return  false;
        }
        return true;
    }
}





