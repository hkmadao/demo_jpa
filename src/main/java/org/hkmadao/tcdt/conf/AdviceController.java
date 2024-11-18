package org.hkmadao.tcdt.conf;

import org.hkmadao.core.base.BusinessException;
//import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.exceptions.TemplateEngineException;

@RestControllerAdvice
public class AdviceController {

    private static final Logger log = LoggerFactory.getLogger(AdviceController.class);

    // 响应码的定义
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(Exception ex) {
        log.error("", ex);
        if (ex instanceof BusinessException) {
            //开发者抛出的错误，通常是业务逻辑不通过，需要提示
            return CommonResult.bizFailed(ex.getMessage());
        }
        if (ex instanceof TemplateEngineException) {
            //生成代码的模板错误，提示详细信息
            CommonResult generateCodeFailed = CommonResult.generateCodeFailed(ex.getMessage(), ex);
            return generateCodeFailed;
        }
//        if (ex instanceof UnauthorizedException) {
//            //无权限异常
//            return CommonResult.forbiddenFailed("权限不足！");
//        }
        //程序抛出的错误，通常是程序bug，需要开发者完善的地方
        return CommonResult.sysException();
    }
}