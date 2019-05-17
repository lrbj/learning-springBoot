package com.example.mybatisdemo.service.impl;

import com.example.mybatisdemo.mapper.ProcessMapper;
import com.example.mybatisdemo.service.ProcessService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import com.example.mybatisdemo.model.Process;

@Service
public class ProcessServiceImpl implements ProcessService {

	@Resource
	private ProcessMapper processMapper;

	@Override
	public Process selectByProcessInstanceId(String processInstanceId) {
		Preconditions.checkNotNull(processInstanceId, "processInstanceId must not be null.");
		return processMapper.selectByProcessInstanceId(processInstanceId);
	}

	@Override
	public List<Process> queryProcessesByApplicationId(String applicationId) {
		Preconditions.checkNotNull(applicationId, "applicationId must not be null.");
		return processMapper.queryProcessesByApplicationId(applicationId);
	}

	@Override
	public int insert(Process process) {
		Preconditions.checkNotNull(process, "insert process must not be null.");
		return processMapper.insert(process);
	}

	@Override
	public int update(Process process) {
		Preconditions.checkNotNull(process, "update process must not be null.");
		return processMapper.update(process);
	}
}
