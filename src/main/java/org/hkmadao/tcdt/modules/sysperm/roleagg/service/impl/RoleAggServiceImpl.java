package org.hkmadao.tcdt.modules.sysperm.roleagg.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseAggService;
import org.hkmadao.tcdt.modules.sysperm.roleagg.dao.aggdesc.RoleMDesc;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
import org.hkmadao.tcdt.modules.sysperm.role.dao.repository.RoleRepository;
import org.hkmadao.tcdt.modules.sysperm.roleagg.service.IRoleAggService;

@Service
public class RoleAggServiceImpl extends BaseAggService<Role, String, RoleRepository, RoleMDesc> implements IRoleAggService {

}
