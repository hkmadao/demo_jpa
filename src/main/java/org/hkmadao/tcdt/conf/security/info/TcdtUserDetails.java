package org.hkmadao.tcdt.conf.security.info;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * springframework.security需要使用的用户信息
 */
public class TcdtUserDetails implements UserDetails {

    private TcdtUserInfo tcdtUserInfo;

    //获取用户的权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //TODO 权限
//        for (Role role : roleList) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return tcdtUserInfo.getFgActive();
    }

    @Override
    public String getUsername() {
        return tcdtUserInfo.getUsername();
    }

    @Override
    public String getPassword() {
        return tcdtUserInfo.getPassword();
    }

    public TcdtUserInfo getTcdtUserInfo() {
        return tcdtUserInfo;
    }

    public void setTcdtUserInfo(TcdtUserInfo tcdtUserInfo) {
        this.tcdtUserInfo = tcdtUserInfo;
    }
}