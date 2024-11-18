package org.hkmadao.tcdt.modules.sysperm.rolemenu.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseService;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.desc.RoleMenuDesc;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.entity.RoleMenu;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.repository.RoleMenuRepository;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.service.IRoleMenuService;

@Service
public class RoleMenuServiceImpl extends BaseService<RoleMenu, String, RoleMenuRepository, RoleMenuDesc> implements IRoleMenuService {

}
