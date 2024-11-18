package org.hkmadao.tcdt.conf.security.tokenmgr;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.core.utils.IdGenerator;
import org.hkmadao.tcdt.conf.security.JwtTokenUtil;
import org.hkmadao.tcdt.conf.security.info.TcdtUserDetails;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;
import org.hkmadao.tcdt.modules.token.dao.entity.Token;
import org.hkmadao.tcdt.modules.token.service.ITokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通过数据库管理token
 */
@Service("dbTokenManager")
public class DbTokenManager implements ITokenManager, CommandLineRunner {

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String createToken(TcdtUserInfo userInfo) throws BusinessException {
        TcdtUserDetails tcdtUserDetails = new TcdtUserDetails();
        TcdtUserInfo tcdtUserInfo = new TcdtUserInfo();
        tcdtUserInfo.setUsername(userInfo.getUsername());
        tcdtUserInfo.setFgActive(userInfo.getFgActive());
        tcdtUserInfo.setNickName(userInfo.getNickName());
        tcdtUserDetails.setTcdtUserInfo(tcdtUserInfo);
        String token = null;
        try {
            token = jwtTokenUtil.generateToken(tcdtUserDetails);
        } catch (Exception e) {
            throw new BusinessException("生成token失败！", e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = null;
        try {
            userString = objectMapper.writeValueAsString(userInfo);
        } catch (JsonProcessingException e) {
            throw new BusinessException("序列化用户信息失败！", e);
        }
        Token tokenSave = new Token();
        tokenSave.setIdToken(IdGenerator.generateId());
        tokenSave.setUserInfoString(userString);
        tokenSave.setToken(token);
        tokenSave.setCreateTime(new Date(userInfo.getCreateTime().getTime()));
        tokenSave.setExpiredTime(new Date(userInfo.getExpiredTime().getTime()));
        tokenSave.setUsername(userInfo.getUsername());
        tokenSave.setNickName(userInfo.getNickName());
        tokenService.insertById(tokenSave);
        return token;
    }

    @Override
    public TcdtUserInfo getTcdtUserInfoByToken(String token) throws BusinessException {
        Token tokenParam = new Token();
        tokenParam.setToken(token);
        Token serviceOne = tokenService.findOne(tokenParam);
        String userInfoString = serviceOne.getUserInfoString();
        if (!StringUtils.hasLength(userInfoString)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TcdtUserInfo userInfo = null;
        try {
            userInfo = mapper.readValue(userInfoString, TcdtUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException("反序列化用户信息失败！", e);
        }
        return userInfo;
    }

    @Override
    public void removeToken(String token) throws BusinessException {
        Token tokenParam = new Token();
        tokenParam.setToken(token);
        tokenService.deleteNoCheckRelation(tokenParam);
    }

    @Override
    public void removeTokenByUsername(String username) throws BusinessException {
        List<Token> tokens = tokenService.findAll();
        List<Token> expiredTokenList = tokens.stream().filter(token -> username.equals(token.getUsername())).collect(Collectors.toList());
        tokenService.deleteAllInBatch(expiredTokenList);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenUtil.validateToken(token);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Token> tokens = tokenService.findAll();
        long currentTimestame = Calendar.getInstance().getTime().getTime();
        List<Token> expiredTokenList = tokens.stream().filter(token -> currentTimestame > token.getExpiredTime().getTime()).collect(Collectors.toList());
        tokenService.deleteAllInBatch(expiredTokenList);
    }
}
