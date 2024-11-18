package org.hkmadao.core.base.enums;

public enum EDataType {
    /**
     * 主键
     */
    INTERNAL_PK("InternalPK"),
    /**
     * 外键
     */
    INTERNAL_FK("InternalFK"),
    /**
     * 1对多关系 子实体的引用属性
     */
    REF("InternalRef"),
    /**
     * 1对1关系 子实体的引用属性
     */
    SINGLE_REF("InternalSingleRef"),
    /**
     * 1对1关系 主实体的子属性
     */
    SINGLE("InternalSingle"),
    /**
     * 1对多关系 主实体的子属性
     */
    ARRAY("InternalArray"),
    /**
     * agg 外键
     */
    AGG_FK("InternalAggFK"),
    /**
     * 1对多关系 子实体的引用属性
     */
    AGG_REF("InternalAggRef"),
    /**
     * agg 1对1关系 子实体的引用属性
     */
    AGG_SINGLE_REF("InternalAggSingleRef"),
    /**
     * agg 1对1关系 主实体的子属性
     */
    AGG_SINGLE("InternalAggSingle"),
    /**
     * agg 1对多关系 主实体的子属性
     */
    AGG_ARRAY("InternalAggArray"),

    STRING("String"),
    INTEGER("Integer"),
    LONG("Long"),
    BOOLEAN("Boolean"),
    FLOAT("Float"),
    DOUBLE("Double"),
    BIG_DECIMAL("BigDecimal"),
    TIMESTAMP("Timestamp"),
    DATE("Date"),
    TIME("Time"),
    LOB("Lob"),
    CLOB("Clob"),
    BLOB("Blob"),
    ;

    private String code;

    EDataType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
