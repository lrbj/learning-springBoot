package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.model.Audit;
import com.example.mybatisdemo.service.AuditService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/audit")
public class AuditController {
    private static Logger logger = LoggerFactory.getLogger(AuditController.class);

    private Timestamp now = new Timestamp(System.currentTimeMillis());
    @Autowired
    private AuditService auditService;

    private boolean judgeParamIsNull(Object object) {
        if (null == object) {
            logger.error("参数错误");
            return true;
        }
        return false;
    }


    @PostMapping(value = "/audit-request")
    @ApiOperation(value = "插入申请")
    public void insertAudit(@RequestBody Audit audit) {
        if (judgeParamIsNull(audit)) {
            return;
        }
        audit.setLastUpdateTime(now);
        audit.setCreateTime(now);
        audit.setPlanEndTime(now);
        audit.setPlanStartTime(now);

        if (audit.getId() == 0) {
            auditService.insert(audit);
        } else {
            auditService.update(audit);
        }
    }

    @GetMapping(value = "/audit-request")
    @ApiOperation(value = "查询")
    public Object queryAudit(@RequestParam("year") Integer year, @RequestParam("month") Integer month) {
        logger.info("queryAudit:year" + year + ": month" + month);
        return auditService.queryAuditRequestByGroupAndMonth(month, year);
    }

}
