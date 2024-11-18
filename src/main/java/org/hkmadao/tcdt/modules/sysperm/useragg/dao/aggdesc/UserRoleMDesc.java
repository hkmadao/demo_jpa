package org.hkmadao.tcdt.modules.sysperm.useragg.dao.aggdesc;

import java.util.Arrays;
import org.hkmadao.core.base.AggBaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.desc.JoinInfo;
import org.hkmadao.core.base.enums.EDataType;

public class UserRoleMDesc extends AggBaseDesc {

    public static final String CLASS_NAME_ENTITY = "UserRole";
    public static final String ID_USER_ROLE_ATTR = "idUserRole";
    public static final String ID_ROLE_ATTR = "idRole";
    public static final String ROLE_ATTR = "role";
    public static final String ID_USER_ATTR = "idUser";
    public static final String USER_ATTR = "user";
    /**
     * 实体描述信息
     */
    @Override
    protected void setEntityInfo() {
        entityInfo = new EntityInfo();
        entityInfo.setName("userRole");
        entityInfo.setDisplayName("用户角色关系");
        entityInfo.setClassName(CLASS_NAME_ENTITY);
        entityInfo.setTableName("sys_user_role");
        entityInfo.setBasePath("org.hkmadao.tcdt.modules.sysperm.useragg");
    }

    /**
     * 主键描述信息
     */
    @Override
    protected void setPkAttributeInfo() {
        pkAttributeInfo = idUserRoleDesc();
    }


    /**
     * 获取不在同一个聚合根下的外键Id属性描述
     */
    @Override
    protected void setNormalFkIdAttributeInfos() {
        normalFkIdAttributeInfos.add(idRoleDesc());
    }

    /**
     * 获取不在同一个聚合根下的外键属性描述
     */
    @Override
    protected void setNormalFkAttributeInfos() {
        normalFkAttributeInfos.add(roleDesc());
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
        aggFkIdAttributeInfo = idUserDesc();
    }

    /**
     * 获取同一个聚合根下的外键属性描述
     */
    @Override
    protected void setAggFkAttributeInfo() {
        aggFkAttributeInfo = userDesc();
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
        attributeInfoMap.put(ID_USER_ROLE_ATTR, idUserRoleDesc());
        attributeInfoMap.put(ID_ROLE_ATTR, idRoleDesc());
        attributeInfoMap.put(ROLE_ATTR, roleDesc());
        attributeInfoMap.put(ID_USER_ATTR, idUserDesc());
        attributeInfoMap.put(USER_ATTR, userDesc());
    }
    private final AttributeInfo idUserRoleDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_USER_ROLE_ATTR);
        attributeInfo.setDisplayName("用户角色关系主属性");
        attributeInfo.setColumnName("id_sys_user_role");
        attributeInfo.setDataType(EDataType.INTERNAL_PK);
        return attributeInfo;
    }
    private final AttributeInfo idRoleDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_ROLE_ATTR);
        attributeInfo.setDisplayName("角色id:角色id");
        attributeInfo.setColumnName("id_role");
        attributeInfo.setOutEntityName("Role");
        attributeInfo.setOutEntityPKAttributeName("idRole");
        attributeInfo.setOutEntityReversalAttributeName("userRoles");
        attributeInfo.setDataType(EDataType.INTERNAL_FK);
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
        attributeInfo.setOutEntityReversalAttributeName("userRoles");
        attributeInfo.setDataType(EDataType.REF);
        attributeInfo.setInnerAttributeName(ID_ROLE_ATTR);
        return attributeInfo;
    }
    private final AttributeInfo idUserDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_USER_ATTR);
        attributeInfo.setDisplayName("系统用户id:系统用户id");
        attributeInfo.setColumnName("id_user");
        attributeInfo.setOutEntityName("User");
        attributeInfo.setOutEntityPKAttributeName("idUser");
        attributeInfo.setOutEntityReversalAttributeName("userRoles");
        attributeInfo.setAggRefDescClassName("UserMDesc");
        attributeInfo.setDataType(EDataType.AGG_FK);
        attributeInfo.setInnerAttributeName(USER_ATTR);
        return attributeInfo;
    }
    private final AttributeInfo userDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(USER_ATTR);
        attributeInfo.setDisplayName("系统用户:系统用户");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("User");
        attributeInfo.setOutEntityPKAttributeName("idUser");
        attributeInfo.setOutEntityReversalAttributeName("userRoles");
        attributeInfo.setAggRefDescClassName("UserMDesc");
        attributeInfo.setDataType(EDataType.AGG_REF);
        JoinInfo joinInfo = new JoinInfo();
        joinInfo.setJoinName("");
        joinInfo.setSourceName("idUser");
        attributeInfo.setJoinInfos(Arrays.asList(joinInfo));
        attributeInfo.setInnerAttributeName(ID_USER_ATTR);
        return attributeInfo;
    }
}
