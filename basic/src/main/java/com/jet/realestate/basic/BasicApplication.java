package com.jet.realestate.basic;

import com.jet.realestate.common.config.WebAutoConfiguration;
import com.jet.realestate.security.config.DefaultSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Import;

/**
 * 应用启动入口
 *
 */
@SpringCloudApplication
@RefreshScope
@Import(WebAutoConfiguration.class)
public class BasicApplication {
    static long[] arry=new long[12000000];
    public static void main(String[] args) {

        SpringApplication.run(BasicApplication.class, args);
    }
}
