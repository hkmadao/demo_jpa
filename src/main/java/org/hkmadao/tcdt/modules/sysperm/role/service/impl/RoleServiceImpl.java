package org.hkmadao.tcdt.modules.sysperm.role.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseService;
import org.hkmadao.tcdt.modules.sysperm.role.dao.desc.RoleDesc;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
import org.hkmadao.tcdt.modules.sysperm.role.dao.repository.RoleRepository;
import org.hkmadao.tcdt.modules.sysperm.role.service.IRoleService;

@Service
public class RoleServiceImpl extends BaseService<Role, String, RoleRepository, RoleDesc> implements IRoleService {

}
