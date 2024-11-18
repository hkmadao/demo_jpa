package org.hkmadao.core.base.desc;

/**
 * 关联字段映射描述信息
 */
public class JoinInfo {
    /**
     * 关联属性id
     */
    private String joinId;
    /**
     * 关联属性名称
     */
    private String joinName;
    /**
     * 被关联属性id
     */
    private String sourceId;
    /**
     * 被关联属性名称
     */
    private String sourceName;

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }

    public String getJoinName() {
        return joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
