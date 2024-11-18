package org.hkmadao.core.advanquery.operators.comparison;

import org.hkmadao.core.advanquery.AqFilterNode;
import org.hkmadao.core.base.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Map;

public class LikeLeftOperator implements IOperator {

    private LikeLeftOperator() {
    }

    private static LikeLeftOperator instance = new LikeLeftOperator();

    public static LikeLeftOperator getInstance() {
        return instance;
    }

    @Override
    public String getOperatorCode() {
        return "likeLeft";
    }

    public <T extends BaseEntity> Predicate createPredicate(Map<String, From<T, T>> fromMap, Root<T> root,
                                                            CriteriaBuilder criteriaBuilder,
                                                            AqFilterNode aqFilterNode) {
        Predicate predicate;
        String fullFieldName = aqFilterNode.getName();
        //主表数据，无需关联表
        if (!fullFieldName.contains(".")) {
            predicate = criteriaBuilder.like(root.get(aqFilterNode.getName()), "%" +
                    aqFilterNode.fetchFirstParam().toString());
            return predicate;
        }
        String fieldName = fullFieldName.substring(fullFieldName.lastIndexOf(".") + 1);
        String joinTableName = fullFieldName.substring(0, fullFieldName.lastIndexOf("."));

        predicate = criteriaBuilder.like(fromMap.get(joinTableName).get(fieldName), "%" +
                aqFilterNode.fetchFirstParam().toString());
        return predicate;
    }
}
