package com.jianmu.config;

import com.jianmu.bean.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;

/**
 * @author lime
 */

@RestControllerAdvice
@Slf4j
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getMethod() != null) {
            return returnType.getMethod().isAnnotationPresent(ResponseResult.class);
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter parameter, @Nonnull MediaType type, @Nonnull Class<? extends HttpMessageConverter<?>> converterType,
                                  @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        if (body instanceof Boolean) {
            return (Boolean) body ? Result.ok() : Result.fail();
        } else if (body instanceof String) {
            return Result.ok(String.valueOf(body));
        }
        return Result.ok(body);
    }
}
