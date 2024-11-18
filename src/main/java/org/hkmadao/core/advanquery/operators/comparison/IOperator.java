package org.hkmadao.core.advanquery.operators.comparison;

import org.hkmadao.core.advanquery.AqFilterNode;
import org.hkmadao.core.base.BaseEntity;

import jakarta.persistence.criteria.*;
import java.io.Serializable;
import java.util.Map;

/**
 * 运算操作符接口
 */
public interface IOperator extends Serializable {

    /**
     * 获取查询条件比较符号编码
     *
     * @return 比较符号编码
     */
    String getOperatorCode();

    /**
     * 生成某个查询条件
     *
     * @param fromMap         连表信息
     * @param root            主表信息
     * @param criteriaBuilder 条件构建器
     * @param aqFilterNode    条件信息
     * @return
     */
    <T extends BaseEntity> Predicate createPredicate(Map<String, From<T, T>> fromMap, Root<T> root,
                                                     CriteriaBuilder criteriaBuilder, AqFilterNode aqFilterNode);
}
