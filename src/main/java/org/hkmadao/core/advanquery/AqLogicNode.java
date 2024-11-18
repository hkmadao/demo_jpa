package org.hkmadao.core.advanquery;

import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.advanquery.operators.comparison.OperatorFactor;
import org.hkmadao.core.advanquery.operators.comparison.IOperator;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.*;
import java.util.*;

/**
 * 逻辑操作节点
 */
public class AqLogicNode {

    private static final long serialVersionUID = 172664176611927583L;

    public static String AND = "and";
    public static String OR = "or";

    /**
     * 逻辑操作编码
     */
    private String logicOperatorCode;

    /**
     * 子节点
     */
    private AqLogicNode logicNode;

    /**
     * 查询条件集合
     */
    private List<AqFilterNode> filterNodes;

    public String getLogicOperatorCode() {
        return logicOperatorCode;
    }

    public void setLogicOperatorCode(String logicOperatorCode) {
        this.logicOperatorCode = logicOperatorCode;
    }

    public AqLogicNode getLogicNode() {
        return logicNode;
    }

    public void setLogicNode(AqLogicNode logicNode) {
        this.logicNode = logicNode;
    }

    public List<AqFilterNode> getFilterNodes() {
        return filterNodes;
    }

    public void setFilterNodes(List<AqFilterNode> filterNodes) {
        this.filterNodes = filterNodes;
    }

    /**
     * 动态生成查询条件
     *
     * @param joinMap         连表信息
     * @param root            主表信息
     * @param criteriaBuilder 条件构建器
     * @param aqLogicNode     当前查询树子节点
     * @return
     */
    public static <T extends BaseEntity> List<Predicate> recursionNode(Map<String, From<T, T>> joinMap,
                                                                       Root<T> root,
                                                                       CriteriaBuilder criteriaBuilder,
                                                                       AqLogicNode aqLogicNode) {
        if (Objects.isNull(aqLogicNode) || !StringUtils.hasLength(aqLogicNode.getLogicOperatorCode())) {
            return new ArrayList<>();
        }
        List<Predicate> predicates = new ArrayList<>();

        aqLogicNode.getFilterNodes().forEach(aqFilterNode -> {
                    IOperator operator = OperatorFactor.getOperator(aqFilterNode.getOperatorCode());
                    predicates.add(operator.createPredicate(joinMap, root, criteriaBuilder, aqFilterNode));
                }
        );

        if (Objects.nonNull(aqLogicNode.getLogicNode())) {
            //子查询条件
            List<Predicate> predicates1 = recursionNode(joinMap, root, criteriaBuilder, aqLogicNode.getLogicNode());
            predicates.addAll(predicates1);
        }

        if (AqLogicNode.AND.equals(aqLogicNode.getLogicOperatorCode())) {
            return Arrays.asList(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
        }
        return Arrays.asList(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
    }
}
