package com.jianmu.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lime
 */
@Configuration
public class GatewayConfig {

    /**
     * 限流配置
     */

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                         ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 加载自定义提示
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GatewayExceptionHandler gatewayExceptionHandler() {
        return new GatewayExceptionHandler();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // 为 Spring Cloud Gateway 注册块异常处理程序。
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
//        initCustomizedApis();
        initGatewayRules();
    }

    private void initCustomizedApis() {
//        Set<ApiDefinition> definitions = new HashSet<>();
//        ApiDefinition api1 = new ApiDefinition("some_customized_api")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    add(new ApiPathPredicateItem().setPattern("/ahas"));
//                    add(new ApiPathPredicateItem().setPattern("/product/**")
//                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});
//        ApiDefinition api2 = new ApiDefinition("another_customized_api")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    add(new ApiPathPredicateItem().setPattern("/**")
//                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});
//        definitions.add(api1);
//        definitions.add(api2);
//        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * resource：资源名称，可以是网关中的 route 名称或者用户自定义的API 分组名称。
     * resourceMode：规则是针对 API Gateway 的route（RESOURCE_MODE_ROUTE_ID）还是用户在 Sentinel 中定义的API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是route。
     * grade：限流指标维度，同限流规则的grade 字段。
     * count：限流阈值
     * intervalSec：统计时间窗口，单位是秒，默认是1 秒（目前仅对参数限流生效）。
     * controlBehavior：流量整形的控制效果，同限流规则的 controlBehavior 字段，目前支持快速失败和匀速排队两种模式，默认是快速失败。
     * burst：应对突发请求时额外允许的请求数目（目前仅对参数限流生效）。
     * maxQueueingTimeoutMs：匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效。
     * paramItem：参数限流配置。若不提供，则代表不针对参数进行限流，该网关规则将会被转换成普通流控规则；否则会转换成热点规则。其中的字段：
     * parseStrategy：从请求中提取参数的策略，目前支持提取来源 IP（PARAM_PARSE_STRATEGY_CLIENT_IP）、Host（PARAM_PARSE_STRATEGY_HOST）、任意 Header（PARAM_PARSE_STRATEGY_HEADER）和任意 URL 参数（PARAM_PARSE_STRATEGY_URL_PARAM）四种模式。
     * fieldName：若提取策略选择 Header 模式或 URL 参数模式，则需要指定对应的 header 名称或 URL 参数名称。
     * pattern 和 matchStrategy：为后续参数匹配特性预留，目前未实现。
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("auth-api")
                        //限流阈值
                        .setCount(1)
                        //统计时间窗口，单位是秒，默认是1 秒（目前仅对参数限流生效）
                        .setIntervalSec(1)
//                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
//                .setMaxQueueingTimeoutMs(3000)
        );

        GatewayRuleManager.loadRules(rules);
    }
}
