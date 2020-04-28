//package com.jet.estate.message.core;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.SpringApplicationRunListener;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//@Slf4j
//public class ListenerRegistrar implements SpringApplicationRunListener {
//    @Autowired
//    private Consumer consumer;
//
//    public ListenerRegistrar(){}
//
//    public ListenerRegistrar(SpringApplication app, String[] s) {
//        log.info("innerConsumer mgr constructor");
//    }
//
//    @PostConstruct
//    public void afterConstruct() {
//        log.info("");
//    }
//
//    @Override
//    public void starting() {
//        log.info("starting...");
//    }
//
//
//    @Override
//    public void started(ConfigurableApplicationContext context) {
//        log.info("started...");
//        if (consumer != null)
//            consumer.registerListener(context);
//    }
//
//    @Override
//    public void running(ConfigurableApplicationContext context) {
//        log.info("running...");
//        if (consumer != null)
//            consumer.polling(context);
//    }
//
//
//    @Override
//    public void environmentPrepared(ConfigurableEnvironment environment) {
//        log.info("env prepared...");
//    }
//
//    @Override
//    public void contextPrepared(ConfigurableApplicationContext context) {
//        log.info("context prepared...");
//    }
//
//    @Override
//    public void contextLoaded(ConfigurableApplicationContext context) {
//        log.info("context loaded...");
//
//    }
//
//    @Override
//    public void failed(ConfigurableApplicationContext context, Throwable exception) {
//        log.info("failed...");
//    }
//}
