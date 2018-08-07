package com.rpym.univweb.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rpym.univweb.entity.SysUsers;
import com.rpym.univweb.service.system.user.IUserService;
import com.rpym.univweb.utils.DataGridResult;
import com.rpym.univweb.utils.ResponseResult;
import com.rpym.univweb.web.controller.base.BaseController;

@Controller
@RequestMapping("/users/*")
public class UserController extends BaseController{

	@Autowired
	private IUserService userService;
	
	@RequestMapping(method=RequestMethod.POST, value="/add")
	@ResponseBody
	public String saveUserInfo(SysUsers user) {
		return userService.addUserInfo(user);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/list")
	@ResponseBody
	public DataGridResult findUserList(@RequestParam("page")int page, @RequestParam("rows")int rows){
		return userService.queryUserList(page, rows);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	@ResponseBody
	public ResponseResult login(@RequestParam("name")String name, @RequestParam("password")String password, HttpServletRequest request){
		return userService.login(name, password, request);
	}
}
