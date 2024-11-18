package org.hkmadao.core.advanquery;

/**
 * 排序设置
 */
public class AqOrder {
    /**
     * 排序方向
     */
    private String direction;
    /**
     * 排序属性
     */
    private String property;
    /**
     * 是否忽略
     */
    private boolean ignoreCase;
    /**
     * 空值处理
     */
    private String nullHandling;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

    public void setNullHandling(String nullHandling) {
        this.nullHandling = nullHandling;
    }
}
