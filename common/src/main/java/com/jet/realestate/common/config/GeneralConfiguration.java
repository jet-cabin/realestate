package com.jet.realestate.common.config;


import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneralConfiguration {

    public GeneralConfiguration() {

    }
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler(){
//        AccessDeniedHandler restfulAccessDeniedHandler= new RestfulAccessDeniedHandler();
//        return restfulAccessDeniedHandler;
//    }

    @Bean
    public ClientHttpRequestFactory clientFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();

        clientHttpRequestFactory.setReadTimeout(12000);
        clientHttpRequestFactory.setConnectTimeout(15000);
        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate template = new RestTemplate();

        template.setRequestFactory(factory);
        return template;
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer1(
            @Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
