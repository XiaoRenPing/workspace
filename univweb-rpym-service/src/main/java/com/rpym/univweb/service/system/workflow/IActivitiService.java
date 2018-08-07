package com.rpym.univweb.service.system.workflow;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.rpym.univweb.dto.workflow.HanderTaskInDto;
import com.rpym.univweb.dto.workflow.HistoryTsakListQryInDto;
import com.rpym.univweb.dto.workflow.PathOutDto;
import com.rpym.univweb.dto.workflow.TaskListQryInDto;
import com.rpym.univweb.dto.workflow.TaskListQryOutDto;
import com.rpym.univweb.utils.ResponseResult;

public interface IActivitiService {

	//审批人 - 查询我的待办
	public PageInfo<TaskListQryOutDto> queryTaskList(TaskListQryInDto dto);
	
	//审批人 - 查询历史处理记录
	public PageInfo<TaskListQryOutDto> queryHistoryTaskList(HistoryTsakListQryInDto dto);
	
	//申请人 - 查询我的申请记录
	public PageInfo<TaskListQryOutDto> queryTaskApplyList(TaskListQryInDto dto);
	
	//获取审批路径列表
	public List<PathOutDto> toHanderTask(String taskId);
	
	//流程进程图
	public void queryImage(String processInstanceId, HttpServletResponse response);

	//审批表单
	public Map<String, Object> queryFormDataInTask(String taskId);
	
	//流程回滚
	public String taskRollback(String taskId);

	//完成流程
	public boolean complete(HanderTaskInDto dto);
	
	//启动请假流程
	public ResponseResult startRestProcess();
}
