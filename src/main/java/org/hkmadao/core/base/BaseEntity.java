package org.hkmadao.core.base;

import org.hkmadao.core.constant.DOStatus;
import jakarta.persistence.Transient;

public abstract class BaseEntity {


    @Transient
    private Integer action = DOStatus.UNCHANGED;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

}
