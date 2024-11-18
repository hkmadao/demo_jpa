package org.hkmadao.tcdt.conf.security;

import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;


import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.security.info.TcdtUserDetails;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.hkmadao.tcdt.modules.sysperm.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    //自定义认证逻辑
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userParam = new User();
        userParam.setAccount(username);
        userParam.setFgActive(true);
        try {
            User user = userService.findOne(userParam);
            //判断用户是否存在,不存在就返回异常
            if (Objects.isNull(user)) {
                throw new BusinessException("用户不存在！");
            }
            TcdtUserDetails tcdtUserDetails = new TcdtUserDetails();
            TcdtUserInfo tcdtUserInfo = new TcdtUserInfo();
            tcdtUserInfo.setUsername(user.getAccount());
            tcdtUserInfo.setPassword(user.getUserPwd());
            tcdtUserInfo.setFgActive(user.getFgActive());
            tcdtUserInfo.setNickName(user.getNickName());
            tcdtUserDetails.setTcdtUserInfo(tcdtUserInfo);
            return tcdtUserDetails;
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

