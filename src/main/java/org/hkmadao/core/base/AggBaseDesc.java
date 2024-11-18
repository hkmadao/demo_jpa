package org.hkmadao.core.base;

import org.hkmadao.core.base.desc.AttributeInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class AggBaseDesc extends BaseDesc {

    /**
     * 获取同一个聚合根下的外键Id属性描述
     */
    protected AttributeInfo aggFkIdAttributeInfo;

    /**
     * 获取同一个聚合根下的外键属性描述
     */
    protected AttributeInfo aggFkAttributeInfo;

    /**
     * 获取同一个聚合根下子属性描述(在一个聚合内的属性，和聚合根一起保存，非一个聚合内的不算在子属下范围内)
     */
    protected final List<AttributeInfo> aggChildren = new ArrayList<>();

    /**
     * 1对1情况的子属性
     */
    protected List<AttributeInfo> aggOne2OneChildren = new ArrayList<>();

    public AggBaseDesc() {
       super();
        setAggFkIdAttributeInfo();
        setAggFkAttributeInfo();
        setAggChildren();
        setAggOne2OneChildren();
    }

    public AttributeInfo getAggFkIdAttributeInfo() {
        return aggFkIdAttributeInfo;
    }

    public AttributeInfo getAggFkAttributeInfo() {
        return aggFkAttributeInfo;
    }

    public List<AttributeInfo> getAggChildren() {
        return aggChildren;
    }

    public List<AttributeInfo> getAggOne2OneChildren() {
        return aggOne2OneChildren;
    }

    protected abstract void setAggFkIdAttributeInfo();

    protected abstract void setAggFkAttributeInfo();

    protected abstract void setAggChildren();

    protected abstract void setAggOne2OneChildren();
}
