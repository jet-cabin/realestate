package com.jet.realestate.common.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class WebAutoConfiguration implements WebMvcConfigurer {
    Logger log = LoggerFactory.getLogger(WebAutoConfiguration.class);

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUrlPathHelper(new UrlPathHelper());
        configurer.getUrlPathHelper().setRemoveSemicolonContent(false);
        log.info("implement the method configurepathmatch of webmvcconfigurer for allow semicolon in url path...");
//        super.configurePathMatch(configurer);
    }
}
