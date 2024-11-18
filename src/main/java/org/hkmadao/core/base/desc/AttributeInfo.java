package org.hkmadao.core.base.desc;

import org.hkmadao.core.base.enums.EDataType;

import java.util.List;

/**
 * 属性描述信息
 */
public class AttributeInfo {
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性显示名称
     */
    private String displayName;
    /**
     * 数据库字段名
     */
    private String columnName;
    /**
     * 聚合关系主子实体间存放的引用描述数据的类名，不是聚合关系的不用此字段，只需关注自己的描述数据即可
     */
    private String aggRefDescClassName;
    /**
     * 数据类型
     */
    private EDataType dataType;
    /**
     * 关联的内部属性名称（外键属性，外键引用属性）
     */
    private String innerAttributeName;

    /**
     * 外键字段映射
     */
    private List<JoinInfo> joinInfos;

    /**
     * 外部实体名
     */
    private String outEntityName;

    /**
     * 外部实体主属性名
     */
    private String outEntityPKAttributeName;

    /**
     * 外部实体引用本实体的属性名称
     */
    private String outEntityReversalAttributeName;

    /**
     * 外部实体引用本实体的外键属性名称
     */
    private String outEntityIdReversalAttributeName;

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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAggRefDescClassName() {
        return aggRefDescClassName;
    }

    public void setAggRefDescClassName(String aggRefDescClassName) {
        this.aggRefDescClassName = aggRefDescClassName;
    }

    public EDataType getDataType() {
        return dataType;
    }

    public void setDataType(EDataType dataType) {
        this.dataType = dataType;
    }

    public String getInnerAttributeName() {
        return innerAttributeName;
    }

    public void setInnerAttributeName(String innerAttributeName) {
        this.innerAttributeName = innerAttributeName;
    }

    public List<JoinInfo> getJoinInfos() {
        return joinInfos;
    }

    public void setJoinInfos(List<JoinInfo> joinInfos) {
        this.joinInfos = joinInfos;
    }

    public String getOutEntityName() {
        return outEntityName;
    }

    public void setOutEntityName(String outEntityName) {
        this.outEntityName = outEntityName;
    }

    public String getOutEntityPKAttributeName() {
        return outEntityPKAttributeName;
    }

    public void setOutEntityPKAttributeName(String outEntityPKAttributeName) {
        this.outEntityPKAttributeName = outEntityPKAttributeName;
    }

    public String getOutEntityReversalAttributeName() {
        return outEntityReversalAttributeName;
    }

    public void setOutEntityReversalAttributeName(String outEntityReversalAttributeName) {
        this.outEntityReversalAttributeName = outEntityReversalAttributeName;
    }

    public String getOutEntityIdReversalAttributeName() {
        return outEntityIdReversalAttributeName;
    }

    public void setOutEntityIdReversalAttributeName(String outEntityIdReversalAttributeName) {
        this.outEntityIdReversalAttributeName = outEntityIdReversalAttributeName;
    }
}
