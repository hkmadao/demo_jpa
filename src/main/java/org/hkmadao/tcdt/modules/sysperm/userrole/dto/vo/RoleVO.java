package org.hkmadao.tcdt.modules.sysperm.userrole.dto.vo;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.base.DtoIdentify;

public class RoleVO extends BaseEntity {

    /**
     * 角色id
     */
    @DtoIdentify
    private String idRole;
    /**
     * 名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String displayName;
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
}
