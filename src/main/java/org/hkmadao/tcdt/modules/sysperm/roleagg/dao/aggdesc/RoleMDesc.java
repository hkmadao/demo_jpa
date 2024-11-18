package org.hkmadao.tcdt.modules.sysperm.roleagg.dao.aggdesc;

import org.hkmadao.core.base.AggBaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.enums.EDataType;

public class RoleMDesc extends AggBaseDesc {

    public static final String CLASS_NAME_ENTITY = "Role";
    public static final String ID_ROLE_ATTR = "idRole";
    public static final String NAME_ATTR = "name";
    public static final String DISPLAY_NAME_ATTR = "displayName";
    public static final String USER_ROLES_ATTR = "userRoles";
    public static final String ROLE_MENUS_ATTR = "roleMenus";
    /**
     * 实体描述信息
     */
    @Override
    protected void setEntityInfo() {
        entityInfo = new EntityInfo();
        entityInfo.setName("role");
        entityInfo.setDisplayName("角色");
        entityInfo.setClassName(CLASS_NAME_ENTITY);
        entityInfo.setTableName("sys_role");
        entityInfo.setBasePath("org.hkmadao.tcdt.modules.sysperm.roleagg");
    }

    /**
     * 主键描述信息
     */
    @Override
    protected void setPkAttributeInfo() {
        pkAttributeInfo = idRoleDesc();
    }


    /**
     * 获取不在同一个聚合根下的外键Id属性描述
     */
    @Override
    protected void setNormalFkIdAttributeInfos() {
    }

    /**
     * 获取不在同一个聚合根下的外键属性描述
     */
    @Override
    protected void setNormalFkAttributeInfos() {
    }

    /**
     * 获取不在同一个聚合根下1:N子属性描述
     */
    @Override
    protected void setNormalChildren() {
    }

    /**
     * 获取不在同一个聚合根下1:1子属性描述
     */
    @Override
    protected void setNormalOne2OneChildren() {
    }

    /**
     * 获取同一个聚合根下的外键Id属性描述
     */
    @Override
    protected void setAggFkIdAttributeInfo() {
    }

    /**
     * 获取同一个聚合根下的外键属性描述
     */
    @Override
    protected void setAggFkAttributeInfo() {
    }

    /**
     * 获取同一个聚合根下子属性描述(在一个聚合内的属性，和聚合根一起保存，非一个聚合内的不算在子属下范围内)
     */
    @Override
    protected void setAggChildren() {
        aggChildren.add(userRolesDesc());
        aggChildren.add(roleMenusDesc());
    }

    @Override
    protected void setAggOne2OneChildren() {
    }

    /**
     * 属性描述信息
     */
    @Override
    protected void setAttributeInfoMap() {
        attributeInfoMap.put(ID_ROLE_ATTR, idRoleDesc());
        attributeInfoMap.put(NAME_ATTR, nameDesc());
        attributeInfoMap.put(DISPLAY_NAME_ATTR, displayNameDesc());
        attributeInfoMap.put(USER_ROLES_ATTR, userRolesDesc());
        attributeInfoMap.put(ROLE_MENUS_ATTR, roleMenusDesc());
    }
    private final AttributeInfo idRoleDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_ROLE_ATTR);
        attributeInfo.setDisplayName("角色id");
        attributeInfo.setColumnName("id_role");
        attributeInfo.setDataType(EDataType.INTERNAL_PK);
        return attributeInfo;
    }
    private final AttributeInfo nameDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(NAME_ATTR);
        attributeInfo.setDisplayName("名称");
        attributeInfo.setColumnName("name");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo displayNameDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(DISPLAY_NAME_ATTR);
        attributeInfo.setDisplayName("显示名称");
        attributeInfo.setColumnName("display_name");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo userRolesDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(USER_ROLES_ATTR);
        attributeInfo.setDisplayName("用户角色关系:用户角色关系");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("UserRole");
        attributeInfo.setOutEntityPKAttributeName("idUserRole");
        attributeInfo.setOutEntityReversalAttributeName("role");
        attributeInfo.setOutEntityIdReversalAttributeName("idRole");
        attributeInfo.setAggRefDescClassName("UserRoleMDesc");
        attributeInfo.setDataType(EDataType.AGG_ARRAY);
        return attributeInfo;
    }
    private final AttributeInfo roleMenusDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ROLE_MENUS_ATTR);
        attributeInfo.setDisplayName("角色与菜单:角色与菜单");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("RoleMenu");
        attributeInfo.setOutEntityPKAttributeName("idRoleMenu");
        attributeInfo.setOutEntityReversalAttributeName("role");
        attributeInfo.setOutEntityIdReversalAttributeName("idRole");
        attributeInfo.setAggRefDescClassName("RoleMenuMDesc");
        attributeInfo.setDataType(EDataType.AGG_ARRAY);
        return attributeInfo;
    }
}
