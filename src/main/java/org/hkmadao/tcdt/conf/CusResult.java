package org.hkmadao.tcdt.conf;

/**
 * 自定义返回结果集
 * 用于需要自定义显示错误信息的返回值
 */
public class CusResult {

    public final static int SUCCESS_STATUS = 0;
    public final static int FAILED_STATUS = 1;

    /**
     * 状态码
     */
    private int status;

    /**
     * 返回说明
     */
    private String message;

    /**
     * 返回结果数据
     */
    private Object data;

    private CusResult() {
    }

    public CusResult(int status) {
        this.status = status;
    }

    private CusResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private CusResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static CusResult success() {
        return new CusResult(SUCCESS_STATUS);
    }

    public static CusResult successMsg(String message) {
        return new CusResult(SUCCESS_STATUS, message);
    }

    public static CusResult success(Object data) {
        return new CusResult(SUCCESS_STATUS, null, data);
    }

    public static CusResult success(String message, Object data) {
        return new CusResult(SUCCESS_STATUS, message, data);
    }

    public static CusResult failed() {
        return new CusResult(FAILED_STATUS, "系统异常，请稍后重试！");
    }

    public static CusResult failed(String message) {
        return new CusResult(FAILED_STATUS, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
