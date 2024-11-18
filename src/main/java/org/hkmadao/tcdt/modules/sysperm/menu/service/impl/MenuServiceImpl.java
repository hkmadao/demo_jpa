package org.hkmadao.tcdt.modules.sysperm.menu.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseService;
import org.hkmadao.tcdt.modules.sysperm.menu.dao.desc.MenuDesc;
import org.hkmadao.tcdt.modules.sysperm.menu.dao.entity.Menu;
import org.hkmadao.tcdt.modules.sysperm.menu.dao.repository.MenuRepository;
import org.hkmadao.tcdt.modules.sysperm.menu.service.IMenuService;

@Service
public class MenuServiceImpl extends BaseService<Menu, String, MenuRepository, MenuDesc> implements IMenuService {

}
