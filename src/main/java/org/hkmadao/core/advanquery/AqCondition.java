package org.hkmadao.core.advanquery;

import java.util.List;

/**
 * 高级查询条件
 */
public class AqCondition {
    /**
     * 查询条件
     */
    private AqLogicNode logicNode;
    /**
     * 分页信息
     */
    private List<AqOrder> orders;

    public AqLogicNode getLogicNode() {
        return logicNode;
    }

    public void setLogicNode(AqLogicNode logicNode) {
        this.logicNode = logicNode;
    }

    public List<AqOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<AqOrder> orders) {
        this.orders = orders;
    }
}
