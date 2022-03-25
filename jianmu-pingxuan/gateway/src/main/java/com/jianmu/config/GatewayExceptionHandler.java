package com.jianmu.config;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.jianmu.component.CommonResponse;
import com.jianmu.config.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 修改限流错误提示
 *
 * @author lime
 */
@Slf4j
public class GatewayExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable e) {
        log.error(e.getMessage());
        e.printStackTrace();
        if (e instanceof ParamFlowException) {
            return CommonResponse.response(exchange, Result.fail("接口只允许每秒访问一次").toString());
        }
        return CommonResponse.response(exchange, Result.fail(e.getMessage()).toString());
    }

}
