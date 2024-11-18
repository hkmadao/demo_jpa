package org.hkmadao.core.base.enums;

public enum EParentAssociateType {
    ONE("one"),
    ZERO_ONE("zeroOne");

    private String code;

    EParentAssociateType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
