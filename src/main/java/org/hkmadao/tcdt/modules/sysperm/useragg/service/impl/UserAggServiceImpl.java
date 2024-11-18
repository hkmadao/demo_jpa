package org.hkmadao.tcdt.modules.sysperm.useragg.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseAggService;
import org.hkmadao.tcdt.modules.sysperm.useragg.dao.aggdesc.UserMDesc;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.hkmadao.tcdt.modules.sysperm.user.dao.repository.UserRepository;
import org.hkmadao.tcdt.modules.sysperm.useragg.service.IUserAggService;

@Service
public class UserAggServiceImpl extends BaseAggService<User, String, UserRepository, UserMDesc> implements IUserAggService {

}
