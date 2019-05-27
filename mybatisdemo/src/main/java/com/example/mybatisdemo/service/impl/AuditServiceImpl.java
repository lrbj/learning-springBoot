package com.example.mybatisdemo.service.impl;

import com.example.mybatisdemo.dto.AuditGroupData;
import com.example.mybatisdemo.dto.AuditRequestDto;
import com.example.mybatisdemo.mapper.AuditMapper;
import com.example.mybatisdemo.model.Audit;
import com.example.mybatisdemo.service.AuditService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class AuditServiceImpl implements AuditService {
    private static Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);
    @Resource
    private AuditMapper auditMapper;

    @Override
    public List<Audit> selectByApplicationId(String applicationId) {
        Preconditions.checkNotNull(applicationId, "applicationId must not be null.");
        return auditMapper.selectByApplicationId(applicationId);
    }

    @Override
    public int insert(Audit audit) {
        Preconditions.checkNotNull(audit, "insert audit must not be null.");
        return auditMapper.insert(audit);
    }

    @Override
    public int update(Audit audit) {
        Preconditions.checkNotNull(audit, "update audit must not be null.");
        return auditMapper.update(audit);
    }

    @Override
    public List<AuditRequestDto> queryAuditRequest(String applicationId, Long applyStartTime, Long applyEndTime,
                                                   String applyUserId,
                                                   String unitParkId,
                                                   String unitBusinessGroupId,
                                                   String unitBusinessDepartmentId,
                                                   Integer lastActivityType,
                                                   Integer lastActivityStatus, Boolean applied) {
        return auditMapper.queryAuditRequest(applicationId, Objects.isNull(applyStartTime) ? null : new Timestamp(applyStartTime), Objects.isNull(applyEndTime) ? null : new Timestamp(applyEndTime), applyUserId,
                 unitParkId, unitBusinessGroupId, unitBusinessDepartmentId,
                lastActivityType, lastActivityStatus, applied);
    }

    @Override
    public AuditRequestDto queryAuditRequestDetailsByApplicationId(String applicationId) {
        Preconditions.checkNotNull(applicationId, "applicationId must not be null.");
        return auditMapper.queryAuditRequestDetailsByApplicationId(applicationId);
    }

    @Override
    public List<AuditGroupData> queryAuditRequestByGroupAndMonth(Integer month, Integer year) {

        String strStartTime = String.format("%d-%02d-01 00:00:00",year,month);
        Integer endYear = month/12+ year;
        String strEndTime = String.format("%d-%02d-01 00:00:00", endYear, (month+1)%12);
        logger.info("strStartTime:"+strStartTime);
        logger.info("strEndTime:" + strEndTime);
        Timestamp startTime = Timestamp.valueOf(strStartTime);
        Timestamp endTime = Timestamp.valueOf(strEndTime);
        logger.info("startTime:"+startTime);
        logger.info("endTime:" + endTime);
       Timestamp now = new Timestamp(System.currentTimeMillis());
       //return null;
        System.out.println(auditMapper.queryAuditRequestByMonth(startTime,endTime,1,2,3,4,5));
        return (List<AuditGroupData>) auditMapper.queryAuditRequestByGroupAndMonth(startTime,endTime,1,2,3,4,5);

    }
}
