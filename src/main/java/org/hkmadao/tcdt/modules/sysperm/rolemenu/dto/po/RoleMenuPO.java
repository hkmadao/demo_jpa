package org.hkmadao.tcdt.modules.sysperm.rolemenu.dto.po;
import org.hkmadao.core.base.BaseEntity;
public class RoleMenuPO extends BaseEntity {

    /**
     * 角色与菜单id
     */
    private String idRoleMenu;
    /**
     * 系统菜单id:系统菜单id
     */
    private String idMenu;
    /**
     * 角色id:角色id
     */
    private String idRole;
    /**
     * 系统菜单:系统菜单
     */
    private MenuPO menu;
    /**
     * 角色:角色
     */
    private RolePO role;
    public String getIdRoleMenu() {
        return idRoleMenu;
    }

    public void setIdRoleMenu(String idRoleMenu) {
        this.idRoleMenu = idRoleMenu;
    }
    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
    public MenuPO getMenu() {
        return menu;
    }

    public void setMenu(MenuPO menu) {
        this.menu = menu;
    }
    public RolePO getRole() {
        return role;
    }

    public void setRole(RolePO role) {
        this.role = role;
    }
}