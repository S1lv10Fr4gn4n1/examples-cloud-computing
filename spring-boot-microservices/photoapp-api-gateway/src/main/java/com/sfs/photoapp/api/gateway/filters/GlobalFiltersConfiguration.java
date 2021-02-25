package com.sfs.photoapp.api.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {

    private final Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter secondFilter() {
        return ((exchange, chain) -> {
            logger.info("Second Global Pre-Filter is executed");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Second Global Post-Filter is executed");
            }));
        });
    }

    @Order(2)
    @Bean
    public GlobalFilter thirdFilter() {
        return ((exchange, chain) -> {
            logger.info("Third Global Pre-Filter is executed");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Third Global Post-Filter is executed");
            }));
        });
    }
}
