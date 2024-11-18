package org.hkmadao.tcdt.conf.security.tokenmgr;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;

public interface ITokenManager {

    String TCDT_TOKEN = "Authorization";

    String createToken(TcdtUserInfo userInfo) throws BusinessException;

    TcdtUserInfo getTcdtUserInfoByToken(String token) throws BusinessException;

    void removeToken(String token) throws BusinessException;

    void removeTokenByUsername(String username) throws BusinessException;

    /**
     * 验证token是否还有效
     *
     * @param token 客户端传入的token
     */
    boolean validateToken(String token);
}
