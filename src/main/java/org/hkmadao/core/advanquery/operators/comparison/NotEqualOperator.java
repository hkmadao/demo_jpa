package org.hkmadao.core.advanquery.operators.comparison;

import org.hkmadao.core.advanquery.AqFilterNode;
import org.hkmadao.core.base.BaseEntity;

import jakarta.persistence.criteria.*;
import java.util.Map;

public class NotEqualOperator implements IOperator {

    private NotEqualOperator() {
    }

    private static NotEqualOperator instance = new NotEqualOperator();

    public static NotEqualOperator getInstance() {
        return instance;
    }

    @Override
    public String getOperatorCode() {
        return "notEqual";
    }

    public <T extends BaseEntity> Predicate createPredicate(Map<String, From<T, T>> fromMap, Root<T> root,
                                                            CriteriaBuilder criteriaBuilder, AqFilterNode aqFilterNode) {
        Predicate predicate;
        String fullFieldName = aqFilterNode.getName();
        //主表数据，无需关联表
        if (!fullFieldName.contains(".")) {
            predicate = criteriaBuilder.notEqual(root.get(aqFilterNode.getName()),
                    aqFilterNode.fetchFirstParam().toString());
            return predicate;
        }
        String fieldName = fullFieldName.substring(fullFieldName.lastIndexOf(".") + 1);
        String joinTableName = fullFieldName.substring(0, fullFieldName.lastIndexOf("."));

        predicate = criteriaBuilder.notEqual(fromMap.get(joinTableName).get(fieldName),
                aqFilterNode.fetchFirstParam().toString());
        return predicate;
    }
}
