package com.jianmu.api;

import com.jianmu.dubbo.api.AuthDubboApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestApi {
    @DubboReference(check = false)
    private AuthDubboApi authDubboApi;

    @GetMapping("/test")
    public String test() {
        log.error(authDubboApi.print("auth"));
        return "aa";
    }
}
