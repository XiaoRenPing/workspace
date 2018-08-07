package com.rpym.univweb.service.system.menu.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.rpym.univweb.dto.menu.SysMenuQueryDto;
import com.rpym.univweb.entity.SysMenu;

public interface ISysMenuService {

	String addSysMenu(SysMenu sysMenu);

	SysMenu findSysMenuById(Long id);

	String deleteSysMenu(Long id);

	PageInfo<SysMenu> pageListSysMenu(SysMenuQueryDto menuQueryDto);

	boolean updateSysMenu(SysMenu sysMenu);
	
	public List<SysMenu> getSubMenuList(Long parenId);
}
