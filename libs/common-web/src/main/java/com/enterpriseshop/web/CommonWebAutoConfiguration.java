package com.enterpriseshop.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * Loaded via META-INF/spring/...AutoConfiguration.imports in every service that
 * depends on common-web, regardless of its base package. SERVLET-only, so it does
 * not touch the reactive api-gateway (which doesn't use common-web anyway).
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CommonWebAutoConfiguration {

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }
}
