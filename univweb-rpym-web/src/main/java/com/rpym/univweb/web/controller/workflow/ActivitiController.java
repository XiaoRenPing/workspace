package com.rpym.univweb.web.controller.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.rpym.univweb.dto.workflow.TaskListQryInDto;
import com.rpym.univweb.dto.workflow.TaskListQryOutDto;
import com.rpym.univweb.service.system.workflow.IActivitiService;
import com.rpym.univweb.utils.ResponseResult;
import com.rpym.univweb.web.controller.base.BaseController;

@Controller
@RequestMapping("/workflow/*")
public class ActivitiController extends BaseController{

	@Autowired
	IActivitiService activitiService;
	
	//------------------------- 视图层跳转    -----------------------------
	@RequestMapping(method=RequestMethod.GET, value="/mytasklist")
	public String myTaskList() {
		return "/workflow/taskList";
	}
	
	
	
	//------------------------- 业务调用  -----------------------------
	@RequestMapping(method=RequestMethod.GET, value="/startrestapply")
	@ResponseBody
	public ResponseResult startRestProcess() {
		return activitiService.startRestProcess();
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/tasklist")
	@ResponseBody
	public PageInfo<TaskListQryOutDto> myTodoList(@ModelAttribute TaskListQryInDto dto) {
		return activitiService.queryTaskList(dto);
	}
}
