package org.hkmadao.tcdt.modules.sysperm.menu.dao.desc;

import org.hkmadao.core.base.BaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.enums.EDataType;

public class MenuDesc extends BaseDesc {

    public static final String CLASS_NAME_ENTITY = "Menu";
    public static final String ID_MENU_ATTR = "idMenu";
    public static final String NAME_ATTR = "name";
    public static final String DISPLAY_NAME_ATTR = "displayName";
    public static final String FG_SHOW_ATTR = "fgShow";
    public static final String QUERY_ATTR = "query";
    public static final String MENU_TYPE_ATTR = "menuType";
    public static final String FG_ACTIVE_ATTR = "fgActive";
    public static final String WEB_PERMS_ATTR = "webPerms";
    public static final String SERVICE_PERMS_ATTR = "servicePerms";
    public static final String CHILDREN_ATTR = "children";
    public static final String ROLE_MENUS_ATTR = "roleMenus";
    public static final String ID_PARENT_ATTR = "idParent";
    public static final String PARENT_ATTR = "parent";
    /**
     * 实体描述信息
     */
    @Override
    protected void setEntityInfo() {
        entityInfo = new EntityInfo();
        entityInfo.setName("menu");
        entityInfo.setDisplayName("系统菜单");
        entityInfo.setClassName(CLASS_NAME_ENTITY);
        entityInfo.setTableName("sys_menu");
        entityInfo.setBasePath("org.hkmadao.tcdt.modules.sysperm.menu");
    }

    /**
     * 主键描述信息
     */
    @Override
    protected void setPkAttributeInfo() {
        pkAttributeInfo = idMenuDesc();
    }


    /**
     * 获取不在同一个聚合根下的外键Id属性描述
     */
    @Override
    protected void setNormalFkIdAttributeInfos() {
        normalFkIdAttributeInfos.add(idParentDesc());
    }

    /**
     * 获取不在同一个聚合根下的外键属性描述
     */
    @Override
    protected void setNormalFkAttributeInfos() {
        normalFkAttributeInfos.add(parentDesc());
    }

    /**
     * 获取不在同一个聚合根下1:N子属性描述
     */
    @Override
    protected void setNormalChildren() {
        normalChildren.add(childrenDesc());
        normalChildren.add(roleMenusDesc());
    }

    /**
     * 获取不在同一个聚合根下1:1子属性描述
     */
    @Override
    protected void setNormalOne2OneChildren() {
    }

    /**
     * 属性描述信息
     */
    @Override
    protected void setAttributeInfoMap() {
        attributeInfoMap.put(ID_MENU_ATTR, idMenuDesc());
        attributeInfoMap.put(NAME_ATTR, nameDesc());
        attributeInfoMap.put(DISPLAY_NAME_ATTR, displayNameDesc());
        attributeInfoMap.put(FG_SHOW_ATTR, fgShowDesc());
        attributeInfoMap.put(QUERY_ATTR, queryDesc());
        attributeInfoMap.put(MENU_TYPE_ATTR, menuTypeDesc());
        attributeInfoMap.put(FG_ACTIVE_ATTR, fgActiveDesc());
        attributeInfoMap.put(WEB_PERMS_ATTR, webPermsDesc());
        attributeInfoMap.put(SERVICE_PERMS_ATTR, servicePermsDesc());
        attributeInfoMap.put(CHILDREN_ATTR, childrenDesc());
        attributeInfoMap.put(ROLE_MENUS_ATTR, roleMenusDesc());
        attributeInfoMap.put(ID_PARENT_ATTR, idParentDesc());
        attributeInfoMap.put(PARENT_ATTR, parentDesc());
    }
    private final AttributeInfo idMenuDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_MENU_ATTR);
        attributeInfo.setDisplayName("系统菜单id");
        attributeInfo.setColumnName("id_menu");
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
    private final AttributeInfo fgShowDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(FG_SHOW_ATTR);
        attributeInfo.setDisplayName("显示标志");
        attributeInfo.setColumnName("fg_show");
        return attributeInfo;
    }
    private final AttributeInfo queryDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(QUERY_ATTR);
        attributeInfo.setDisplayName("路由参数");
        attributeInfo.setColumnName("query");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo menuTypeDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(MENU_TYPE_ATTR);
        attributeInfo.setDisplayName("菜单类型");
        attributeInfo.setColumnName("menu_type");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo fgActiveDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(FG_ACTIVE_ATTR);
        attributeInfo.setDisplayName("启用标志");
        attributeInfo.setColumnName("fg_active");
        return attributeInfo;
    }
    private final AttributeInfo webPermsDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(WEB_PERMS_ATTR);
        attributeInfo.setDisplayName("前端权限标识");
        attributeInfo.setColumnName("web_perms");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo servicePermsDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(SERVICE_PERMS_ATTR);
        attributeInfo.setDisplayName("后台权限标识");
        attributeInfo.setColumnName("service_perms");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo childrenDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(CHILDREN_ATTR);
        attributeInfo.setDisplayName("下级系统菜单:下级系统菜单");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("Menu");
        attributeInfo.setOutEntityPKAttributeName("idMenu");
        attributeInfo.setOutEntityReversalAttributeName("parent");
        attributeInfo.setOutEntityIdReversalAttributeName("idParent");
        attributeInfo.setDataType(EDataType.ARRAY);
        return attributeInfo;
    }
    private final AttributeInfo roleMenusDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ROLE_MENUS_ATTR);
        attributeInfo.setDisplayName("角色与菜单:角色与菜单");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("RoleMenu");
        attributeInfo.setOutEntityPKAttributeName("idRoleMenu");
        attributeInfo.setOutEntityReversalAttributeName("menu");
        attributeInfo.setOutEntityIdReversalAttributeName("idMenu");
        attributeInfo.setDataType(EDataType.ARRAY);
        return attributeInfo;
    }
    private final AttributeInfo idParentDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_PARENT_ATTR);
        attributeInfo.setDisplayName("上级系统菜单id:上级系统菜单id");
        attributeInfo.setColumnName("id_parent");
        attributeInfo.setOutEntityName("Menu");
        attributeInfo.setOutEntityPKAttributeName("idMenu");
        attributeInfo.setOutEntityReversalAttributeName("children");
        attributeInfo.setDataType(EDataType.INTERNAL_FK);
        attributeInfo.setInnerAttributeName(PARENT_ATTR);
        return attributeInfo;
    }
    private final AttributeInfo parentDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(PARENT_ATTR);
        attributeInfo.setDisplayName("上级系统菜单:上级系统菜单");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("Menu");
        attributeInfo.setOutEntityPKAttributeName("idMenu");
        attributeInfo.setOutEntityReversalAttributeName("children");
        attributeInfo.setDataType(EDataType.REF);
        attributeInfo.setInnerAttributeName(ID_PARENT_ATTR);
        return attributeInfo;
    }
}
