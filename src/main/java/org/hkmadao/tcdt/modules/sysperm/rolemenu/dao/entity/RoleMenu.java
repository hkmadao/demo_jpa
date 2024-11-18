package org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.desc.RoleMenuDesc;
import org.hkmadao.tcdt.modules.sysperm.menu.dao.entity.Menu;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
@NamedEntityGraph(name = RoleMenuDesc.CLASS_NAME_ENTITY + ".graph"
        )
@Entity(name = "sys_role_menu")
public class RoleMenu extends BaseEntity {

    /**
     * 角色与菜单id
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id_role_menu")
    private String idRoleMenu;
    /**
     * 系统菜单id:系统菜单id
     */
    @Column(name = "id_menu" , length = 255 )
    private String idMenu;
    /**
     * 角色id:角色id
     */
    @Column(name = "id_role" , length = 255 )
    private String idRole;
    /**
     * 系统菜单:系统菜单
     */
    @ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu", insertable = false, updatable = false)
    private Menu menu;
    /**
     * 角色:角色
     */
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private Role role;
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
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
