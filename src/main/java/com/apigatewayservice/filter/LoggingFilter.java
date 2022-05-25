package com.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter gatewayFilter = new OrderedGatewayFilter((exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter-baseMessage = {}", config.getBaseMessage());
            if (config.isPreLogger()) {
                log.info("Logging Pre Filter start-requestId : {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Logging Post Filter-responseCode : {}", response.getStatusCode());
                }
            }));
        }, Ordered.HIGHEST_PRECEDENCE); // filter의 우선순위
        return gatewayFilter;
    }
}
