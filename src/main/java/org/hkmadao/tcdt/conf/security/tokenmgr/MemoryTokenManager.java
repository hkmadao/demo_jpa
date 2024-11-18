package org.hkmadao.tcdt.conf.security.tokenmgr;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 通过内存管理token
 */
@Service("memoryTokenManager")
public class MemoryTokenManager implements ITokenManager{

    private static Map<String, TcdtUserInfo> userInfoMap = new HashMap<>();

    @Override
    public String createToken(TcdtUserInfo userInfo) {
        String token = UUID.randomUUID().toString();
        userInfoMap.put(token, userInfo);
        return token;
    }

    @Override
    public TcdtUserInfo getTcdtUserInfoByToken(String token) {
        return userInfoMap.get(token);
    }

    @Override
    public void removeToken(String token) {
        userInfoMap.remove(token);
    }

    @Override
    public void removeTokenByUsername(String username) throws BusinessException {

    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }
}
