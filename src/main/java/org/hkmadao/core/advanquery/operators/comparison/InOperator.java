package org.hkmadao.core.advanquery.operators.comparison;

import org.hkmadao.core.advanquery.AqFilterNode;
import org.hkmadao.core.base.BaseEntity;

import jakarta.persistence.criteria.*;
import java.util.Map;

public class InOperator implements IOperator {

    private InOperator() {
    }

    private static InOperator instance = new InOperator();

    public static InOperator getInstance() {
        return instance;
    }

    @Override
    public String getOperatorCode() {
        return "in";
    }

    public <T extends BaseEntity> Predicate createPredicate(Map<String, From<T, T>> fromMap, Root<T> root,
                                                            CriteriaBuilder criteriaBuilder, AqFilterNode aqFilterNode) {
        Predicate predicate;
        String fullFieldName = aqFilterNode.getName();
        //主表数据，无需关联表
        if (!fullFieldName.contains(".")) {

            predicate = root.get(aqFilterNode.getName()).in(aqFilterNode.getFilterParams());
            return predicate;
        }
        String fieldName = fullFieldName.substring(fullFieldName.lastIndexOf(".") + 1);
        String joinTableName = fullFieldName.substring(0, fullFieldName.lastIndexOf("."));

        predicate = fromMap.get(joinTableName).get(fieldName).in(aqFilterNode.getFilterParams());
        return predicate;
    }
}
