package org.hkmadao.tcdt.modules.sysperm.roleagg.dto.agg.po;
import org.hkmadao.core.base.BaseEntity;
public class MenuPO extends BaseEntity {

    /**
     * 系统菜单id
     */
    private String idMenu;
    /**
     * 名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 显示标志
     */
    private Boolean fgShow;
    /**
     * 路由参数
     */
    private String query;
    /**
     * 菜单类型
     */
    private String menuType;
    /**
     * 启用标志
     */
    private Boolean fgActive;
    /**
     * 前端权限标识
     */
    private String webPerms;
    /**
     * 后台权限标识
     */
    private String servicePerms;
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
}
