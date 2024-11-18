package org.hkmadao.tcdt.conf.security.tokenmgr;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * 通过mapdb管理token
 */
//@Service("mapDbTokenManager")
public class MapDbTokenManager implements ITokenManager, CommandLineRunner, DisposableBean {

    @Value("${tcdt.token.file-path:#{NULL}}")
    private String tokenFilePath;

    private DB db;

    private ConcurrentMap tokenMap;

    @Override
    public void run(String... args) throws Exception {
        //TODO 如果使用mapdb管理token，请自行实现过期token清理逻辑
        String STORAGE_FILE_PATH = System.getProperty("user.dir") + File.separator + "token.mapdb";
        //如果没有配置文件路径，token文件存储在运行路径下
        if (StringUtils.hasLength(tokenFilePath)) {
            STORAGE_FILE_PATH = tokenFilePath + "token.mapdb";
        }

        db = DBMaker.fileDB(STORAGE_FILE_PATH).checksumHeaderBypass().make();
        tokenMap = db.hashMap("tokenMap").createOrOpen();

        long currentTimestame = Calendar.getInstance().getTime().getTime();
        Set<String> removeTokens = new HashSet<>();
        tokenMap.forEach((key, value) -> {
            String userInfoString = (String) tokenMap.get(value);
            if (!StringUtils.hasLength(userInfoString)) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                TcdtUserInfo userInfo = mapper.readValue(userInfoString, TcdtUserInfo.class);
                if (currentTimestame > userInfo.getExpiredTime().getTime()) {
                    removeTokens.add(key.toString());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                removeTokens.add(key.toString());
            }
        });
        for (String removeToken : removeTokens) {
            tokenMap.remove(removeToken);
        }
    }

    @Override
    public void destroy() throws Exception {
        db.close();
    }

    @Override
    public String createToken(TcdtUserInfo userInfo) {
        String token = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = null;
        try {
            userString = objectMapper.writeValueAsString(userInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        tokenMap.put(token, userString);
        return token;
    }

    @Override
    public TcdtUserInfo getTcdtUserInfoByToken(String token) {
        String userInfoString = (String) tokenMap.get(token);
        if (!StringUtils.hasLength(userInfoString)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TcdtUserInfo userInfo = null;
        try {
            userInfo = mapper.readValue(userInfoString, TcdtUserInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    @Override
    public void removeToken(String token) {
        tokenMap.remove(token);
    }

    /**
     * 修改密码后清理用户token
     * @param username 用户账号
     * @throws BusinessException
     */
    @Override
    public void removeTokenByUsername(String username) throws BusinessException {
        //TODO 如果使用mapdb管理token，请自行实现
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

}
