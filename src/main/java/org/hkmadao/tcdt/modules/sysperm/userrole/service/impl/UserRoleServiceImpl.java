package org.hkmadao.tcdt.modules.sysperm.userrole.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseService;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.desc.UserRoleDesc;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.entity.UserRole;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.repository.UserRoleRepository;
import org.hkmadao.tcdt.modules.sysperm.userrole.service.IUserRoleService;

@Service
public class UserRoleServiceImpl extends BaseService<UserRole, String, UserRoleRepository, UserRoleDesc> implements IUserRoleService {

}
