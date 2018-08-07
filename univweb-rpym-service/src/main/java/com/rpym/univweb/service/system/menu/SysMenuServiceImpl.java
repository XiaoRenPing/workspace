package com.rpym.univweb.service.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.rpym.univweb.dao.SysMenuMapper;
import com.rpym.univweb.dto.menu.SysMenuQueryDto;
import com.rpym.univweb.entity.SysMenu;
import com.rpym.univweb.entity.SysMenuExample;
import com.rpym.univweb.service.system.menu.impl.ISysMenuService;

@Service("sysMenuService")
public class SysMenuServiceImpl implements ISysMenuService {

	@Autowired
	SysMenuMapper sysMenuDao;

	public String addSysMenu(SysMenu sysMenu) {
		sysMenuDao.updateByPrimaryKeySelective(sysMenu);
		return sysMenu.getMenuName();
	}

	public SysMenu findSysMenuById(Long id) {
		SysMenu sysMenu = sysMenuDao.selectByPrimaryKey(id);
		return sysMenu;
	}

	public String deleteSysMenu(Long id) {
		SysMenu sysMenu = sysMenuDao.selectByPrimaryKey(id);
		sysMenuDao.deleteByPrimaryKey(sysMenu.getId());
		return sysMenu.getMenuName();
	}

	/**
	 * 查询所有菜单
	 * <p>Title: pageListSysMenu</p>   
	 * <p>Description: </p>   
	 * @param menuQueryDto
	 * @return   
	 * @see com.rpym.univweb.service.basic.menu.impl.ISysMenuService#pageListSysMenu(com.rpym.univweb.dto.SysMenuQueryDto)
	 */
	public PageInfo<SysMenu> pageListSysMenu(SysMenuQueryDto menuQueryDto) {
		SysMenuExample menuExample = new SysMenuExample();
		SysMenuExample.Criteria menuCriteria = menuExample.createCriteria();
		if(menuQueryDto.getMenuname() != null) {
			menuCriteria.andMenuNameLike(menuQueryDto.getMenuname());
		}
		List<SysMenu> sysMenuList = sysMenuDao.selectByExample(menuExample);
		return new PageInfo<SysMenu>(sysMenuList);
	}
	
	public boolean updateSysMenu(SysMenu sysMenu) {
		sysMenuDao.updateByPrimaryKeySelective(sysMenu);
		return true;
	}
	
	// ----------------   根据    -------------------------
	/**
	 * 根据父类id获取子菜单
	* @Title: getSubMenuList
	* @Description: 
	* @param @param parenId
	* @param @return    参数
	* @return List<SysMenu>    返回类型
	* @throws
	 */
	public List<SysMenu> getSubMenuList(Long parenId){
		SysMenuExample menuExample = new SysMenuExample();
		menuExample.createCriteria().andParentIdEqualTo(parenId);
		List<SysMenu> sysMenuList = sysMenuDao.selectByExample(menuExample);
		return sysMenuList;
	}
}
