package org.hkmadao.core.advanquery;

import java.util.List;

/**
 * 高级查询分页信息
 */
public class AqPageInfoInput {

    /**
     * 当前页码
     */
    private Integer pageIndex;
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Long totalCount;

    /**
     * 查询条件
     */
    private AqLogicNode logicNode;

    /**
     * 排序设置
     */
    private List<AqOrder> orders;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

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
