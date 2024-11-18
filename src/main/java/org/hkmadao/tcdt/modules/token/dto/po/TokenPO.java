package org.hkmadao.tcdt.modules.token.dto.po;
import java.sql.Date;

import org.hkmadao.core.base.BaseEntity;
public class TokenPO extends BaseEntity {

    /**
     * 令牌主属性
     */
    private String idToken;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 令牌
     */
    private String token;
    /**
     * 过期时间
     */
    private Date expiredTime;
    /**
     * 用户信息序列化
     */
    private String userInfoString;
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
    public String getUserInfoString() {
        return userInfoString;
    }

    public void setUserInfoString(String userInfoString) {
        this.userInfoString = userInfoString;
    }
}