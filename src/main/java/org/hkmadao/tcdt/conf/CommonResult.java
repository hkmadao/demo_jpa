package org.hkmadao.tcdt.conf;

/**
 * 公共返回结果类型
 */
public class CommonResult {

    //逻辑成功状态
    public final static int SUCCESS_STATUS = 0;
    //开发者抛出的错误，通常是业务逻辑不通过，需要提示
    public final static int FAILED_STATUS = 1;
    //生成代码报错
    public final static int GENERATE_CODE_FAILED_STATUS = 2;
    //程序抛出的错误，通常是程序bug，需要开发者完善的地方
    public final static int EXCEPTION_STATUS = 500;
    //不带token，或token格式不对
    public final static int UNAUTHORIZED_STATUS = 401;
    //权限不足，越权访问
    public final static int FORBIDDEN_STATUS = 403;

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

    private CommonResult() {
    }

    public CommonResult(int status) {
        this.status = status;
    }

    private CommonResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private CommonResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static CommonResult success() {
        return new CommonResult(SUCCESS_STATUS);
    }

    public static CommonResult successMsg(String message) {
        return new CommonResult(SUCCESS_STATUS, message);
    }

    public static CommonResult success(Object data) {
        return new CommonResult(SUCCESS_STATUS, null, data);
    }

    public static CommonResult success(String message, Object data) {
        return new CommonResult(SUCCESS_STATUS, message, data);
    }

    /**
     * 不需要入参，程序异常信息不暴露到前端
     *
     * @return 系统异常
     */
    public static CommonResult sysException() {
        return new CommonResult(EXCEPTION_STATUS, "系统异常，请稍后重试！");
    }
    /**
     * 需要将开发者给出的信息提示到前端
     *
     * @return 系统异常
     */
    public static CommonResult bizFailed(String message) {
        return new CommonResult(FAILED_STATUS, message);
    }
    /**
     * 生成代码错误，提供详细错误信息
     *
     * @return 生成代码报错的错误信息
     */
    public static CommonResult generateCodeFailed(String message,Object data) {
        return new CommonResult(GENERATE_CODE_FAILED_STATUS, message,data);
    }

    /**
     * 不带token，或token格式不对
     *
     * @return 不带token，或token格式不对
     */
    public static CommonResult unauthorizedFailed(String message) {
        return new CommonResult(UNAUTHORIZED_STATUS, message);
    }

    /**
     * 无权限异常
     *
     * @return 无权限异常
     */
    public static CommonResult forbiddenFailed(String message) {
        return new CommonResult(FORBIDDEN_STATUS, message);
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
