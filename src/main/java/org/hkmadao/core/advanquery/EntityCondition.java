package org.hkmadao.core.advanquery;

import org.hkmadao.core.base.BaseEntity;

import java.util.List;

/**
 * 带排序的条件类
 *
 * @param <T>
 */
public class EntityCondition<T extends BaseEntity> {
    private T entity;
    private List<AqOrder> orders;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public List<AqOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<AqOrder> orders) {
        this.orders = orders;
    }
}
