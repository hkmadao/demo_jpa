package org.hkmadao.tcdt.modules.sysperm.user.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseService;
import org.hkmadao.tcdt.modules.sysperm.user.dao.desc.UserDesc;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.hkmadao.tcdt.modules.sysperm.user.dao.repository.UserRepository;
import org.hkmadao.tcdt.modules.sysperm.user.service.IUserService;

@Service
public class UserServiceImpl extends BaseService<User, String, UserRepository, UserDesc> implements IUserService {

}
