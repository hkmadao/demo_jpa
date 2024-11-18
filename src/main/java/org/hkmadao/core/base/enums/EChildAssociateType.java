package org.hkmadao.core.base.enums;

public enum EChildAssociateType {
    ZERO_TO_ONE("zeroToOne"),
    ZERO_TO_MANY("zeroToMany"),
    ONE_TO_ONE("oneToOne"),
    ONE_TO_MANY("oneToMany");

    private String code;

    EChildAssociateType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
