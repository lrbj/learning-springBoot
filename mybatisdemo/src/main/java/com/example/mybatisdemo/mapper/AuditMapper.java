package com.example.mybatisdemo.mapper;


import com.example.mybatisdemo.dto.AuditGroupData;
import com.example.mybatisdemo.dto.AuditRequestDto;
import com.example.mybatisdemo.model.Audit;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface AuditMapper {
    /**
     * 根据申请编号，查询 消审业务数据
     *
     * @param applicationId
     *            申请编号
     * @return  消审业务数据
     */
    List<Audit> selectByApplicationId(@Param("applicationId") String applicationId);

    /**
     * 将 消审业务数据，插入数据库
     *
     * @param audit
     *             消审业务数据
     * @return 插入成功条数
     */
    int insert(Audit audit);

    /**
     * 删除 消审业务数据
     *
     * @param audit
     *             消审业务数据
     * @return 删除成功条数
     */
    int update(Audit audit);

    /**
     * 根据窗口请求参数体，查询相应的 消审申请列表
     *
     * @param applicationId
     *             消审申请编号
     * @param applyStartTime
     *            申请时间区间
     * @param applyEndTime
     *            申请时间区间
     * @param applyUserId
     *            申请人用户id
     * @param unitParkId
     *            申请单位-园区id
     * @param unitBusinessGroupId
     *            申请单位-事业群id
     * @param unitBusinessDepartmentId
     *            申请单位-事业处id
     * @param lastActivityStatus
     *            最后活动的状态
     * @param applied
     *            是否已提交
     * @return  消审申请记录
     */
    List<AuditRequestDto> queryAuditRequest(@Param("applicationId") String applicationId,
                                            @Param("applyStartTime") Timestamp applyStartTime,
                                            @Param("applyEndTime") Timestamp applyEndTime,
                                            @Param("applyUserId") String applyUserId,
                                            @Param("unitParkId") String unitParkId,
                                            @Param("unitBusinessGroupId") String unitBusinessGroupId,
                                            @Param("unitBusinessDepartmentId") String unitBusinessDepartmentId,
                                            @Param("lastActivityType") Integer lastActivityType,
                                            @Param("lastActivityStatus") Integer lastActivityStatus, @Param("applied") Boolean applied);


    /**
     * 根据申请编号，查询 消审申请详情
     *
     * @param applicationId
     *            申请编号
     * @return  消审原因列表
     */
    AuditRequestDto queryAuditRequestDetailsByApplicationId(@Param("applicationId") String applicationId);

    /**
     *
     * @param startDate
     * @param endDate
     * @param approved
     * @param returned
     * @param unapplied
     * @param approving
     * @param unapproved
     * @return
     */
    AuditGroupData queryAuditRequestByGroupAndMonth(@Param("startDate") Timestamp startDate,
                                                    @Param("endDate") Timestamp endDate,
                                                    @Param("approved") Integer approved,//通过
                                                    @Param("returned") Integer returned,//退件
                                                    @Param("unapplied") Integer unapplied, //待申请
                                                    @Param("approving") Integer approving,//待审核
                                                    @Param("unapproved") Integer unapproved//驳回
                                                    );






}
