package org.hkmadao.tcdt.modules.sysperm.useragg.dto.agg.po;
import org.hkmadao.core.base.BaseEntity;
public class UserRolePO extends BaseEntity {

    /**
     * 用户角色关系主属性
     */
    private String idUserRole;
    /**
     * 角色id:角色id
     */
    private String idRole;
    /**
     * 系统用户id:系统用户id
     */
    private String idUser;
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
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public RolePO getRole() {
        return role;
    }

    public void setRole(RolePO role) {
        this.role = role;
    }
}
