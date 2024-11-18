package org.hkmadao.core.advanquery;

import java.util.List;

/**
 * 查询条件节点
 */
public class AqFilterNode {

    private static final long serialVersionUID = 3618267111823976581L;

    /**
     * 查询条件名称
     */
    private String name;

    /**
     * 比较操作符编码
     */
    private String operatorCode;

    /**
     * 查询参数
     */
    private List<Object> filterParams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public List<Object> getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(List<Object> filterParams) {
        this.filterParams = filterParams;
    }

    public Object fetchFirstParam() {
        return filterParams.get(0);
    }

    public Object fetchSecondParam() {
        return filterParams.get(1);
    }
}
