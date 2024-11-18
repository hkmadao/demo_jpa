package org.hkmadao.tcdt.conf.security.info;

import java.util.Date;

public class TcdtUserInfo {
    private String username;
    private String password;
    private Boolean fgActive;
    private String nickName;

    private String token;

    private Date createTime;

    private Date expiredTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
