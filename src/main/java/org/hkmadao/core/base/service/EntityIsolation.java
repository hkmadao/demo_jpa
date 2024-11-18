package org.hkmadao.core.base.service;

import org.hkmadao.core.base.BaseEntity;

import java.util.Map;
import java.util.Set;

public class EntityIsolation<T extends BaseEntity> {
    private T mainEntity;
    private Map<String, Set<BaseEntity>> childEntityMap;
    private Map<String, BaseEntity> childOne2OneEntityMap;

    public T getMainEntity() {
        return mainEntity;
    }

    public void setMainEntity(T mainEntity) {
        this.mainEntity = mainEntity;
    }

    public Map<String, Set<BaseEntity>> getChildEntityMap() {
        return childEntityMap;
    }

    public void setChildEntityMap(Map<String, Set<BaseEntity>> childEntityMap) {
        this.childEntityMap = childEntityMap;
    }

    public Map<String, BaseEntity> getChildOne2OneEntityMap() {
        return childOne2OneEntityMap;
    }

    public void setChildOne2OneEntityMap(Map<String, BaseEntity> childOne2OneEntityMap) {
        this.childOne2OneEntityMap = childOne2OneEntityMap;
    }
}
