package com.jet.realestate.biz;

import com.jet.realestate.security.config.DefaultSecurityConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;

@SpringCloudApplication
@RefreshScope
@EnableFeignClients
public class BizApplication implements CommandLineRunner {
    private static final int SIZE = 1024 * 1024 * 100;

    public static void main(String[] args) {
        SpringApplication.run(BizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//       ScheduledExecutorService schd= Executors.newScheduledThreadPool(10);
//       schd.scheduleAtFixedRate(()->{
//           long[] arry=new long[SIZE];
//           System.out.println("分配...");
//       },3,3, TimeUnit.SECONDS);

    }
}
