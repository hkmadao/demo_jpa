package org.hkmadao.tcdt.modules.sysperm.useragg.dao.aggdesc;

import org.hkmadao.core.base.AggBaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.enums.EDataType;

public class UserMDesc extends AggBaseDesc {

    public static final String CLASS_NAME_ENTITY = "User";
    public static final String ID_USER_ATTR = "idUser";
    public static final String ACCOUNT_ATTR = "account";
    public static final String USER_PWD_ATTR = "userPwd";
    public static final String PHONE_ATTR = "phone";
    public static final String EMAIL_ATTR = "email";
    public static final String NAME_ATTR = "name";
    public static final String NICK_NAME_ATTR = "nickName";
    public static final String GENDER_ATTR = "gender";
    public static final String FG_ACTIVE_ATTR = "fgActive";
    public static final String USER_ROLES_ATTR = "userRoles";
    /**
     * 实体描述信息
     */
    @Override
    protected void setEntityInfo() {
        entityInfo = new EntityInfo();
        entityInfo.setName("user");
        entityInfo.setDisplayName("系统用户");
        entityInfo.setClassName(CLASS_NAME_ENTITY);
        entityInfo.setTableName("sys_user");
        entityInfo.setBasePath("org.hkmadao.tcdt.modules.sysperm.useragg");
    }

    /**
     * 主键描述信息
     */
    @Override
    protected void setPkAttributeInfo() {
        pkAttributeInfo = idUserDesc();
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
    }

    @Override
    protected void setAggOne2OneChildren() {
    }

    /**
     * 属性描述信息
     */
    @Override
    protected void setAttributeInfoMap() {
        attributeInfoMap.put(ID_USER_ATTR, idUserDesc());
        attributeInfoMap.put(ACCOUNT_ATTR, accountDesc());
        attributeInfoMap.put(USER_PWD_ATTR, userPwdDesc());
        attributeInfoMap.put(PHONE_ATTR, phoneDesc());
        attributeInfoMap.put(EMAIL_ATTR, emailDesc());
        attributeInfoMap.put(NAME_ATTR, nameDesc());
        attributeInfoMap.put(NICK_NAME_ATTR, nickNameDesc());
        attributeInfoMap.put(GENDER_ATTR, genderDesc());
        attributeInfoMap.put(FG_ACTIVE_ATTR, fgActiveDesc());
        attributeInfoMap.put(USER_ROLES_ATTR, userRolesDesc());
    }
    private final AttributeInfo idUserDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_USER_ATTR);
        attributeInfo.setDisplayName("系统用户id");
        attributeInfo.setColumnName("id_user");
        attributeInfo.setDataType(EDataType.INTERNAL_PK);
        return attributeInfo;
    }
    private final AttributeInfo accountDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ACCOUNT_ATTR);
        attributeInfo.setDisplayName("登录账号 ");
        attributeInfo.setColumnName("account");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo userPwdDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(USER_PWD_ATTR);
        attributeInfo.setDisplayName("用户密码 ");
        attributeInfo.setColumnName("user_pwd");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo phoneDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(PHONE_ATTR);
        attributeInfo.setDisplayName("手机号码");
        attributeInfo.setColumnName("phone");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo emailDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(EMAIL_ATTR);
        attributeInfo.setDisplayName("邮箱");
        attributeInfo.setColumnName("email");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo nameDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(NAME_ATTR);
        attributeInfo.setDisplayName("姓名 ");
        attributeInfo.setColumnName("name");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo nickNameDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(NICK_NAME_ATTR);
        attributeInfo.setDisplayName("昵称");
        attributeInfo.setColumnName("nick_name");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo genderDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(GENDER_ATTR);
        attributeInfo.setDisplayName("性别");
        attributeInfo.setColumnName("gender");
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
    private final AttributeInfo userRolesDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(USER_ROLES_ATTR);
        attributeInfo.setDisplayName("用户角色关系:用户角色关系");
        attributeInfo.setColumnName("");
        attributeInfo.setOutEntityName("UserRole");
        attributeInfo.setOutEntityPKAttributeName("idUserRole");
        attributeInfo.setOutEntityReversalAttributeName("user");
        attributeInfo.setOutEntityIdReversalAttributeName("idUser");
        attributeInfo.setAggRefDescClassName("UserRoleMDesc");
        attributeInfo.setDataType(EDataType.AGG_ARRAY);
        return attributeInfo;
    }
}
