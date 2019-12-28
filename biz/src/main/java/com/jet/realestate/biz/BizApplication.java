package com.jet.realestate.biz;

import com.jet.realestate.security.config.DefaultSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringCloudApplication
@RefreshScope
@EnableFeignClients
public class BizApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizApplication.class,args);
    }
}
