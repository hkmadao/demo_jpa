package org.hkmadao.core.base;

import java.util.HashSet;
import java.util.Set;

/**
 * dto类型树
 */
public class DtoClazzTree {
    /**
     * 对象类型的id属性名称
     */
    private String idName;
    /**
     * 属性名称
     */
    private String fieldName;
    /**
     * 属性是否忽略处理
     */
    private boolean fgIgnore;
    /**
     * 属性类型
     */
    private Class dtoClazz;
    /**
     * 基本属性类型
     */
    private Set<DtoClazzTree> baseClazzSet = new HashSet<>();
    /**
     * 实体属性的类型
     */
    private Set<DtoClazzTree> refDtoClazzSet = new HashSet<>();
    /**
     * 集合属性的类型
     */
    private Set<DtoClazzTree> childrenDtoClazzSet = new HashSet<>();

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getDtoClazz() {
        return dtoClazz;
    }

    public void setDtoClazz(Class dtoClazz) {
        this.dtoClazz = dtoClazz;
    }

    public Set<DtoClazzTree> getRefDtoClazzSet() {
        return refDtoClazzSet;
    }

    public void setRefDtoClazzSet(Set<DtoClazzTree> refDtoClazzSet) {
        this.refDtoClazzSet = refDtoClazzSet;
    }

    public Set<DtoClazzTree> getChildrenDtoClazzSet() {
        return childrenDtoClazzSet;
    }

    public void setChildrenDtoClazzSet(Set<DtoClazzTree> childrenDtoClazzSet) {
        this.childrenDtoClazzSet = childrenDtoClazzSet;
    }

    public Set<DtoClazzTree> getBaseClazzSet() {
        return baseClazzSet;
    }

    public void setBaseClazzSet(Set<DtoClazzTree> baseClazzSet) {
        this.baseClazzSet = baseClazzSet;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public boolean isFgIgnore() {
        return fgIgnore;
    }

    public void setFgIgnore(boolean fgIgnore) {
        this.fgIgnore = fgIgnore;
    }
}
