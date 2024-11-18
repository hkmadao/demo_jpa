package org.hkmadao.tcdt.modules.sysperm.userrole.dto.po;
import org.hkmadao.core.base.BaseEntity;
public class UserRolePO extends BaseEntity {

    /**
     * 用户角色关系主属性
     */
    private String idUserRole;
    /**
     * 系统用户id:系统用户id
     */
    private String idUser;
    /**
     * 角色id:角色id
     */
    private String idRole;
    /**
     * 系统用户:系统用户
     */
    private UserPO user;
    /**
     * 角色:角色
     */
    private RolePO role;
    public String getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(String idUserRole) {
        this.idUserRole = idUserRole;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }
    public RolePO getRole() {
        return role;
    }

    public void setRole(RolePO role) {
        this.role = role;
    }
}