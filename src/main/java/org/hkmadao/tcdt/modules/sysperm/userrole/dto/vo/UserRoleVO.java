package org.hkmadao.tcdt.modules.sysperm.userrole.dto.vo;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.base.DtoIdentify;

public class UserRoleVO extends BaseEntity {

    /**
     * 用户角色关系主属性
     */
    @DtoIdentify
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
    private UserVO user;
    /**
     * 角色:角色
     */
    private RoleVO role;
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
    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
    public RoleVO getRole() {
        return role;
    }

    public void setRole(RoleVO role) {
        this.role = role;
    }
}
