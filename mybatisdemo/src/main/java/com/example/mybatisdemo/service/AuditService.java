package com.example.mybatisdemo.service;


import com.example.mybatisdemo.dto.AuditGroupData;
import com.example.mybatisdemo.dto.AuditRequestDto;
import com.example.mybatisdemo.model.Audit;

import java.util.List;

public interface AuditService {

     List<Audit> selectByApplicationId(String applicationId);


     int insert(Audit audit);


     int update(Audit audit) ;


    List<AuditRequestDto> queryAuditRequest(String applicationId, Long applyStartTime, Long applyEndTime,
                                            String applyUserId,
                                            String unitParkId,
                                            String unitBusinessGroupId,
                                            String unitBusinessDepartmentId,
                                            Integer lastActivityType,
                                            Integer lastActivityStatus, Boolean applied);



   AuditRequestDto queryAuditRequestDetailsByApplicationId(String applicationId) throws Exception;

   List<AuditGroupData> queryAuditRequestByGroupAndMonth(Integer month, Integer year);
}
