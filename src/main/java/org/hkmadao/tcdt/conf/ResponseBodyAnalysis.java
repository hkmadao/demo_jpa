package org.hkmadao.tcdt.conf;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseBodyAnalysis implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<?
            extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        //非json返回类型不需要包装
        if (!MediaType.APPLICATION_JSON.equals(selectedContentType)) {
            return body;
        }
        if (body instanceof CommonResult) {
            if (((CommonResult) body).getStatus() == CommonResult.EXCEPTION_STATUS) {
                //程序抛出的错误，通常是程序bug，需要开发者完善的地方，以http状态码为500返回
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return body;
            }
            //开发者抛出的错误，通常是业务逻辑不通过，以提示方式返回
            if (((CommonResult) body).getStatus() == CommonResult.FAILED_STATUS) {
                return body;
            }
            //生成代码错误，提供详细错误信息
            if (((CommonResult) body).getStatus() == CommonResult.GENERATE_CODE_FAILED_STATUS) {
                //以http状态码为500返回
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return body;
            }
//            if (((CommonResult) body).getStatus() == CommonResult.FORBIDDEN_STATUS) {
//                response.setStatusCode(HttpStatus.FORBIDDEN);
//                return ((CommonResult) body).getMessage();
//            }
            return body;
        }
        return CommonResult.success(body);
    }
}
