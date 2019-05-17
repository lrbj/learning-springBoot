package com.example.mybatisdemo.service;
import com.example.mybatisdemo.model.Process;
import java.util.List;

public interface ProcessService {

	/**
	 * 根据流程实例Id，查询流程数据
	 *
	 * @param processInstanceId
	 *            流程实例id
	 * @return 流程数据
	 */
	Process selectByProcessInstanceId(String processInstanceId);

	/**
	 * 根据申请单号，查询流程数据列表
	 *
	 * @param applicationId
	 *            流程实例id
	 * @return 流程数据列表
	 */
	List<Process> queryProcessesByApplicationId(String applicationId);

	/**
	 * 将流程数据，插入数据库
	 *
	 * @param process
	 *            流程数据
	 * @return 插入成功条数
	 */
	int insert(Process process);

	/**
	 * 更新流程数据
	 *
	 * @param process
	 *            流程数据
	 * @return 更新成功条数
	 */
	int update(Process process);
}
