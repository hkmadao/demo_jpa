package org.hkmadao.core.advanquery.operators.comparison;

import org.hkmadao.core.advanquery.AqFilterNode;
import org.hkmadao.core.base.BaseEntity;

import jakarta.persistence.criteria.*;
import java.util.Map;

public class NullOperator implements IOperator {

    private NullOperator() {
    }

    private static NullOperator instance = new NullOperator();

    public static NullOperator getInstance() {
        return instance;
    }

    @Override
    public String getOperatorCode() {
        return "isNull";
    }

    @Override
    public <T extends BaseEntity> Predicate createPredicate(Map<String, From<T, T>> fromMap, Root<T> root,
                                                            CriteriaBuilder criteriaBuilder, AqFilterNode aqFilterNode) {
        Predicate predicate;
        String fullFieldName = aqFilterNode.getName();
        //主表数据，无需关联表
        if (!fullFieldName.contains(".")) {
            predicate = criteriaBuilder.isNull(root.get(aqFilterNode.getName()));
            return predicate;
        }
        String fieldName = fullFieldName.substring(fullFieldName.lastIndexOf(".") + 1);
        String joinTableName = fullFieldName.substring(0, fullFieldName.lastIndexOf("."));

        predicate = criteriaBuilder.isNull(fromMap.get(joinTableName).get(fieldName));
        return predicate;
    }
}
