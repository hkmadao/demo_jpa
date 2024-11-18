package org.hkmadao.core.base.rule;

import org.hkmadao.core.base.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class RuleManager<T> {
    private List<IRule<T>> beforeRules = new ArrayList<>();
    private List<IRule<T>> afterRules = new ArrayList<>();

    public void addBefore(IRule<T> rule) {
        this.beforeRules.add(rule);
    }

    public void addAfter(IRule<T> rule) {
        this.afterRules.add(rule);
    }

    public T before(T entity) throws BusinessException {
        T end = entity;
        for (IRule<T> rule : beforeRules) {
            end = rule.process(entity);
        }
        return end;
    }

    public T after(T entity) throws BusinessException {
        T end = entity;
        for (IRule<T> rule : afterRules) {
            end = rule.process(entity);
        }
        return end;
    }

    public List<T> after(List<T> entities) throws BusinessException {
        List<T> ends = entities;
        for (IRule<T> rule : afterRules) {
            ends = rule.process(entities);
        }
        return ends;
    }
}
