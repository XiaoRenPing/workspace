package com.rpym.univweb.service.system.user;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.rpym.univweb.dto.user.TokenDto;
import com.rpym.univweb.dto.user.UserAddDto;
import com.rpym.univweb.dto.user.UserInfoDetailOutDto;
import com.rpym.univweb.dto.user.UserQueryDto;
import com.rpym.univweb.dto.user.UserQueryOutDto;
import com.rpym.univweb.dto.user.UserUpdateDto;
import com.rpym.univweb.entity.SysUsers;
import com.rpym.univweb.utils.DataGridResult;
import com.rpym.univweb.utils.ResponseResult;

public interface IUserService {

	public String addUserInfo(SysUsers user);
	
	public DataGridResult queryUserList(int page, int rows);

	public ResponseResult login(String name, String password, HttpServletRequest request);
	
	
}
