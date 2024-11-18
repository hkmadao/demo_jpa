package org.hkmadao.core.base.rule;

import org.hkmadao.core.base.BusinessException;

import java.util.ArrayList;
import java.util.List;

public interface IRule<T> {
    T process(T entity) throws BusinessException;

    default List<T> process(List<T> entities) throws BusinessException{
        List<T> newList = new ArrayList<>();
        for (T entity : entities) {
            newList.add(this.process(entity));
        }
        return newList;
    }
}
