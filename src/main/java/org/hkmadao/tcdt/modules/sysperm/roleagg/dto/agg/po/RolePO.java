package org.hkmadao.tcdt.modules.sysperm.roleagg.dto.agg.po;
import java.util.List;

import org.hkmadao.core.base.BaseEntity;
public class RolePO extends BaseEntity {

    /**
     * 角色id
     */
    private String idRole;
    /**
     * 名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 用户角色关系:用户角色关系
     */
    private List<UserRolePO> userRoles;
    /**
     * 角色与菜单:角色与菜单
     */
    private List<RoleMenuPO> roleMenus;
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public List<UserRolePO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRolePO> userRoles) {
        this.userRoles = userRoles;
    }
    public List<RoleMenuPO> getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(List<RoleMenuPO> roleMenus) {
        this.roleMenus = roleMenus;
    }
}
