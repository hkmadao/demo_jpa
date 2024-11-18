package org.hkmadao.core.base;

import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;

import java.util.*;

public abstract class BaseDesc {
    /**
     * 实体信息
     */
    protected EntityInfo entityInfo;

    /**
     * 属性信息
     */
    protected Map<String, AttributeInfo> attributeInfoMap = new HashMap<>();

    /**
     * 获取主键属性描述
     */
    protected AttributeInfo pkAttributeInfo;

    /**
     * 获取不在同一个聚合根下的外键Id属性描述
     */
    protected List<AttributeInfo> normalFkIdAttributeInfos = new ArrayList<>();

    /**
     * 获取不在同一个聚合根下的外键属性描述
     */
    protected List<AttributeInfo> normalFkAttributeInfos = new ArrayList<>();

    /**
     * 获取不在同一个聚合根下子属性描述
     */
    protected final List<AttributeInfo> normalChildren = new ArrayList<>();

    /**
     * 1对1情况的子属性
     */
    protected List<AttributeInfo>  normalOne2OneChildren = new ArrayList<>();

    public BaseDesc() {
        setEntityInfo();
        setPkAttributeInfo();
        setAttributeInfoMap();
        setNormalFkIdAttributeInfos();
        setNormalFkAttributeInfos();
        setNormalChildren();
        setNormalOne2OneChildren();
    }

    public EntityInfo getEntityInfo() {
        return entityInfo;
    }

    public Map<String, AttributeInfo> getAttributeInfoMap() {
        return attributeInfoMap;
    }

    public AttributeInfo getPkAttributeInfo() {
        return pkAttributeInfo;
    }

    public List<AttributeInfo> getNormalFkIdAttributeInfos() {
        return normalFkIdAttributeInfos;
    }

    public List<AttributeInfo> getNormalFkAttributeInfos() {
        return normalFkAttributeInfos;
    }

    public List<AttributeInfo> getNormalChildren() {
        return normalChildren;
    }

    public List<AttributeInfo> getNormalOne2OneChildren() {
        return normalOne2OneChildren;
    }

    /**
     * 实体描述信息
     */
    protected abstract void setEntityInfo();

    /**
     * 主键描述信息
     */
    protected abstract void setPkAttributeInfo();

    /**
     * 属性描述信息
     */
    protected abstract void setAttributeInfoMap();

    protected abstract void setNormalFkIdAttributeInfos();

    protected abstract void setNormalFkAttributeInfos();

    protected abstract void setNormalChildren();

    protected abstract void setNormalOne2OneChildren();
}
