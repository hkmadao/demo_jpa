package org.hkmadao.tcdt.conf;

/**
 * 删除引用错误信息
 */
public class DeleteRefErrorMessageVO {

    /**
     * 被引用id
     */
    private String idData;


    /**
     * 错误信息
     */
    private String message;

    /**
     * 被引用类名
     */
    private String sourceClassName;

    /**
     * 引用类名
     */
    private String refClassName;

    public String getIdData() {
        return idData;
    }

    public void setIdData(String idData) {
        this.idData = idData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceClassName() {
        return sourceClassName;
    }

    public void setSourceClassName(String sourceClassName) {
        this.sourceClassName = sourceClassName;
    }

    public String getRefClassName() {
        return refClassName;
    }

    public void setRefClassName(String refClassName) {
        this.refClassName = refClassName;
    }
}
