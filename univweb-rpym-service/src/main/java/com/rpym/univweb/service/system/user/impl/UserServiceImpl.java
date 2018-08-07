package com.rpym.univweb.service.system.user.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rpym.univweb.constants.CommonConst;
import com.rpym.univweb.dao.SysUserRolesMapper;
import com.rpym.univweb.dao.SysUsersMapper;
import com.rpym.univweb.dto.common.SessionUserDto;
import com.rpym.univweb.dto.user.TokenDto;
import com.rpym.univweb.dto.user.UserAddDto;
import com.rpym.univweb.dto.user.UserInfoDetailOutDto;
import com.rpym.univweb.dto.user.UserQueryDto;
import com.rpym.univweb.dto.user.UserQueryOutDto;
import com.rpym.univweb.dto.user.UserUpdateDto;
import com.rpym.univweb.entity.SysUsers;
import com.rpym.univweb.entity.SysUsersExample;
import com.rpym.univweb.service.base.BaseService;
import com.rpym.univweb.service.system.user.IUserService;
import com.rpym.univweb.utils.DataGridResult;
import com.rpym.univweb.utils.RandomUtil;
import com.rpym.univweb.utils.RedisCacheManager;
import com.rpym.univweb.utils.ResponseResult;
import com.rpym.univweb.utils.UWException;

/**
 * 用户业务服务
* @ClassName: UserServiceImpl
* @Description: 
* @author XiaoRenPing
* @date 2018年6月2日
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements IUserService{
	
	@Autowired
	SysUsersMapper userDao;
	
	@Autowired
	SysUserRolesMapper userRolesDao;
	
	@Autowired
	RedisCacheManager redisCacheManager;
	
	
	public String addUserInfo(SysUsers user) {
		userDao.insertSelective(user);
		return user.getName();
	}

	public DataGridResult queryUserList(int page, int rows) {
		SysUsersExample userExample = new SysUsersExample();
		userExample.createCriteria().andIdIsNotNull();
		PageHelper.startPage(page, rows);
		//this.initPage(userQryDto);
		List<SysUsers> userList = userDao.selectByExample(userExample);

		DataGridResult dataResult = new DataGridResult();
		dataResult.setDataList(userList);
		PageInfo<SysUsers> userPageInfo = new PageInfo<SysUsers>(userList);
		dataResult.setTotal(userPageInfo.getTotal());
		return dataResult;
	}

	
	public ResponseResult login(String name, String password, HttpServletRequest request) {
		SysUsersExample userExample = new SysUsersExample();
		userExample.createCriteria().andUsernameEqualTo(name).andPasswordEqualTo(password);
		List<SysUsers> userList = userDao.selectByExample(userExample);
		if(userList != null && userList.size() == 0) {
			throw new UWException("用户不存在！");
		}
		SysUsers user = userList.get(0);
		//保存用户信息
		long time = 60 * 60; // 1个小时
		String token = RandomUtil.getContractId();
		String userKey = this.addPrefixUser(user.getId());
		//从Redis中获取token
		String tokenKey = (String) redisCacheManager.get(userKey);
		if(StringUtils.isNotBlank(tokenKey)) {
			redisCacheManager.del(tokenKey);
			redisCacheManager.del(userKey);
			redisCacheManager.del(addPrefixDeleteToken(token));
			
			String markDeletetokey = StringUtils.join(CommonConst.DELETE, CommonConst.UNDER_LINE, tokenKey);
			redisCacheManager.set(markDeletetokey, userKey, time);
		}
		boolean tokeyResult = redisCacheManager.set(tokenKey, user, time);
		boolean userKeyResult = redisCacheManager.set(userKey, tokenKey, time);

		logger.info("insert new token to redis = " + tokeyResult + ", " + userKeyResult);

		long endTime1 = System.currentTimeMillis();
		logger.info("执行登录，执行时长：" + (endTime1 - System.currentTimeMillis()));

		request.getSession().setAttribute("sessionUser", user);
		return ResponseResult.ok();
	}


}
