package org.hkmadao.tcdt.modules.sysperm.user.dto.chpw;
import org.hkmadao.core.base.BaseEntity;

public class UserPwPO extends BaseEntity {

    /**
     * 登录账号 
     */
    private String username;
    /**
     * 用户密码 
     */
    private String password;
    /**
     * 旧的用户密码
     */
    private String oldPassword;

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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}