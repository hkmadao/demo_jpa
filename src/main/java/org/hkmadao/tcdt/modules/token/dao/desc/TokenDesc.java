package org.hkmadao.tcdt.modules.token.dao.desc;

import org.hkmadao.core.base.BaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.enums.EDataType;

public class TokenDesc extends BaseDesc {

    public static final String CLASS_NAME_ENTITY = "Token";
    public static final String ID_TOKEN_ATTR = "idToken";
    public static final String USERNAME_ATTR = "username";
    public static final String NICK_NAME_ATTR = "nickName";
    public static final String CREATE_TIME_ATTR = "createTime";
    public static final String TOKEN_ATTR = "token";
    public static final String EXPIRED_TIME_ATTR = "expiredTime";
    public static final String USER_INFO_STRING_ATTR = "userInfoString";
    /**
     * 实体描述信息
     */
    @Override
    protected void setEntityInfo() {
        entityInfo = new EntityInfo();
        entityInfo.setName("token");
        entityInfo.setDisplayName("令牌");
        entityInfo.setClassName(CLASS_NAME_ENTITY);
        entityInfo.setTableName("sys_token");
        entityInfo.setBasePath("org.hkmadao.tcdt.modules.token");
    }

    /**
     * 主键描述信息
     */
    @Override
    protected void setPkAttributeInfo() {
        pkAttributeInfo = idTokenDesc();
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
     * 属性描述信息
     */
    @Override
    protected void setAttributeInfoMap() {
        attributeInfoMap.put(ID_TOKEN_ATTR, idTokenDesc());
        attributeInfoMap.put(USERNAME_ATTR, usernameDesc());
        attributeInfoMap.put(NICK_NAME_ATTR, nickNameDesc());
        attributeInfoMap.put(CREATE_TIME_ATTR, createTimeDesc());
        attributeInfoMap.put(TOKEN_ATTR, tokenDesc());
        attributeInfoMap.put(EXPIRED_TIME_ATTR, expiredTimeDesc());
        attributeInfoMap.put(USER_INFO_STRING_ATTR, userInfoStringDesc());
    }
    private final AttributeInfo idTokenDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(ID_TOKEN_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("id_sys_token");
        attributeInfo.setDataType(EDataType.INTERNAL_PK);
        return attributeInfo;
    }
    private final AttributeInfo usernameDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(USERNAME_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("username");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo nickNameDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(NICK_NAME_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("nick_name");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo createTimeDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(CREATE_TIME_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("create_time");
        return attributeInfo;
    }
    private final AttributeInfo tokenDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(TOKEN_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("token");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
    private final AttributeInfo expiredTimeDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(EXPIRED_TIME_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("expired_time");
        return attributeInfo;
    }
    private final AttributeInfo userInfoStringDesc() {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setName(USER_INFO_STRING_ATTR);
        attributeInfo.setDisplayName("");
        attributeInfo.setColumnName("user_info_string");
        attributeInfo.setDataType(EDataType.STRING);
        return attributeInfo;
    }
}
