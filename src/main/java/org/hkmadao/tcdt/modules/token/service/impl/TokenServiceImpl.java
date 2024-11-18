package org.hkmadao.tcdt.modules.token.service.impl;

import org.springframework.stereotype.Service;
import org.hkmadao.core.base.service.BaseService;
import org.hkmadao.tcdt.modules.token.dao.desc.TokenDesc;
import org.hkmadao.tcdt.modules.token.dao.entity.Token;
import org.hkmadao.tcdt.modules.token.dao.repository.TokenRepository;
import org.hkmadao.tcdt.modules.token.service.ITokenService;

@Service
public class TokenServiceImpl extends BaseService<Token, String, TokenRepository, TokenDesc> implements ITokenService {

}
