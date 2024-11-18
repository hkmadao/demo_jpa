package org.hkmadao.core.advanquery.operators.comparison;

import org.hkmadao.core.advanquery.AqFilterNode;
import org.hkmadao.core.base.BaseEntity;

import jakarta.persistence.criteria.*;
import java.util.Map;

public class BetweenOperator implements IOperator {

    private static BetweenOperator instance = new BetweenOperator();

    private BetweenOperator() {
    }

    public static BetweenOperator getInstance() {
        return instance;
    }

    @Override
    public String getOperatorCode() {
        return "between";
    }

    public <T extends BaseEntity> Predicate createPredicate(Map<String, From<T, T>> fromMap, Root<T> root,
                                                            CriteriaBuilder criteriaBuilder, AqFilterNode aqFilterNode) {
        Predicate predicate;
        String fullFieldName = aqFilterNode.getName();
        //主表数据，无需关联表
        if (!fullFieldName.contains(".")) {
            predicate = criteriaBuilder.between(root.get(aqFilterNode.getName()),
                    aqFilterNode.fetchFirstParam().toString(), aqFilterNode.fetchSecondParam().toString());
            return predicate;
        }
        String fieldName = fullFieldName.substring(fullFieldName.lastIndexOf(".") + 1);
        String joinTableName = fullFieldName.substring(0, fullFieldName.lastIndexOf("."));

        predicate = criteriaBuilder.between(fromMap.get(joinTableName).get(fieldName),
                aqFilterNode.fetchFirstParam().toString(), aqFilterNode.fetchSecondParam().toString());
        return predicate;
    }
}
