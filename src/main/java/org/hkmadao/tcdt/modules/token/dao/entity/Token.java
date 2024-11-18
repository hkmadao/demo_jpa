package org.hkmadao.tcdt.modules.token.dao.entity;

import jakarta.persistence.*;

import java.sql.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.tcdt.modules.token.dao.desc.TokenDesc;

@NamedEntityGraph(name = TokenDesc.CLASS_NAME_ENTITY + ".graph"
)
@Entity(name = "sys_token")
public class Token extends BaseEntity {

    /**
     * 令牌主属性
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id_sys_token")
    private String idToken;
    /**
     * 用户名称
     */
    @Column(name = "username")
    private String username;
    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 令牌
     */
    @Column(name = "token", length = 4000)
    private String token;
    /**
     * 过期时间
     */
    @Column(name = "expired_time")
    private Date expiredTime;
    /**
     * 用户信息序列化
     */
    @Column(name = "user_info_string")
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
