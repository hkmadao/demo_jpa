package org.hkmadao.tcdt.modules.sysperm.role.dao.entity;

import jakarta.persistence.*;

import java.util.Set;
import org.hibernate.annotations.GenericGenerator;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.tcdt.modules.sysperm.role.dao.desc.RoleDesc;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.desc.RoleMenuDesc;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.entity.RoleMenu;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.desc.UserRoleDesc;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.entity.UserRole;
@NamedEntityGraph(name = RoleDesc.CLASS_NAME_ENTITY + ".graph"
        ,
        attributeNodes = {
        }
        )
@Entity(name = "sys_role")
public class Role extends BaseEntity {

    /**
     * 角色id
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id_role")
    private String idRole;
    /**
     * 名称
     */
    @Column(name = "name" , length = 255 )
    private String name;
    /**
     * 显示名称
     */
    @Column(name = "display_name" , length = 255 )
    private String displayName;
    /**
     * 角色与菜单:角色与菜单
     */
    @OneToMany(mappedBy = RoleMenuDesc.ROLE_ATTR, fetch = FetchType.LAZY)
    private Set<RoleMenu> roleMenus;
    /**
     * 用户角色关系:用户角色关系
     */
    @OneToMany(mappedBy = UserRoleDesc.ROLE_ATTR, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;
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
    public Set<RoleMenu> getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(Set<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
