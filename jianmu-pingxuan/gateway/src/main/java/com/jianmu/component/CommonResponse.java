package com.jianmu.component;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author kong
 */
public class CommonResponse {
    public static Mono<Void> response(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Cookie,X-CSRF-TOKEN, Accept,Authorization");
        response.getHeaders().add("Access-Control-Expose-Headers", "Authorization,authenticated");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, OPTIONS");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
