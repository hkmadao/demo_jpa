package org.hkmadao.tcdt.modules.sysperm.menu.dao.entity;

import jakarta.persistence.*;

import java.util.Set;
import org.hibernate.annotations.GenericGenerator;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.tcdt.modules.sysperm.menu.dao.desc.MenuDesc;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.desc.RoleMenuDesc;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.entity.RoleMenu;
@NamedEntityGraph(name = MenuDesc.CLASS_NAME_ENTITY + ".graph"
        ,
        attributeNodes = {
        }
        )
@Entity(name = "sys_menu")
public class Menu extends BaseEntity {

    /**
     * 系统菜单id
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id_menu")
    private String idMenu;
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
     * 显示标志
     */
    @Column(name = "fg_show" , length = 1 )
    private Boolean fgShow;
    /**
     * 路由参数
     */
    @Column(name = "query" , length = 255 )
    private String query;
    /**
     * 菜单类型
     */
    @Column(name = "menu_type" , length = 255 )
    private String menuType;
    /**
     * 启用标志
     */
    @Column(name = "fg_active" , length = 1 )
    private Boolean fgActive;
    /**
     * 前端权限标识
     */
    @Column(name = "web_perms" , length = 255 )
    private String webPerms;
    /**
     * 后台权限标识
     */
    @Column(name = "service_perms" , length = 255 )
    private String servicePerms;
    /**
     * 上级系统菜单id:上级系统菜单id
     */
    @Column(name = "id_parent" , length = 255 )
    private String idParent;
    /**
     * 上级系统菜单:上级系统菜单
     */
    @ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent", insertable = false, updatable = false)
    private Menu parent;
    /**
     * 下级系统菜单:下级系统菜单
     */
    @OneToMany(mappedBy = MenuDesc.PARENT_ATTR, fetch = FetchType.LAZY)
    private Set<Menu> children;
    /**
     * 角色与菜单:角色与菜单
     */
    @OneToMany(mappedBy = RoleMenuDesc.MENU_ATTR, fetch = FetchType.LAZY)
    private Set<RoleMenu> roleMenus;
    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
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
    public Boolean getFgShow() {
        return fgShow;
    }

    public void setFgShow(Boolean fgShow) {
        this.fgShow = fgShow;
    }
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }
    public String getWebPerms() {
        return webPerms;
    }

    public void setWebPerms(String webPerms) {
        this.webPerms = webPerms;
    }
    public String getServicePerms() {
        return servicePerms;
    }

    public void setServicePerms(String servicePerms) {
        this.servicePerms = servicePerms;
    }
    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }
    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
    }
    public Set<RoleMenu> getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(Set<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }
}
