package org.hkmadao.tcdt.modules.sysperm.roleagg.dao.aggdesc;

import java.util.Arrays;
import org.hkmadao.core.base.AggBaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.desc.JoinInfo;
import org.hkmadao.core.base.enums.EDataType;

public class RoleMenuMDesc extends AggBaseDesc {

    public static final String CLASS_NAME_ENTITY = "RoleMenu";
    public static final String ID_ROLE_MENU_ATTR = "idRoleMenu";
    public static final String ID_MENU_ATTR = "idMenu";
    public static final String MENU_ATTR = "menu";
    public static final String ID_ROLE_ATTR = "idRole";
    public static final String ROLE_ATTR = "role";
    /**
     * 实体描述信息
     */
    @Override
    protected void setEntityInfo() {
        entityInfo = new EntityInfo();
        entityInfo.setName("roleMenu");
        entityInfo.setDisplayName("角色与菜单");
        entityInfo.setClassName(CLASS_NAME_ENTITY);
        entityInfo.setTableName("sys_role_menu");
        entityInfo.setBasePath("org.hkmadao.tcdt.modules.sysperm.roleagg");
    }

    /**
     * 主键描述信息
     */
    @Override
    protected void setPkAttributeInfo() {
        pkAttributeInfo = idRoleMenuDesc();
    }


    /**
     * 获取不在同一个聚合根下的外键Id属性描述
     */
    @Override
    protected void setNormalFkIdAttributeInfos() {
        normalFkIdAttributeInfos.add(idMenuDesc());
    }

    /**
     * 获取不在同一个聚合根下的外键属性描述
     */
    @Override
    protected void setNormalFkAttributeInfos() {
        normalFkAttributeInfos.add(menuDesc());
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
        aggFkIdAttributeInfo = idRoleDesc();
    }

    /**
     * 获取同一个聚合根下的外键属性描述
     */
    @Override
    protected void setAggFkAttributeInfo() {
        aggFkAttributeInfo = roleDesc();
    }

    /**
     * 获取同一个聚合根下子属性描述(在一个聚合内的属性，和聚合根一起保存，非一个聚合内的不算在子属下范围内)
     */
    @Override
    protected void setAggChildren() {
    }

    @Override
    protected void setAggOne2OneChildren() {
    }

    /**
     * 属性描述信息
     */
    @Override
    protected void setAttributeInfoMap() {
        attributeInfoMap.put(ID_ROLE_MENU_ATTR, idRoleMenuDesc());
        attributeInfoMap.put(ID_MENU_ATTR, idMenuDesc());
        attributeInfoMap.put(MENU_ATTR, menuDesc());
        attributeInfoMap.put(ID_ROLE_ATTR, idRoleDesc());
        attributeInfoMap.put(ROLE_ATTR, roleDesc());
    }
    private final AttributeInfo idRoleMenuDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_ROLE_MENU_ATTR);
        attributeInfo.setDisplayName("角色与菜单id");
        attributeInfo.setColumnName("id_role_menu");
        attributeInfo.setDataType(EDataType.INTERNAL_PK);
        return attributeInfo;
    }
    private final AttributeInfo idMenuDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_MENU_ATTR);
        attributeInfo.setDisplayName("系统菜单id:系统菜单id");
        attributeInfo.setColumnName("id_menu");
        attributeInfo.setOutEntityName("Menu");
        attributeInfo.setOutEntityPKAttributeName("idMenu");
        attributeInfo.setOutEntityReversalAttributeName("roleMenus");
        attributeInfo.setDataType(EDataType.INTERNAL_FK);
        attributeInfo.setInnerAttributeName(MENU_ATTR);
        return attributeInfo;
    }
    private final AttributeInfo menuDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(MENU_ATTR);
        attributeInfo.setDisplayName("系统菜单:系统菜单");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("Menu");
        attributeInfo.setOutEntityPKAttributeName("idMenu");
        attributeInfo.setOutEntityReversalAttributeName("roleMenus");
        attributeInfo.setDataType(EDataType.REF);
        attributeInfo.setInnerAttributeName(ID_MENU_ATTR);
        return attributeInfo;
    }
    private final AttributeInfo idRoleDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_ROLE_ATTR);
        attributeInfo.setDisplayName("角色id:角色id");
        attributeInfo.setColumnName("id_role");
        attributeInfo.setOutEntityName("Role");
        attributeInfo.setOutEntityPKAttributeName("idRole");
        attributeInfo.setOutEntityReversalAttributeName("roleMenus");
        attributeInfo.setAggRefDescClassName("RoleMDesc");
        attributeInfo.setDataType(EDataType.AGG_FK);
        attributeInfo.setInnerAttributeName(ROLE_ATTR);
        return attributeInfo;
    }
    private final AttributeInfo roleDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ROLE_ATTR);
        attributeInfo.setDisplayName("角色:角色");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("Role");
        attributeInfo.setOutEntityPKAttributeName("idRole");
        attributeInfo.setOutEntityReversalAttributeName("roleMenus");
        attributeInfo.setAggRefDescClassName("RoleMDesc");
        attributeInfo.setDataType(EDataType.AGG_REF);
        JoinInfo joinInfo = new JoinInfo();
        joinInfo.setJoinName("");
        joinInfo.setSourceName("idRole");
        attributeInfo.setJoinInfos(Arrays.asList(joinInfo));
        attributeInfo.setInnerAttributeName(ID_ROLE_ATTR);
        return attributeInfo;
    }
}
